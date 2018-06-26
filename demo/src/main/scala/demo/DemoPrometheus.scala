package demo

import aws.AwsCredentials
import aws.Ec2MetaLabels._
import aws.Ec2SdConfigBuilder.ec2
import global.GlobalConfiguration
import job.scrape
import label.relabel
import net.jcazevedo.moultingyaml._
import render.PrometheusYamlProtocol._
import rules.RulesConfiguration
import static.StaticConfigBuilder._

import scala.concurrent.duration._
import scala.language.postfixOps

object DemoPrometheus {

  implicit object MyGlobalConfiguration extends GlobalConfiguration {
    override val scrapeInterval: Duration = 30 seconds
    override val scrapeTimeout: Duration = 30 seconds
    override val ruleEvaluationInterval: Duration = 10 seconds
    override val externalLabels: Option[Map[String, String]] = None
  }

  implicit object MyRulesConfiguration extends RulesConfiguration {
    override def files: Seq[String] = Vector(
      "/etc/prometheus/rules/*.yml"
    )
  }

  def main(args: Array[String]): Unit = {

    val scrapes =
      scrape := (
        relabel(
          job named "node-exporter" scrapes {
            ec2 inRegion "us-east-1" fromPort 80 every (30 seconds) withCredentials AwsCredentials("access", "secret")
          },
          __meta_ec2_availability_zone --> "az" =~ "us-east-1",
          __meta_ec2_instance_id --> "id"
        ),
        job named "foosite" scrapes {
          targets("foo.com")
        }
      )


    val prom = prometheus.configure(scrapes)

    val yaml = prom.toYaml
    println(yaml.prettyPrint)
  }
}
