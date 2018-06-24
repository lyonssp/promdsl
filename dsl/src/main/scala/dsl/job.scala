import aws.{Ec2SdConfig, Ec2SdConfigBuilder}
import label.RelabelConfigs
import static.{StaticConfig, StaticConfigBuilder}

package object job {
  def named(name: String): ScrapeConfig = ScrapeConfig(name, None, None, None)

  case class ScrapeConfig(name: String, staticConfig: Option[StaticConfig], ec2SdConfig: Option[Ec2SdConfig], relabelConfig: Option[RelabelConfigs]) {
    def scrapes(ec2SdConfigBuilder: => Ec2SdConfigBuilder): ScrapeConfig = copy(ec2SdConfig = Some(ec2SdConfigBuilder.build))

    def scrapes(staticConfigBuilder: StaticConfigBuilder): ScrapeConfig = copy(staticConfig = Some(staticConfigBuilder.build))
  }

  object scrape {
    def :=(sc: ScrapeConfig*): ScrapeConfigs = ScrapeConfigs(sc.toSeq)

    case class ScrapeConfigs(list: Seq[ScrapeConfig])

  }

}

