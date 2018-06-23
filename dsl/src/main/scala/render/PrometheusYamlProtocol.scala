package render

import dsl._
import net.jcazevedo.moultingyaml._

import scala.concurrent.duration.Duration

object PrometheusYamlProtocol extends DefaultYamlProtocol {
  implicit def durationString(d: Duration): String = s"${d.toSeconds}s"

  implicit object Ec2SdConfigYamlFormat extends YamlFormat[Ec2SdConfig] {
    override def write(obj: Ec2SdConfig): YamlValue =
      YamlObject(
        YamlString("region") -> YamlString(obj.region),
        YamlString("access_key") -> YamlString(obj.accessKey),
        YamlString("secret_key") -> YamlString(obj.secretKey),
        YamlString("refresh_interval") -> YamlString(obj.refreshInterval),
        YamlString("port") -> YamlNumber(obj.port)
      )

    override def read(yaml: YamlValue): Ec2SdConfig = ???
  }

  implicit val staticConfigFormat = yamlFormat1(StaticConfig)

  implicit object RelabelConfigFormat extends YamlFormat[RelabelConfig] {
    override def write(obj: RelabelConfig): YamlValue = YamlObject(
      YamlString("action") -> YamlString(obj.action.name), YamlString("source_labels") -> YamlArray(obj.sourceLabels.ls.map(label => YamlString(label.s)): _*),
      YamlString("target_label") -> YamlString(obj.targetLabel.s),
      YamlString("regex") -> obj.regex.toYaml
    )

    override def read(yaml: YamlValue): RelabelConfig = ???
  }

  implicit object RelabelConfigsFormat extends YamlFormat[RelabelConfigs] {
    override def write(obj: RelabelConfigs): YamlValue =
      YamlArray(obj.list.map(conf => conf.toYaml): _*)

    override def read(yaml: YamlValue): RelabelConfigs = ???
  }


  implicit object ScrapeConfigFormat extends YamlFormat[job] {
    override def write(obj: job): YamlValue = prune(YamlObject(
      YamlString("job_name") -> YamlString(obj.name),
      YamlString("ec2_sd_config") -> obj.ec2SdConfig.toYaml,
      YamlString("relabel_config") -> obj.relabelConfig.toYaml
    ))

    override def read(yaml: YamlValue): job = ???
  }

  implicit object ScrapeConfigsFormat extends YamlFormat[ScrapeConfigs] {
    override def write(obj: ScrapeConfigs): YamlValue = YamlObject(
      YamlString("scrape_configs") -> YamlArray(obj.list.map(config => config.toYaml): _*)
    )

    override def read(yaml: YamlValue): ScrapeConfigs = ???
  }

  implicit object GlobalConfigsFormat extends YamlFormat[GlobalConfiguration] {
    override def write(obj: GlobalConfiguration): YamlValue = YamlObject(
      YamlString("scrape_interval") -> YamlString(obj.scrapeInterval),
      YamlString("scrape_timeout") -> YamlString(obj.scrapeInterval),
      YamlString("evaluation_interval") -> YamlString(obj.scrapeInterval)
    )

    override def read(yaml: YamlValue): GlobalConfiguration = ???
  }

  implicit object PrometheusConfigsFormat extends YamlFormat[PrometheusConfiguration] {
    override def write(obj: PrometheusConfiguration): YamlValue = prune(YamlObject(
      YamlString("global") -> obj.globalConfiguration.toYaml,
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
