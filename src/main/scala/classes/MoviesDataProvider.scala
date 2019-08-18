package classes

import traits.MoviesDataProviderTrait

case class MoviesDataProvider(name: String, url: String,
                              parseOptions: MoviesDataProviderParseOptions,
                              headers: Map[String, String]) extends MoviesDataProviderTrait { }
