package services

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpHeader, HttpMethods, HttpRequest, HttpResponse, StatusCode, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import clients.HttpMethod
import clients.HttpMethods.Get
import exceptions.{HttpMethodNotSupportedException, HttpResponseException}
import traits.AkkaImplicits
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object HttpService extends AkkaImplicits {

  def execute[T](url: String, headers: Map[String, String], method: HttpMethod): Future[T] = {
    method match {
      case Get() => get[T](url, headers)
      case _ => throw HttpMethodNotSupportedException() // TODO: Maybe Future.failed?
    }
  }

  private def get[T](url: String, headers: Map[String, String]): Future[T] = {
    val httpRequest = prepareRequest(url, Map("a" -> "b"))

    val futureRequest = Http().singleRequest(httpRequest)

    val httpResponse = Await.result(futureRequest, 5.seconds) // TODO: Import 5 seconds from configuration

    handleResponse[T](httpResponse)
  }

  private def prepareRequest(url: String, headers: Map[String, String]): HttpRequest = {
    val httpRequest = HttpRequest(method = HttpMethods.POST,
                                  url,
                                  entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "data"))

    val httpHeaders = headers.map{
      case (key: String, value: String) => RawHeader(key, value)
    }

    httpHeaders.foreach(httpRequest.addHeader)

    httpRequest
  }

  private def handleResponse[T <: AnyRef](response: HttpResponse): Option[T] = {
    if (response.status.isSuccess()) {
      // TODO: Deserialize json

    } else {
      throw HttpResponseException(response)
    }
  }

  private def handleResponse[String](response: HttpResponse): Future[String] = {
    if (response.status.isSuccess()) {
      Unmarshal(response.entity).to[String]
    } else {
      throw HttpResponseException(response) // TODO: Maybe Future.failed?
    }
  }
}
