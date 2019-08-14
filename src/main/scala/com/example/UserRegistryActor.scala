package com.example

import akka.actor.{ Actor, ActorLogging, Props }

final case class Movie(name: String, score: Double)

object UserRegistryActor {
  final case class GetMovieScore(name: String)
  final case class ActionPerformed(description: String)

  def props: Props = Props[UserRegistryActor]
}

class UserRegistryActor extends Actor with ActorLogging {
  import UserRegistryActor._

  def receive: Receive = {
    case GetMovieScore(name) =>
      sender() ! Some(Movie(name, 99.4))
  }
}
