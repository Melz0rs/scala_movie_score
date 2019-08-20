package common.config

trait ConfigService {
  def loadConfig[A <: Config](filePath: String, config: A): Unit
}
