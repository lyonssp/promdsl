import global.GlobalConfiguration
import job.scrape.ScrapeConfigs

package object prometheus {

  case class PrometheusConfiguration(globalConfiguration: Option[GlobalConfiguration], scrapeConfigs: ScrapeConfigs)

  def configure(scrapeConfigs: ScrapeConfigs)(implicit global: GlobalConfiguration): PrometheusConfiguration = {
    prometheus.PrometheusConfiguration(
      Some(global),
      scrapeConfigs
    )
  }
}


