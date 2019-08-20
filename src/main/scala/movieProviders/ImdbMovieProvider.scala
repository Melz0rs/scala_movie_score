package movieProviders

import java.net.URLEncoder

import classes.Score
import httpClient.HttpClient
import movieProviders.responses.ImdbResponse
import movieProvidersPlugins.{ CachingMovieProvider, HttpMovieProvider }
import traits.Cache

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class ImdbMovieProvider(url: String, headers: Map[String, String], apiKey: String)(implicit cache: Cache, httpClient: HttpClient)
  extends CachingMovieProvider with HttpMovieProvider {

  val baseUrl = "http://www.omdbapi.com/"
  override val cacheKeyPrefix = "imdb_"

  override def internalGetScore(movieName: String): Future[Score] = {
    val encodedMovieName = URLEncoder.encode(movieName, "UTF-8")
    val url = s"$baseUrl?apiKey=$apiKey&t=$encodedMovieName"

    httpClient.get[ImdbResponse](url, headers).map(response => Score(response.imdbRating))
  }
}
