package demo

import global.GlobalConfiguration
import job.scrape
import render.dumper.dump
import static.StaticConfigBuilder._

import scala.concurrent.duration._
import scala.language.postfixOps

object DemoGlobal {

  implicit object MyGlobalConfiguration extends GlobalConfiguration {
    override def scrapeInterval: Duration = 100 seconds

    override def scrapeTimeout: Duration = 100 seconds

    override def ruleEvaluationInterval: Duration = 100 seconds

    override def externalLabels: Option[Map[String, String]] = Some(Map(
      "foo" -> "bar",
      "baz" -> "qux"
    ))
  }

  def main(args: Array[String]): Unit = {
    /*
    global:
      scrape_interval: 30s
      scrape_timeout: 30s
      evaluation_interval: 30s
    scrape_configs:
    - job_name: ec2-sd-demo
      static_config:
        targets:
        - foo.com
        - bar.com
        labels:
          foo: bar
          baz: qux
    */
    import dsl.defaults.NoRulesConfiguration
    println(dump {
      prometheus.configure(
        scrape :=
          job named "global-demo" scrapes {
            targets("foo.com")
          })
    })
  }
}
