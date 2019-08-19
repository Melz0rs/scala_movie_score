package movieProviders

import classes.Score
import httpClient.HttpClient
import movieProviders.responses.RottenTomatoesResponse
import traits.MovieProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class RottenTomatoesMovieProvider(url: String, headers: Map[String, String])
  extends MovieProvider with HttpMovieProvider {

  //  val baseUrl= "http://www.omdbapi.com/"

  override def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score] = {
    val url = ""

    httpClient.get[RottenTomatoesResponse](url, headers).map(response => Score(response.score))
  }

}
