package clients

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpMethod, HttpMethods, HttpRequest, HttpResponse }
import akka.http.scaladsl.unmarshalling.Unmarshal
import exceptions.HttpResponseException
import traits.AkkaImplicits
import scala.concurrent.Future

class HttpClient(onError: Exception => Unit) extends AkkaImplicits {

  def get[A](url: String, headers: Map[String, String]): Future[A] = {
    try {
      execute[A](url, headers, HttpMethods.GET)
    } catch {
      case e: Exception =>
        onError(e)
        Future.failed(e)
    }
  }

  private def execute[A](url: String, headers: Map[String, String], method: HttpMethod): Future[A] = {
    val httpRequest = prepareRequest(url, Map("a" -> "b"), method)

    Http().singleRequest(httpRequest).flatMap(handleResponse[A])
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

  private def handleResponse[A](response: HttpResponse): Future[A] = {
    if (response.status.isSuccess()) {
      Unmarshal(response).to[A]
    } else {
      throw HttpResponseException(response) // TODO: Maybe Future.failed?
    }
  }
}
