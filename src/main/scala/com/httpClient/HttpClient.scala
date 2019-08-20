package com.httpClient

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.akkaServer.AkkaImplicits
import com.exceptions.{HttpResponseException, UnsupportedJsonFormatException}
import io.circe.Decoder
import io.circe._
import io.circe.parser._

import scala.concurrent.Future

class HttpClient(onError: Exception => Unit) extends AkkaImplicits {

  def get[A](url: String, headers: Map[String, String])(implicit decoder: Decoder[A]): Future[A] = {
    execute[A](url, headers, HttpMethods.GET)
  }

  private def execute[A](url: String, headers: Map[String, String], method: HttpMethod)(implicit decoder: Decoder[A]): Future[A] = {
    val httpRequest = prepareRequest(url, headers, method)

    // TODO: What if SingleRequest fails?
    Http().singleRequest(httpRequest).flatMap(handleResponse[A])
  }

  private def prepareRequest(url: String, headers: Map[String, String], httpMethod: HttpMethod): HttpRequest = {

    val httpRequest = HttpRequest(
      method = httpMethod,
      url,
      entity = HttpEntity(ContentTypes.`application/json`, "data"))

    val httpHeaders = headers.map {
      case (key: String, value: String) => RawHeader(key, value)
    }

    httpHeaders.foreach(httpRequest.addHeader)

    httpRequest
  }

  private def handleResponse[A](response: HttpResponse)(implicit decoder: Decoder[A]): Future[A] = {
    if (response.status.isSuccess()) {
      Unmarshal(response.entity).to[String].map { jsonString =>
        val json = parse(jsonString).getOrElse(Json.Null)

        decoder.decodeJson(json) match {
          case Right(value) => value
          case Left(_) => throw UnsupportedJsonFormatException(jsonString)
        }
      }
    } else {
      Future.failed(HttpResponseException(response))
    }
  }
}
