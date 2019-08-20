package com.api.routes

import akka.http.scaladsl.server.{ RequestContext, RouteResult }
import akka.http.scaladsl.server.Directives._
import com.httpClient.HttpClient
import com.traits.Cache

import scala.concurrent.Future

object Routes {

  def setup( /* conf? */ )(implicit httpClient: HttpClient, cache: Cache): RequestContext => Future[RouteResult] = {
    concat(
      MoviesRoutes().getRoutes())
  }

}
