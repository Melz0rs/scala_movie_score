package com.routes

import akka.actor.{ActorRef, Props}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.pattern.ask
import common.akkaServer.BaseRoutes
import common.akkaServer.actors.MoviesRegistryActor
import common.akkaServer.jsonSupport.moviesJsonSupport
import com.cache.Cache
import com.classes.Score
import com.config.AppConfig
import com.httpClient.HttpClient
import com.factories.AppMovieProvidersFactory
import com.movieProviders.AvgMovieProvider
import com.traits.MovieProvidersFactory

case class MoviesRoutes(config: AppConfig)(implicit httpClient: HttpClient, cache: Cache) extends BaseRoutes with moviesJsonSupport {

  override def getRoutes(): Route = {

    // TODO: Check environment variables \ conf file in order to know what factory to use
    val movieProvidersFactory: MovieProvidersFactory = AppMovieProvidersFactory(config)

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
