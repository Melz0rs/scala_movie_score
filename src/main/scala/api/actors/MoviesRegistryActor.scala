package api.actors

import akka.actor.{ Actor, ActorLogging }
import httpClient.HttpClient
import akka.pattern.pipe
import api.actors.MoviesRegistryActor.GetMovieScoreResponse
import classes.Score
import movieProvidersPlugins.MovieProvider

import scala.concurrent.ExecutionContext.Implicits.global

object MoviesRegistryActor {
  final case class GetMovieScore(name: String)
  final case class GetMovieScoreResponse(score: Score)
}

class MoviesRegistryActor(movieProvider: MovieProvider)(implicit httpClient: HttpClient) extends Actor with ActorLogging {
  def receive: Receive = {

    case MoviesRegistryActor.GetMovieScore(name) =>
      movieProvider.getScore(name).map(GetMovieScoreResponse.apply).pipeTo(self)(sender())

    case GetMovieScoreResponse(score) =>
      sender() ! score

  }
}
