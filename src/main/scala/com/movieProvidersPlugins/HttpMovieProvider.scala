package com.movieProvidersPlugins

trait HttpMovieProvider {
  val url: String
  val headers: Map[String, String]
}
