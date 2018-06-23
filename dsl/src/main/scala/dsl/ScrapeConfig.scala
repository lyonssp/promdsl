package dsl

object ScrapeConfig {
  def named(name: String): ScrapeConfig = ScrapeConfig(name, None, None, None)
}

case class ScrapeConfig(jobName: String, staticConfig: Option[StaticConfig], ec2SdConfig: Option[Ec2SdConfig], relabelConfig: Option[RelabelConfigs]) {
  def scrapes(ec2SdConfigBuilder:  => Ec2SdConfigBuilder): ScrapeConfig = copy(ec2SdConfig = Some(ec2SdConfigBuilder.build))

  def withRelabeling(rc: RelabelConfig*): ScrapeConfig = copy(relabelConfig = Some(RelabelConfigs(rc.toSeq)))
}
