package driver

import dsl.JobDecorators.relabel
import dsl.Ec2MetaLabels._
import dsl.Ec2SdConfigBuilder.ec2
import dsl._
import net.jcazevedo.moultingyaml._
import render.PrometheusYamlProtocol._

import scala.concurrent.duration._

object Driver {
  def main(args: Array[String]): Unit = {
    val config =
      ScrapeDefinition :=
        relabel(
          ScrapeConfig named "ops-node-exporter" scrapes {
            ec2 inRegion "us-east-1" fromPort 80 every (30 seconds) withCredentials("access", "secret")
          },
          __meta_ec2_availability_zone --> "az" =~ "us-east-1",
          __meta_ec2_instance_id --> "id"
        )

    val yaml = config.toYaml
    println(yaml.prettyPrint)
  }
}