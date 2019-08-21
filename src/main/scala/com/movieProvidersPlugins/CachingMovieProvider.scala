package com.movieProvidersPlugins

import com.cache.Cache
import com.classes.Score
import com.httpClient.HttpClient

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Success

abstract class CachingMovieProvider()(implicit
  cache: Cache,
  httpClient: HttpClient,
  executionContext: ExecutionContext) extends MovieProvider {

  val cacheKeyPrefix: String

  def internalGetScore(str: String): Future[Score]

  override def getScore(movieName: String): Future[Score] = {
    val cacheKey = cacheKeyPrefix + movieName

    tryGetScoreFromCache(cacheKey).flatMap {
      case Some(score) =>
        Future.successful(score)
      case _ =>
        internalGetScore(movieName)
          .andThen({ case Success(value) => cache.set(cacheKey, value) })
    }
  }

  private def tryGetScoreFromCache(movieName: String): Future[Option[Score]] = {
    cache.get[Score](movieName)
  }

}