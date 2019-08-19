package movieProviders

trait HttpMovieProvider {
  val url: String
  val headers: Map[String, String]
}

