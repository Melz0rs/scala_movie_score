package com.config

trait ConfigService {
  def loadConfig(filePath: String): Unit
}
