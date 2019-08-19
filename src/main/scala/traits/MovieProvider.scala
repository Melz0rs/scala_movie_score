package traits

import cache.Cache
import classes.Score
import httpClient.HttpClient

import scala.concurrent.Future

trait MovieProvider {

  val cacheKeyPrefix: String

  def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score] = {
    val cacheKey = cacheKeyPrefix + movieName

    tryGetScoreFromCache(cacheKey) match {
      case Some(value) => Future.successful(value)
      case _ =>
        val score = internalGetScore(movieName)
        Cache.set(cacheKey, score)

        score
    }
  }

  def internalGetScore(movieName: String)(implicit httpClient: HttpClient): Future[Score]

  private def tryGetScoreFromCache(movieName: String): Option[Score] = {
    Cache.get[Score](movieName)
  }
}
