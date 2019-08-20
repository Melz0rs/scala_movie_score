package com.akkaServer

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.server.Route
import akka.util.Timeout

trait BaseRoutes {
  implicit lazy val timeout = Timeout(10.seconds) // Inject from config file
  implicit def system: ActorSystem = ActorSystem("scala_movice_service_system_actor")
  lazy val log = Logging(system, classOf[BaseRoutes])

  def getRoutes(): Route
}