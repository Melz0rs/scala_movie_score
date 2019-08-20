package com.factories

import com.cache.Cache
import com.config.AppConfig
import com.httpClient.HttpClient
import com.movieProviders.{ ImdbMovieProvider, RottenTomatoesMovieProvider }
import com.movieProvidersPlugins.MovieProvider
import com.traits.MovieProvidersFactory

case class AppMovieProvidersFactory(implicit httpClient: HttpClient, cache: Cache) extends MovieProvidersFactory {

  def apply(): Seq[MovieProvider] = {
    // TODO: Get url, headers and keys from conf
    Seq(
      ImdbMovieProvider(AppConfig.imdbUrl, AppConfig.imdbHeaders, AppConfig.imdbApiKey)
    //        RottenTomatoesMovieProvider("rt.com", Map(), "bjxqspq7j27u5ay6257yh6hz")
    )
  }

}
