package com.movieProvidersPlugins

import com.classes.Score
import com.httpClient.HttpClient
import com.traits.Cache

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
    tryGetScoreFromCache(cacheKey) match {
      case Some(score) =>
        Future.successful(score)
      case _ => internalGetScore(movieName)
        .andThen({ case Success(value) => cache.set(cacheKey, value) })
    }
  }

  private def tryGetScoreFromCache(movieName: String)(implicit cache: Cache): Option[Score] = {
    cache.get[Score](movieName)
  }

}