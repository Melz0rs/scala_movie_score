package api.routes

import akka.actor.{ ActorRef, Props }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.pattern.ask
import api.BaseRoutes
import api.actors.MoviesRegistryActor
import api.jsonSupport.moviesJsonSupport
import classes.Score
import httpClient.HttpClient
import factories.AppMovieProvidersFactory
import movieProviders.AvgMovieProvider
import traits.MovieProvidersFactory

import scala.concurrent.Future

object MoviesRoutes extends BaseRoutes with moviesJsonSupport {

  override def getRoutes(implicit httpClient: HttpClient): Route = {

    // TODO: Check environment variables \ conf file in order to know what factory to use
    val movieProvidersFactory: MovieProvidersFactory = AppMovieProvidersFactory

    val moviesRegistryActor: ActorRef =
      system.actorOf(
        Props(new MoviesRegistryActor(AvgMovieProvider(movieProvidersFactory))),
        "moviesRegistryActor")

    pathPrefix("movie") {
      path(Segment) { name =>
        get {
          log.info(s"Getting movie $name")
          val movieScoreFuture = (moviesRegistryActor ? MoviesRegistryActor.GetMovieScore(name)).mapTo[Score]

          onSuccess(movieScoreFuture) { movieScore =>
            complete(movieScore)
          }
        }
      }
    }
  }
}
