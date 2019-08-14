package com.example

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.Future
import com.example.UserRegistryActor._
import akka.pattern.ask
import akka.util.Timeout

trait MovieRoutes extends JsonSupport {

  implicit def system: ActorSystem
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  lazy val log = Logging(system, classOf[MovieRoutes])
  def userRegistryActor: ActorRef

  lazy val movieRoutes: Route =
    pathPrefix("movie") {
      path(Segment) { name =>
        get {
          log.info(s"Getting movie $name")
          val movie: Future[Option[Movie]] =
            (userRegistryActor ? GetMovieScore(name)).mapTo[Option[Movie]]
          //           rejectEmptyResponse {
          complete(movie)
          //          }
        }
      }
    }
}
