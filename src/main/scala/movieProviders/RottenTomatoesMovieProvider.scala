package movieProviders

import java.net.URLEncoder

import classes.Score
import httpClient.HttpClient
import movieProviders.responses.RottenTomatoesResponse
import movieProvidersPlugins.{ CachingMovieProvider, HttpMovieProvider }
import traits.Cache

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class RottenTomatoesMovieProvider(url: String, headers: Map[String, String], apiKey: String)(implicit cache: Cache, httpClient: HttpClient)
  extends CachingMovieProvider with HttpMovieProvider {

  val baseUrl = "http://api.rottentomatoes.com/api/public/v1.0/movies.json"

  override val cacheKeyPrefix: String = "rt_"

  override def internalGetScore(movieName: String): Future[Score] = {
    val encodedMovieName = URLEncoder.encode(movieName, "UTF-8")
    val url = s"$baseUrl?apikey=$apiKey&q=$encodedMovieName"

    httpClient.get[RottenTomatoesResponse](url, headers).map(response => Score(response.score))
  }
}
