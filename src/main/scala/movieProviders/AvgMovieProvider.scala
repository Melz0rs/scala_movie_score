package movieProviders

import classes.Score
import httpClient.HttpClient
import traits.{ MovieProvider, MovieProvidersFactory }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class AvgMovieProvider(movieProvidersFactory: MovieProvidersFactory) extends MovieProvider {

  override val cacheKeyPrefix: String = "avg_"

  override def internalGetScore(movieName: String)(implicit httpClient: HttpClient): Future[Score] = {
    val movieProviders = movieProvidersFactory()
    val futureScores = Future.sequence(movieProviders.map(_.getScore(movieName)))

    for {
      // TODO: What if any of the futureScores fail?
      scores <- futureScores
      sum = scores.map(_.value).sum
    } yield Score(sum / movieProviders.size)
  }
}