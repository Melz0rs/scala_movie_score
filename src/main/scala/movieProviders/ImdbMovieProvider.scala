package MovieProviders

import classes.{MoviesDataProvider, MoviesDataProviderParseOptions}

case class ImdbMovieProvider()
  extends MoviesDataProvider("imdb", "",
    MoviesDataProviderParseOptions("asd"),
    Map("a" -> "b"))  { }
