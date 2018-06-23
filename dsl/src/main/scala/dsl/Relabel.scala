package dsl

abstract class RelabelConfig(val regex: Option[String]) {
  def =~(r: String): RelabelConfig

  def action: RelabelAction

  def sourceLabels: Labels

  def targetLabel: Label
}

case class KeepConfig(override val regex: Option[String], sourceLabels: Labels, targetLabel: Label) extends RelabelConfig(regex) {
  def action: RelabelAction = keep

  override def =~(r: String): RelabelConfig = copy(regex = Some(r))
}

case class DropConfig(override val regex: Option[String], sourceLabels: Labels, targetLabel: Label) extends RelabelConfig(regex) {
  def action: RelabelAction = drop

  override def =~(r: String): RelabelConfig = copy(regex = Some(r))
}

case class ReplaceConfig(override val regex: Option[String], sourceLabels: Labels, targetLabel: Label) extends RelabelConfig(regex) {
  def action: RelabelAction = replace

  override def =~(r: String): RelabelConfig = copy(regex = Some(r))
}
