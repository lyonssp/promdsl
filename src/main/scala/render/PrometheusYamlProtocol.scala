package render

import dsl._
import net.jcazevedo.moultingyaml._

object PrometheusYamlProtocol extends DefaultYamlProtocol {

  implicit object Ec2SdConfigYamlFormat extends YamlFormat[Ec2SdConfig] {
    override def write(obj: Ec2SdConfig): YamlValue =
      YamlObject(
        YamlString("region") -> YamlString(obj.region),
        YamlString("access_key") -> YamlString(obj.accessKey),
        YamlString("secret_key") -> YamlString(obj.secretKey),
        YamlString("refresh_interval") -> YamlString(obj.refreshInterval.toString),
        YamlString("port") -> YamlNumber(obj.port)
      )

    override def read(yaml: YamlValue): Ec2SdConfig = ???
  }

  implicit val staticConfigFormat = yamlFormat1(StaticConfig)

  implicit object RelabelConfigFormat extends YamlFormat[RelabelConfig] {
    override def write(obj: RelabelConfig): YamlValue = {
      val required = YamlObject(
        YamlString("action") -> YamlString(obj.action.name),
        YamlString("source_labels") -> YamlArray(obj.sourceLabels.ls.map(label => YamlString(label.s)): _*),
        YamlString("target_label") -> YamlString(obj.targetLabel.s)
      )
      obj.regex match {
        case None => required
        case Some(r) => required.copy(fields = required.fields.updated(YamlString("regex"), YamlString(r)))
      }
    }

    override def read(yaml: YamlValue): RelabelConfig = ???
  }

  implicit object RelabelConfigsFormat extends YamlFormat[RelabelConfigs] {
    override def write(obj: RelabelConfigs): YamlValue =
      YamlArray(obj.list.map(conf => conf.toYaml): _*)

    override def read(yaml: YamlValue): RelabelConfigs = ???
  }


  implicit object ScrapeConfigFormat extends YamlFormat[ScrapeConfig] with NullOptions {
    override def write(obj: ScrapeConfig): YamlValue = YamlObject(
      YamlString("job_name") -> YamlString(obj.jobName),
      YamlString("ec2_sd_config") -> obj.ec2SdConfig.toYaml,
      YamlString("relabel_config") -> obj.relabelConfig.toYaml
    )

    override def read(yaml: YamlValue): ScrapeConfig = ???
  }

  implicit object ScrapeConfigsFormat extends YamlFormat[ScrapeConfigs] with NullOptions {
    override def write(obj: ScrapeConfigs): YamlValue = YamlObject(
      YamlString("scrape_configs") -> YamlArray(obj.scrapeConfigs.map(config => config.toYaml): _*)
    )

    override def read(yaml: YamlValue): ScrapeConfigs = ???
  }

}
