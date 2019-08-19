package factories

import movieProviders.{ImdbMovieProvider, RottenTomatoesMovieProvider}
import traits.{MovieProvider, MovieProvidersFactoryTrait}

object MovieProvidersFactory extends MovieProvidersFactoryTrait {

  def apply(): Seq[MovieProvider] = {
    // TODO: Get url and headers from conf
    Seq(ImdbMovieProvider("imdb.com", Map()), RottenTomatoesMovieProvider("rt.com", Map()))
  }

}
