package traits

import classes.Score
import httpClient.HttpClient

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

trait MovieProvider {

  val cacheKeyPrefix: String

  def getScore(movieName: String): Future[Score]

  def getScore1(movieName: String)(implicit httpClient: HttpClient,
                                  executionContext: ExecutionContext,
                                  cache: Cache): Future[Score] = {
    val cacheKey = cacheKeyPrefix + movieName

    tryGetScoreFromCache(cacheKey) match {
      case Some(score) =>
        Future.successful(score)
      case _ =>
        internalGetScore(movieName)
          .andThen({ case Success(value) => cache.set(cacheKey, value)})
    }
  }

  def internalGetScore(movieName: String)(implicit httpClient: HttpClient): Future[Score]

  private def tryGetScoreFromCache(movieName: String)(implicit cache: Cache): Option[Score] = {
    cache.get[Score](movieName)
  }
}


class CachingMoviesProvider(cache: Cache, prefix: String, underlying: MovieProvider) extends MovieProvider {
  override val cacheKeyPrefix: String = _

  override def getScore(movieName: String): Future[Score] = {
    val cacheKey = prefix + movieName
    cache.get[Score](cacheKey) match {
      case Some(score) =>
        Future.successful(score)
      case _ =>
        underlying.getScore(movieName)
    }
  }
}