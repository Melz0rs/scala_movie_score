package com.traits

import com.movieProvidersPlugins.MovieProvider

trait MovieProvidersFactory {
  def apply(): Seq[MovieProvider]
}
