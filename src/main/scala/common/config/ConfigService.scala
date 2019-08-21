package common.config

import com.config.AppConfig
import pureconfig.ConfigReader

import scala.reflect.ClassTag

trait ConfigService {
  def loadConfig[A <: Config: ConfigReader: ClassTag](): AppConfig//A
}
