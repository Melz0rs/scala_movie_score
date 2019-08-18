package api.actors

import akka.actor.{ Actor, ActorLogging, Props }
import services.MoviesService

final case class Movie(name: String, score: Double)

object MoviesRegistryActor {
  final case class GetMovieScore(name: String)
  final case class ActionPerformed(description: String)

  def props: Props = Props[MoviesRegistryActor]
}

// TODO: Inject MoviesService here
class MoviesRegistryActor extends Actor with ActorLogging {
  def receive: Receive = {
    case MoviesRegistryActor.GetMovieScore(name) =>
      sender() ! MoviesService.getScore(name) // call movies service get score
  }
}
