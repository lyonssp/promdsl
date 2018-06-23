package dsl

object ScrapeJobs {
  def :=(sc: job*): ScrapeConfigs = ScrapeConfigs(sc.toSeq)
}

