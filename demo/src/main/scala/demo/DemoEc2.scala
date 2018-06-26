package demo

import aws.AwsCredentials
import aws.Ec2SdConfigBuilder._
import dsl.defaults
import job.scrape
import prometheus.PrometheusConfiguration
import render.dumper.dump

import scala.concurrent.duration._
import scala.language.postfixOps

object DemoEc2 {
  def main(args: Array[String]): Unit = {
    /*
    scrape_configs:
    - job_name: ec2-sd-demo
      ec2_sd_config:
        access_key: access
        port: 80
        secret_key: secret
        region: us-east-1
        refresh_interval: 30s
    */
    println(dump {
      PrometheusConfiguration(
        None,
        defaults.NoRulesConfiguration,
        scrape := {
          job named "ec2-sd-demo" scrapes {
            ec2 inRegion "us-east-1" fromPort 80 every (30 seconds) withCredentials AwsCredentials("access", "secret")
          }
        }
      )
    })
  }
}
