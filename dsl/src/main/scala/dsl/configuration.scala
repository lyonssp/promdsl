import scala.concurrent.duration._
import scala.language.postfixOps

package object configuration {

  abstract class ScrapeTargets {
    def kind: String
  }

  case class Ec2Targets(region: String, accessKey: String, secretKey: String, refreshInterval: Duration, port: Int) extends ScrapeTargets {
    override def kind: String = "ec2_sd_config"
  }

  case class StaticTargets(targets: Seq[String], lmap: Map[String, String]) extends ScrapeTargets {
    override def kind: String = "static_config"
  }

}
