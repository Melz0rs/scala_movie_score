package common.app

import common.config.{Config, ConfigService}

trait ConfigApp extends CommonApp {

  def getConfig: () => Config
  def getConfigService: () => ConfigService
  def getConfigFilePath:() => String

  getConfigService().loadConfig(getConfigFilePath(), getConfig())
}
