package com.routes

import akka.http.scaladsl.server.{RequestContext, RouteResult}
import akka.http.scaladsl.server.Directives._
import com.cache.Cache
import com.config.AppConfig
import com.httpClient.HttpClient

import scala.concurrent.Future

object Routes {

  def setup(config: AppConfig)(implicit httpClient: HttpClient, cache: Cache): RequestContext => Future[RouteResult] = {
    concat(
      MoviesRoutes(config).getRoutes())
  }

}
