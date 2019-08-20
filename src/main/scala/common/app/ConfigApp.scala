package common.app

import common.config.{ Config, ConfigService }

trait ConfigApp extends CommonApp {
  val config: Config
  val configService: ConfigService
  val configFilePath: String

  configService.loadConfig(configFilePath, config)
}