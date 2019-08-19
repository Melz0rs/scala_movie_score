package traits

trait MovieProvidersFactoryTrait {
  def apply(): Seq[MovieProvider]
}
