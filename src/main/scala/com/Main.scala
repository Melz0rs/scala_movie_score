package com

import akka.http.scaladsl.server.{RequestContext, RouteResult}
import com.routes.Routes
import com.cache.Cache
import com.cache.impls.{MoviesCache, RedisCache}
import common.app.AkkaApp
import common.config.{CommonConfigService, ConfigService}
import com.httpClient.HttpClient

import scala.concurrent.Future

/*
  TODO-LIST: 1) Load config file and parse it into Config object
             2) Implement RedisCache
             3) Replace docker compose with k8s
*/

object Main extends AkkaApp {

  val configService: ConfigService = CommonConfigService()
  val configFilePath: String = "" // TODO: Get config file from system variables / run arguments
  val config = configService.loadConfig(configFilePath)

  override def getRoutes: () => RequestContext => Future[RouteResult] = () => {
    implicit val cache: Cache = new MoviesCache() // RedisCache()
    implicit val httpClient: HttpClient = new HttpClient((ex: Exception) => println(s"an error occurred: $ex"))

    Routes.setup(config)
  }
}
