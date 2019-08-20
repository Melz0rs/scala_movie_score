package com.movieProviders

import java.net.URLEncoder

import com.cache.Cache
import com.classes.Score
import com.httpClient.HttpClient
import com.movieProviders.responses.RottenTomatoesResponse
import com.movieProvidersPlugins.{CachingMovieProvider, HttpMovieProvider}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class RottenTomatoesMovieProvider(url: String, headers: Map[String, String], apiKey: String)(implicit cache: Cache, httpClient: HttpClient)
  extends CachingMovieProvider with HttpMovieProvider {

  val baseUrl = "http://com.api.rottentomatoes.com/com.api/public/v1.0/movies.json"

  override val cacheKeyPrefix: String = "rt_"

  override def internalGetScore(movieName: String): Future[Score] = {
    val encodedMovieName = URLEncoder.encode(movieName, "UTF-8")
    val url = s"$baseUrl?apikey=$apiKey&q=$encodedMovieName"

    httpClient.get[RottenTomatoesResponse](url, headers).map(response => Score(response.score))
  }
}
