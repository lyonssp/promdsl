package dsl

object StaticConfigBuilder {
  def targets(list: String*): StaticConfigBuilder = StaticConfigBuilder(Some(list.toSeq), None)
}

case class StaticConfigBuilder(targets: Option[Seq[String]], lmap: Option[Map[String, String]]) {
  def labeled(tups: (String, String)*): StaticConfigBuilder = copy(lmap = Some(tups.foldLeft(Map.empty[String, String]) {
    case (map, (k, v)) => map.updated(k, v)
  }))

  def build: StaticConfig = StaticConfig(targets.getOrElse(Nil), lmap.getOrElse(Map.empty[String, String]))
}
