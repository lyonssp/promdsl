package demo

import global.GlobalConfiguration
import job.scrape
import static.StaticConfigBuilder._
import prometheus.PrometheusConfiguration
import render.dumper.dump

import scala.concurrent.duration._

object DemoGlobal {
  def main(args: Array[String]): Unit = {
    /*
    global:
      scrape_interval: 30s
      scrape_timeout: 30s
      evaluation_interval: 30s
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
        Some(GlobalConfiguration(
          scrapeInterval = 30 seconds,
          scrapeTimeout = 30 seconds,
          ruleEvaluationInterval = 30 seconds,
          external_labels = None
        )),
        scrape :=
          job named "ec2-sd-demo" scrapes {
            targets("foo.com", "bar.com") labeled("foo" -> "bar", "baz" -> "qux")
          }
      )
    })
  }
}
