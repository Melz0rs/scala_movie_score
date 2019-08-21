package common.config

import com.config.AppConfig
import pureconfig.syntax._
import pureconfig._

import scala.reflect.ClassTag

// TODO: Use semi auto
case class CommonConfigService() extends ConfigService {

  override def loadConfig[A <: Config : ConfigReader : ClassTag](): AppConfig  = { //A = {
  // TODO: Make it work
  //    val config = loadConfigOrThrow[com.typesafe.config.Config]
  //
  //    config.toOrThrow[A]

  AppConfig(imdbUrl = "http://www.omdbapi.com/",
    rtUrl = "",
    imdbApiKey = "67d579a3",
    rtApiKey = "",
    imdbHeaders = Map(),
    rtHeaders = Map())
  }
}
