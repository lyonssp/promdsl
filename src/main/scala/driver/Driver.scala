package driver

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
        ScrapeConfig named "ops-node-exporter" scrapes
          (ec2 inRegion "us-east-1" fromPort 80 every (30 seconds) withCredentials("access", "secret")) withRelabeling(

          __meta_ec2_availability_zone --> Label("az"),
          __meta_ec2_instance_id --> Label("id")

        )

    val yaml = config.toYaml

    println(yaml.prettyPrint)
  }
}