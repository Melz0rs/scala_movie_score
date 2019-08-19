package api.routes

import akka.http.scaladsl.server.{RequestContext, RouteResult}
import akka.http.scaladsl.server.Directives._
import clients.HttpClient

import scala.concurrent.Future

object Routes {

  def setup( /* conf? */ )(implicit httpClient: HttpClient): RequestContext => Future[RouteResult] = {
    concat(
      MoviesRoutes.getRoutes
    )
  }

}
