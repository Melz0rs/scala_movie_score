package com.movieProviders

import java.net.URLEncoder

import com.cache.Cache
import com.classes.Score
import com.httpClient.HttpClient
import com.movieProviders.responses.ImdbResponse
import com.movieProvidersPlugins.{ CachingMovieProvider, HttpMovieProvider }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class ImdbMovieProvider(url: String, headers: Map[String, String], apiKey: String)(implicit cache: Cache, httpClient: HttpClient)
  extends CachingMovieProvider with HttpMovieProvider {

  override val cacheKeyPrefix = "imdb_"

  override def internalGetScore(movieName: String): Future[Score] = {
    val encodedMovieName = URLEncoder.encode(movieName, "UTF-8")
    val url = s"$url?apiKey=$apiKey&t=$encodedMovieName"

    httpClient.get[ImdbResponse](url, headers).map(response => Score(response.imdbRating))
  }
}
