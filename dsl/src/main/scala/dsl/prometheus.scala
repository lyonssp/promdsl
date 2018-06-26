import global.GlobalConfiguration
import job.scrape.ScrapeConfigs
import rules.RulesConfiguration

package object prometheus {

  case class PrometheusConfiguration(globalConfiguration: Option[GlobalConfiguration], rulesConfiguration: RulesConfiguration, scrapeConfigs: ScrapeConfigs)

  def configure(scrapeConfigs: ScrapeConfigs)(implicit global: GlobalConfiguration, rules: RulesConfiguration): PrometheusConfiguration = {
    prometheus.PrometheusConfiguration(
      Some(global),
      rules,
      scrapeConfigs
    )
  }
}


