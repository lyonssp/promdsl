package dsl

object job {
  def named(name: String): job = job(name, None, None, None)
}

case class job(name: String, staticConfig: Option[StaticConfig], ec2SdConfig: Option[Ec2SdConfig], relabelConfig: Option[RelabelConfigs]) {
  def scrapes(ec2SdConfigBuilder:  => Ec2SdConfigBuilder): job = copy(ec2SdConfig = Some(ec2SdConfigBuilder.build))

  def withRelabeling(rc: RelabelConfig*): job = copy(relabelConfig = Some(RelabelConfigs(rc.toSeq)))
}
