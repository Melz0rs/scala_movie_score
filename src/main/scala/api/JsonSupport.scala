package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import api.actors.Movie
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._

  implicit val movieJsonFormat = jsonFormat2(Movie)
}
