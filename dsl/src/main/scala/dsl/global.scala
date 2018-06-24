import label.Labels

import scala.concurrent.duration.Duration

package object global {

  case class GlobalConfiguration(scrapeInterval: Duration, scrapeTimeout: Duration, ruleEvaluationInterval: Duration, external_labels: Option[Labels])

}

