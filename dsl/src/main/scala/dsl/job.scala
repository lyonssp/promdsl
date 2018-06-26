import aws.Ec2SdConfigBuilder
import label.RelabelConfigs
import static.StaticConfigBuilder

package object job {
  def named(name: String): ScrapeConfig = ScrapeConfig(name, null, None)

  case class ScrapeConfig(name: String, scrapeTargets: configuration.ScrapeTargets, relabelConfig: Option[RelabelConfigs]) {
    def scrapes(ec2SdConfigBuilder: => Ec2SdConfigBuilder): ScrapeConfig = copy(scrapeTargets = ec2SdConfigBuilder.build)

    def scrapes(staticConfigBuilder: StaticConfigBuilder): ScrapeConfig = copy(scrapeTargets = staticConfigBuilder.build)
  }

  object scrape {
    def :=(sc: ScrapeConfig*): ScrapeConfigs = ScrapeConfigs(sc.toSeq)

    case class ScrapeConfigs(list: Seq[ScrapeConfig])

  }

}

