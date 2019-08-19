package movieProviders

import classes.Score
import httpClient.HttpClient
import traits.{ MovieProvider, MovieProvidersFactory }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class AvgMovieProvider(movieProvidersFactory: MovieProvidersFactory) extends MovieProvider {
  override def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score] = {
    val movieProviders = movieProvidersFactory()
    val futureScores = Future.sequence(movieProviders.map(_.getScore(movieName)))

    for {
      scores <- futureScores
      sum = scores.map(_.value).sum
    } yield Score(sum / movieProviders.size)
  }
}
