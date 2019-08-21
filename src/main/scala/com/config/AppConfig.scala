package com.config

import common.config.Config

case class AppConfig(
  imdbUrl: String,
  rtUrl: String,
  imdbApiKey: String,
  rtApiKey: String,
  imdbHeaders: Map[String, String],
  rtHeaders: Map[String, String]) extends Config