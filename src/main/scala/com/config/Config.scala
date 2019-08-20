package com.config

object Config {
  var imdbUrl: String = "http://www.omdbapi.com/"
  var rtUrl: String = ""

  var imdbApiKey: String = "67d579a3"
  var rtApiKey: String = ""

  var imdbHeaders: Map[String, String] = Map()
  var rtHeaders: Map[String, String] = Map()
}
