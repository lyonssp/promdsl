import global.GlobalConfiguration

import scala.concurrent.duration._
import scala.language.postfixOps

package object configuration {

  implicit object MyGlobalConfiguration extends GlobalConfiguration {
    override val scrapeInterval: Duration = 30 seconds
    override val scrapeTimeout: Duration = 30 seconds
    override val ruleEvaluationInterval: Duration = 10 seconds
    override val externalLabels: Option[Map[String, String]] = None
  }

}
