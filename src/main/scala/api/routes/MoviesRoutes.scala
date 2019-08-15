package api.routes

import akka.actor.ActorRef
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.HttpResponse

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.{Await, Future}
import akka.pattern.ask
import api.BaseRoutes
import api.actors.MoviesRegistryActor

object MoviesRoutes extends BaseRoutes {

  val moviesRegistryActor: ActorRef = system.actorOf(MoviesRegistryActor.props, "moviesRegistryActor")

  override def getRoutes: Route =
    pathPrefix("movie") {
      path(Segment) { name =>
        get {
          log.info(s"Getting movie $name")
          val movieScoreFuture: Future[Double] = (moviesRegistryActor ? MoviesRegistryActor.GetMovieScore(name)).mapTo[Double]

          val movieScore = Await.result(movieScoreFuture, 5.seconds)

          // TODO: Return movieScore
          complete("temp"/*movieScore*/)
        }
      }
    }
}
