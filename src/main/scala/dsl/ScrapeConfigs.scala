package dsl

object ScrapeDefinition {
  def :=(sc: ScrapeConfig*): ScrapeConfigs = ScrapeConfigs(sc.toSeq)
}

case class ScrapeConfigs(scrapeConfigs: Seq[ScrapeConfig])
