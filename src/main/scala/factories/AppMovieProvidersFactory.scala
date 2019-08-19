package factories

import movieProviders.{ ImdbMovieProvider, RottenTomatoesMovieProvider }
import traits.{ MovieProvider, MovieProvidersFactory }

object AppMovieProvidersFactory extends MovieProvidersFactory {

  def apply(): Seq[MovieProvider] = {
    // TODO: Get url, headers and keys from conf
    Seq(ImdbMovieProvider("imdb.com", Map(), "67d579a3") /*, RottenTomatoesMovieProvider("rt.com", Map())*/ )
  }

}
