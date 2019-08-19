package movieProviders.responses

import io.circe._, io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class ImdbResponse(imdbRating: Double)

object ImdbResponse {
  implicit val imdbResponseDecoder: Decoder[ImdbResponse] = deriveDecoder
  implicit val imdbResponseEncoder: Encoder[ImdbResponse] = deriveEncoder
}
