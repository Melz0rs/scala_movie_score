package api.routes

import akka.http.scaladsl.server.{ RequestContext, RouteResult }
import akka.http.scaladsl.server.Directives._

import scala.concurrent.Future

object Routes {

  def setup( /* conf? */ ): RequestContext => Future[RouteResult] = {
    concat(
      MoviesRoutes.getRoutes)
  }

}
