package com.classes

import com.redis.serialization.Parse

case class Score(value: Double) {}
//
//object Score {
//  implicit val parser: Parse[Score] = Parse[Score](bytes => Score(bytes.toString.toDouble))
//}