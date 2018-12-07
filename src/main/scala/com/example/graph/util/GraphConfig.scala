package com.example.graph.util

import com.typesafe.config.{Config, ConfigFactory}

trait GraphConfig {
  val conf: Config = ConfigFactory.load().getConfig("app")
  val graphName = conf.getString("graphName")
  val host = conf.getString("host")
}
