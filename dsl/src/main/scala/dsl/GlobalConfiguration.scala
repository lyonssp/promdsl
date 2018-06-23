package dsl

import scala.concurrent.duration.Duration

case class GlobalConfiguration(scrapeInterval: Duration, scrapeTimeout: Duration, ruleEvaluationInterval: Duration, external_labels: Option[Labels])
