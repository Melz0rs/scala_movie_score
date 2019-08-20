package com.movieProviders

import com.cache.Cache
import com.classes.Score
import com.httpClient.HttpClient
import com.movieProvidersPlugins.MovieProvider
import com.traits.MovieProvidersFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class AvgMovieProvider(movieProvidersFactory: MovieProvidersFactory)(implicit
  httpClient: HttpClient,
  cache: Cache) extends MovieProvider {

  override def getScore(movieName: String): Future[Score] = {
    val movieProviders = movieProvidersFactory()
    val futureScores = Future.sequence(movieProviders.map(_.getScore(movieName)))

    for {
      // TODO: What if any of the futureScores fail?
      scores <- futureScores
      sum = scores.map(_.value).sum
    } yield Score(sum / movieProviders.size)
  }
}