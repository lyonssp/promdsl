package dsl

object JobDecorators {
  def relabel(sc: ScrapeConfig, rc: RelabelConfig*): ScrapeConfig = {
    sc.relabelConfig match {
      case None => sc.copy(relabelConfig = Some(RelabelConfigs(rc)))
      case Some(rcs) =>
        sc.copy(relabelConfig = Some(RelabelConfigs(rcs.list ++ rc.toSeq)))
    }
  }
}
