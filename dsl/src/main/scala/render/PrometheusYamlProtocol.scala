package render

import configuration.{Ec2Targets, StaticTargets}
import global.GlobalConfiguration
import job.ScrapeConfig
import job.scrape.ScrapeConfigs
import label.{RelabelConfig, RelabelConfigs}
import net.jcazevedo.moultingyaml._
import prometheus.PrometheusConfiguration
import rules.RulesConfiguration

import scala.concurrent.duration.Duration
import scala.language.implicitConversions

object PrometheusYamlProtocol extends DefaultYamlProtocol {
  implicit def durationString(d: Duration): String = s"${d.toSeconds}s"

  implicit object Ec2TargetsYamlFormat extends YamlFormat[Ec2Targets] {
    override def write(obj: Ec2Targets): YamlValue =
      YamlObject(
        YamlString("region") -> YamlString(obj.region),
        YamlString("access_key") -> YamlString(obj.accessKey),
        YamlString("secret_key") -> YamlString(obj.secretKey),
        YamlString("refresh_interval") -> YamlString(obj.refreshInterval),
        YamlString("port") -> YamlNumber(obj.port)
      )

    override def read(yaml: YamlValue): Ec2Targets = ???
  }

  implicit object StaticTargetsFormat extends YamlFormat[StaticTargets] {
    override def write(obj: StaticTargets): YamlValue = prune(YamlObject(
      YamlString("targets") -> obj.targets.toYaml,
      YamlString("labels") -> {
        if (obj.lmap.isEmpty)
          YamlNull
        else
          obj.lmap.toYaml
      }))

    override def read(yaml: YamlValue): StaticTargets = ???
  }

  implicit object RelabelConfigFormat extends YamlFormat[RelabelConfig] {
    override def write(obj: RelabelConfig): YamlValue = prune(YamlObject(
      YamlString("action") -> YamlString(obj.action.name), YamlString("source_labels") -> YamlArray(obj.sourceLabels.ls.map(label => YamlString(label.s)): _*),
      YamlString("target_label") -> YamlString(obj.targetLabel.s),
      YamlString("regex") -> obj.regex.map(r => r.toYaml).getOrElse(YamlNull)
    ))

    override def read(yaml: YamlValue): RelabelConfig = ???
  }

  implicit object RelabelConfigsFormat extends YamlFormat[RelabelConfigs] {
    override def write(obj: RelabelConfigs): YamlValue =
      YamlArray(obj.list.map(conf => conf.toYaml): _*)

    override def read(yaml: YamlValue): RelabelConfigs = ???
  }


  implicit object ScrapeConfigFormat extends YamlFormat[ScrapeConfig] {
    override def write(obj: ScrapeConfig): YamlValue = prune(YamlObject(
      YamlString("job_name") -> YamlString(obj.name),
      obj.scrapeTargets match {
        case st: StaticTargets => YamlString("static_config") -> st.toYaml
        case ec2: Ec2Targets => YamlString("ec2_sd_config") -> ec2.toYaml
      },
      YamlString("relabel_config") -> obj.relabelConfig.toYaml,
    ))

    override def read(yaml: YamlValue): ScrapeConfig = ???
  }

  implicit object ScrapeConfigsFormat extends YamlFormat[ScrapeConfigs] {
    override def write(obj: ScrapeConfigs): YamlValue = YamlObject(
      YamlString("scrape_configs") -> YamlArray(obj.list.map(config => config.toYaml): _*)
    )

    override def read(yaml: YamlValue): ScrapeConfigs = ???
  }

  implicit object GlobalConfigsFormat extends YamlFormat[GlobalConfiguration] {
    override def write(obj: GlobalConfiguration): YamlValue = prune(YamlObject(
      YamlString("scrape_interval") -> YamlString(obj.scrapeInterval),
      YamlString("scrape_timeout") -> YamlString(obj.scrapeTimeout),
      YamlString("evaluation_interval") -> YamlString(obj.ruleEvaluationInterval),
      YamlString("external_labels") -> obj.externalLabels.map(el => el.toYaml).getOrElse(YamlNull)
    ))

    override def read(yaml: YamlValue): GlobalConfiguration = ???
  }

  implicit object RulesConfigurationFormat extends YamlFormat[RulesConfiguration] {
    override def write(obj: RulesConfiguration): YamlValue = obj.toYaml

    override def read(yaml: YamlValue): RulesConfiguration = ???
  }

  implicit object PrometheusConfigsFormat extends YamlFormat[PrometheusConfiguration] {
    override def write(obj: PrometheusConfiguration): YamlValue = prune(YamlObject(
      YamlString("global") -> obj.globalConfiguration.toYaml,
      YamlString("rule_files") -> {
        obj.rulesConfiguration.files match {
          case Nil => YamlNull
          case ls => ls.toYaml
        }
      },
      YamlString("scrape_configs") -> YamlArray(obj.scrapeConfigs.list.map(config => config.toYaml): _*)
    ))

    override def read(yaml: YamlValue): PrometheusConfiguration = ???
  }

  private def prune(value: YamlValue): YamlValue = value match {
    case YamlArray(vs) => YamlArray(vs.map(prune))
    case YamlObject(fs) => YamlObject(fs.filter({
      case (_, v) => v != YamlNull
    }))
    case _ => value
  }
}
