package traits

trait MovieProvidersFactory {
  def apply(): Seq[MovieProvider]
}
