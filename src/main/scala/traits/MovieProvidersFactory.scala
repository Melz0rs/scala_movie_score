package traits

import movieProvidersPlugins.MovieProvider

trait MovieProvidersFactory {
  def apply(): Seq[MovieProvider]
}
