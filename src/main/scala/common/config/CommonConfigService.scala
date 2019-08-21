package common.config

import pureconfig.syntax._
import pureconfig.loadConfigOrThrow

case class CommonConfigService() extends ConfigService {

  override def loadConfig[A <: Config](filePath: String): A = {
    val config = loadConfigOrThrow[com.typesafe.config.Config](filePath)

    config.toOrThrow[A]
  }

}
