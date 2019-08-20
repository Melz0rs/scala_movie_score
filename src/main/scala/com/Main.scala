package com

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.api.routes.Routes
import com.cache.Cache
import com.cache.impls.MoviesCache
import com.httpClient.HttpClient
import com.traits.AkkaImplicits

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object Main extends App with AkkaImplicits {

  // TODO: Init dependencies
  implicit val httpClient: HttpClient = new HttpClient((ex: Exception) => println(s"an error occurred: $ex"))
  implicit val cache: Cache = new MoviesCache()

  lazy val routes: Route = Routes.setup()

  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 8080)

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
}
