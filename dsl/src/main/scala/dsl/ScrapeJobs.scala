package dsl

object ScrapeJobs {
  def :=(sc: ScrapeConfig*): ScrapeConfigs = ScrapeConfigs(sc.toSeq)
}

