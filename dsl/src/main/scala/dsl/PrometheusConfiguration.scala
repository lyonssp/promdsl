package dsl

case class PrometheusConfiguration(globalConfiguration: Option[GlobalConfiguration], scrapeConfigs: ScrapeConfigs)
