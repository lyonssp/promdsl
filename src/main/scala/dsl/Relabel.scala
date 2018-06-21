package dsl

abstract class RelabelConfig {
  def matching: String

  def action: RelabelAction

  def sourceLabels: Labels

  def targetLabel: Label
}

case class KeepConfig(regex: Option[String], sourceLabels: Labels, targetLabel: Label) extends RelabelConfig {
  def action: RelabelAction = keep

  override def matching: String = regex.getOrElse(".*")
}

case class DropConfig(regex: Option[String], sourceLabels: Labels, targetLabel: Label) extends RelabelConfig {
  def action: RelabelAction = drop

  override def matching: String = regex.getOrElse(".*")
}

case class ReplaceConfig(regex: Option[String], sourceLabels: Labels, targetLabel: Label) extends RelabelConfig {
  def action: RelabelAction = replace

  override def matching: String = regex.getOrElse(".*")
}
