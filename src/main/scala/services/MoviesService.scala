package services

import classes.{ MoviesDataProvider, MoviesDataProviderParseOptions }
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

// TODO Make it class singleton
object MoviesService {

  val moviesDataProvidersClients = new ListBuffer[MoviesDataProviderClient]

  def apply(): Unit = {
    // TODO: get data providers from conf and init them
    val imdbDataProvider =
      new MoviesDataProvider("imdb", "", parseOptions = MoviesDataProviderParseOptions("score")) // TODO: From configuration
    val imdbDataProviderClient = new MoviesDataProviderClient(imdbDataProvider, (e) => println(e), Map("auth" -> "abs")) // TODO: From configurqtion

    moviesDataProvidersClients += imdbDataProviderClient
  }

  def getScore(movieName: String): Double = {

    val scoresFutures: Seq[Future[Option[Double]]] =
      moviesDataProvidersClients.map(dataProvider => Future(dataProvider.getScore(movieName)))

    val FutureScores = Future.sequence(scoresFutures)
    val scoresOptions = Await.result(FutureScores, 5.seconds) // TODO Change to onComplete / map / for comprehension
    val scores = scoresOptions.flatten
    val avg = scores.sum / scores.length

    avg
  }
}