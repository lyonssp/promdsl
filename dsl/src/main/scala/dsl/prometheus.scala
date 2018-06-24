import global.GlobalConfiguration
import job.scrape.ScrapeConfigs

package object prometheus {

  case class PrometheusConfiguration(globalConfiguration: Option[GlobalConfiguration], scrapeConfigs: ScrapeConfigs)

}


