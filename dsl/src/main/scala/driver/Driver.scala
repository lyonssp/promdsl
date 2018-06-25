package driver

import aws.AwsCredentials
import aws.Ec2MetaLabels._
import aws.Ec2SdConfigBuilder.ec2
import job.scrape
import label.relabel
import net.jcazevedo.moultingyaml._
import render.PrometheusYamlProtocol._
import configuration._

import scala.concurrent.duration._
import scala.language.postfixOps

object Driver {
  def main(args: Array[String]): Unit = {
    val scrapes =
      scrape :=
        relabel(
          job named "ops-node-exporter" scrapes {
            ec2 inRegion "us-east-1" fromPort 80 every (30 seconds) withCredentials AwsCredentials("access", "secret")
          },
          __meta_ec2_availability_zone --> "az" =~ "us-east-1",
          __meta_ec2_instance_id --> "id"
        )

    val prom = prometheus.configure(scrapes)

    val yaml = prom.toYaml
    println(yaml.prettyPrint)
  }
}
