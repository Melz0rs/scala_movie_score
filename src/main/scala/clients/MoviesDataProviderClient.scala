package clients

import classes.MoviesDataProvider
import utils.MoviesData
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class MoviesDataProviderClient(dataProvider: MoviesDataProvider, onError: Exception => Unit, headers: Map[String, String])
      extends BaseHttpClient(onError, headers) {

  private val baseUrl = dataProvider.url

  def getScore(movieName: String): Double = {
    val getFuture = get(baseUrl.format(movieName))
    val movieData = Await.result(getFuture, 5.seconds) // TODO: Timeout from conf

    MoviesData.parseScore(movieData, dataProvider.parseOptions) // TODO: What if the request fails??
  }
}
