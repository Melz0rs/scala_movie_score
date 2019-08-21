package com

import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import common.akkaServer.routes.Routes
import com.cache.Cache
import com.cache.impls.{MoviesCache, RedisCache}
import com.config.AppConfig
import common.app.{AkkaApp, ConfigApp}
import common.config.{CommonConfigService, Config, ConfigService}
import com.httpClient.HttpClient

import scala.concurrent.Future

/*
  TODO-LIST: 1) Load config file and parse it into Config object
             2) Implement RedisCache
             3) Replace docker compose with k8s
*/

object Main extends ConfigApp with AkkaApp {

  override def getRoutes: () => RequestContext => Future[RouteResult] = () => {
    implicit val cache: Cache = new MoviesCache() // RedisCache()
    implicit val httpClient: HttpClient = new HttpClient((ex: Exception) => println(s"an error occurred: $ex"))

    Routes.setup()
  }

  override def getConfig: () => AppConfig.type = () => AppConfig
  override def getConfigService: () => CommonConfigService = () => CommonConfigService()
  override def getConfigFilePath: () => String = () => ""
//
//  private val createImplicits() => {
//
//  }
}
