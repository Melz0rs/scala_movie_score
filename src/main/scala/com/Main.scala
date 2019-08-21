package com

import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import com.routes.Routes
import com.cache.Cache
import com.cache.impls.{MoviesCache, RedisCache}
import common.config.{CommonConfigService, ConfigService}
import com.httpClient.HttpClient
import common.webServer.{AkkaWebServer, WebServer}

/*
  TODO-LIST:
            * Load config file and parse it into Config object
            * Implement RedisCache
            * Replace docker compose with k8s
*/

object Main extends App {

  implicit val cache: Cache = new MoviesCache() // RedisCache()
  implicit val httpClient: HttpClient = new HttpClient((ex: Exception) => println(s"an error occurred: $ex"))

  val configService: ConfigService = CommonConfigService()
  val config = configService.loadConfig()

  val routes: Route = Routes.setup(config)
  val webServer: WebServer = new AkkaWebServer(routes)

  webServer.listen()
}
