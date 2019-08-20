package com

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.akkaServer.AkkaImplicits
import com.akkaServer.routes.Routes
import com.cache.Cache
import com.cache.impls.{MoviesCache, RedisCache}
import com.config.{AppConfigService, ConfigService}
import com.httpClient.HttpClient

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}


/*
  TODO-LIST: 1) Load config file and parse it into Config object
             2) Implement RedisCache
*/

object Main extends App with AkkaImplicits {

  implicit val httpClient: HttpClient = new HttpClient((ex: Exception) => println(s"an error occurred: $ex"))
  implicit val cache: Cache = new MoviesCache() // RedisCache()
  val configService: ConfigService = new AppConfigService()

  configService.loadConfig(/* get config file from env variables */)

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
