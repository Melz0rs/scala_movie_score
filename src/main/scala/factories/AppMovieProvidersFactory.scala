package factories

import httpClient.HttpClient
import movieProviders.{ ImdbMovieProvider, RottenTomatoesMovieProvider }
import movieProvidersPlugins.MovieProvider
import traits.{ Cache, MovieProvidersFactory }

case class AppMovieProvidersFactory(implicit httpClient: HttpClient, cache: Cache) extends MovieProvidersFactory {

  def apply(): Seq[MovieProvider] = {
    // TODO: Get url, headers and keys from conf
    Seq(
      ImdbMovieProvider("imdb.com", Map(), "67d579a3")
    //        RottenTomatoesMovieProvider("rt.com", Map(), "bjxqspq7j27u5ay6257yh6hz")
    )
  }

}
