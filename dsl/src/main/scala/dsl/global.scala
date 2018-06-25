import scala.concurrent.duration.Duration

package object global {

  trait GlobalConfiguration {
    def scrapeInterval: Duration

    def scrapeTimeout: Duration

    def ruleEvaluationInterval: Duration

    def externalLabels: Option[Map[String, String]]
  }

}
