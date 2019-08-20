package com

import akka.http.scaladsl.server.Route
import common.akkaServer.routes.Routes
import com.cache.Cache
import com.cache.impls.{ MoviesCache, RedisCache }
import com.config.AppConfig
import common.app.{ AkkaApp, ConfigApp }
import common.config.{ CommonConfigService, Config, ConfigService }
import com.httpClient.HttpClient

/*
  TODO-LIST: 1) Load config file and parse it into Config object
             2) Implement RedisCache
             3) Replace docker compose with k8s
*/

object Main extends ConfigApp with AkkaApp {

  override val config: Config = AppConfig
  override val configService: ConfigService = CommonConfigService()
  override val configFilePath: String = ""
  override lazy val routes: Route = Routes.setup()

  implicit val httpClient: HttpClient = new HttpClient((ex: Exception) => println(s"an error occurred: $ex"))
  implicit val cache: Cache = new MoviesCache() // RedisCache()

}
