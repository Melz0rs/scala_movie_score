package com.movieProviders.responses

import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.{ Decoder, Encoder }

case class RottenTomatoesResponse(score: Double)

object RottenTomatoesResponse {
  implicit val rtResponseDecoder: Decoder[RottenTomatoesResponse] = deriveDecoder
  implicit val rtResponseEncoder: Encoder[RottenTomatoesResponse] = deriveEncoder
}
