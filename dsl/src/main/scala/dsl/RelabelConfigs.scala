package dsl

case class RelabelConfigs(list: Seq[RelabelConfig]) {
  def +(rc: RelabelConfig*): RelabelConfigs = RelabelConfigs(list ++ rc)
}
