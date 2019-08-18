package clients

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import exceptions.{HttpMethodNotSupportedException, HttpResponseException}
import traits.AkkaImplicits

import scala.concurrent.Future

trait BaseHttpClient extends AkkaImplicits {

  val onError: Exception => Unit
  val headers: Map[String, String]

  protected def get[A](url: String): Future[A] = {
    try {
      execute(url, headers, HttpMethods.GET)
    } catch {
      case e: Exception =>
        onError(e)
        Future.failed(e)
    }
  }

  private def execute[A](url: String, headers: Map[String, String], method: HttpMethod): Future[A] = {
    val httpRequest = prepareRequest(url, Map("a" -> "b"), method)

    
  }

  private def get[A <: AnyRef](url: String, headers: Map[String, String]): Future[A] = {

    val futureRequest = Http().singleRequest(httpRequest)

    futureRequest.flatMap(handleStringResponse)
  }

  private def get[String](url: String, headers: Map[String, String]): Future[String] = {
    val httpRequest = prepareRequest(url, Map("a" -> "b"))

    val futureRequest = Http().singleRequest(httpRequest)

    futureRequest.flatMap(handleStringResponse)
  }

  private def prepareRequest(url: String, headers: Map[String, String], httpMethod: HttpMethod): HttpRequest = {

    val httpRequest = HttpRequest(
      method = httpMethod,
      url,
      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "data"))

    val httpHeaders = headers.map {
      case (key: String, value: String) => RawHeader(key, value)
    }

    httpHeaders.foreach(httpRequest.addHeader)

    httpRequest
  }

  private def handleStringResponse(response: HttpResponse): Future[String] = {
    //    implicit unmarshaller: Unmarshaller[ResponseEntity, String]

    if (response.status.isSuccess()) {
      Unmarshal(response).to[String]
    } else {
      throw HttpResponseException(response) // TODO: Maybe Future.failed?
    }
  }
}
