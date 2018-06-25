package dsl

import global.GlobalConfiguration

import scala.concurrent.duration._
import scala.language.postfixOps

object defaults {

  implicit object DefaultGlobalConfiguration extends GlobalConfiguration {

    override def scrapeInterval: Duration = 30 seconds

    override def scrapeTimeout: Duration = 30 seconds

    override def ruleEvaluationInterval: Duration = 30 seconds

    override def externalLabels: Option[Map[String, String]] = None
  }

}
