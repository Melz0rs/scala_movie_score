package movieProviders

import classes.Score
import clients.HttpClient
import movieProviders.responses.ImdbResponse
import traits.MovieProvider
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class ImdbMovieProvider(url: String, headers: Map[String, String], apiKey: String)
  extends MovieProvider with HttpMovieProvider {

    val baseUrl= "http://www.omdbapi.com/"

    override def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score] = {
        val url = s"$baseUrl?apiKey=$apiKey&t=$movieName"

        httpClient.get[ImdbResponse](url, headers).map(response => Score(response.imdbRating))
    }

}
