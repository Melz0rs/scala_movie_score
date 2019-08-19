package api.jsonSupport

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import classes.Score
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait moviesJsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._

  implicit val scoreJsonFormat: RootJsonFormat[Score] = jsonFormat1(Score)
}
