package common.app

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import common.akkaServer.AkkaImplicits

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

trait AkkaApp extends CommonApp with AkkaImplicits {

  def getRoutes: () => Route
  val host: String = "localhost"
  val port: Int = 8080
  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(getRoutes(), host, port)

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()

      Await.result(system.whenTerminated, Duration.Inf)
  }
}