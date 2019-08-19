package api.actors

import akka.actor.{ Actor, ActorLogging, Props }
import httpClient.HttpClient
import traits.MovieProvider

object MoviesRegistryActor {
  final case class GetMovieScore(name: String)
}

class MoviesRegistryActor(movieProvider: MovieProvider)(implicit httpClient: HttpClient) extends Actor with ActorLogging {
  def receive: Receive = {
    case MoviesRegistryActor.GetMovieScore(name) =>
      sender() ! movieProvider.getScore(name)
  }
}
