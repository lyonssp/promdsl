package driver

import aws.AwsCredentials
import aws.Ec2MetaLabels._
import aws.Ec2SdConfigBuilder.ec2
import global.GlobalConfiguration
import job.scrape
import label.relabel
import net.jcazevedo.moultingyaml._
import prometheus.PrometheusConfiguration
import render.PrometheusYamlProtocol._

import scala.concurrent.duration._
import scala.language.postfixOps

object Driver {
  def main(args: Array[String]): Unit = {
    val global =
      GlobalConfiguration(
        scrapeInterval = 30 seconds,
        scrapeTimeout = 30 seconds,
        ruleEvaluationInterval = 30 seconds,
        external_labels = None
      )

    val scrapes =
      scrape :=
        relabel(
          job named "ops-node-exporter" scrapes {
            ec2 inRegion "us-east-1" fromPort 80 every (30 seconds) withCredentials AwsCredentials("access", "secret")
          },
          __meta_ec2_availability_zone --> "az" =~ "us-east-1",
          __meta_ec2_instance_id --> "id"
        )

    val prometheus = PrometheusConfiguration(
      Some(global),
      scrapes
    )

    val yaml = prometheus.toYaml
    println(yaml.prettyPrint)
  }
}
