package movieProviders

import java.net.URLEncoder

import classes.Score
import httpClient.HttpClient
import movieProviders.responses.ImdbResponse
import traits.MovieProvider

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class ImdbMovieProvider(url: String, headers: Map[String, String], apiKey: String)
  extends MovieProvider with HttpMovieProvider {

  val baseUrl = "http://www.omdbapi.com/"

  override def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score] = {
    val encodedMovieName = URLEncoder.encode(movieName, "UTF-8")
    val url = s"$baseUrl?apiKey=$apiKey&t=$encodedMovieName"

    httpClient.get[ImdbResponse](url, headers).map(response =>
      Score(response.imdbRating)
    )
  }

}
