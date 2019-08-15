package clients

import akka.http.scaladsl.model.HttpResponse
import services.HttpService

import scala.concurrent.Future

trait HttpMethod

object HttpMethods {
  final case class Get() extends HttpMethod
  final case class Post() extends HttpMethod
  final case class Delete() extends HttpMethod
  final case class Update() extends HttpMethod
}

abstract class BaseHttpClient[T](onError: Exception => Unit, var headers: Map[String, String]) {

  def get(url: String): Future[T] = {
    try {
      HttpService.execute(url, headers, HttpMethods.Get())
    } catch {
      case e: Exception =>
        onError(e)
        Future.failed(e)
    }
  }
//
//  def get(url: String): Future[String] = {
//    try {
//      HttpService.execute(url, headers, HttpMethods.Get)
//
//
//    } catch {
//      case e: Exception => {
//        onError(e)
//
//        None
//      }
//    }
//  }
}
