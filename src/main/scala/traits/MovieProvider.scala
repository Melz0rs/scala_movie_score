package traits

import cache.Cache
import classes.Score
import httpClient.HttpClient

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

trait MovieProvider {

  val cacheKeyPrefix: String

  def getScore(movieName: String)(implicit httpClient: HttpClient,
                                  executionContext: ExecutionContext): Future[Score] = {
    val cacheKey = cacheKeyPrefix + movieName

    tryGetScoreFromCache(cacheKey) match {
      case Some(score) =>
        Future.successful(score)
      case _ =>
        internalGetScore(movieName)
          .andThen({ case Success(value) => Cache.set(cacheKey, value)})
    }
  }

  def internalGetScore(movieName: String)(implicit httpClient: HttpClient): Future[Score]

  private def tryGetScoreFromCache(movieName: String): Option[Score] = {
    Cache.get[Score](movieName)
  }
}
