package common.webServer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import common.akkaServer.AkkaImplicits

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class AkkaWebServer(routes: Route) extends WebServer with AkkaImplicits {
  override def listen(host: String, port: Int): Unit = {
    val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, host, port)

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
}
