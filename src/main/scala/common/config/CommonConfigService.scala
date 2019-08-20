package common.config

case class CommonConfigService() extends ConfigService {

  override def loadConfig[A <: Config](filePath: String, config: A): Unit = {
    // TODO: Load config file and decode it to Config class
  }

}
