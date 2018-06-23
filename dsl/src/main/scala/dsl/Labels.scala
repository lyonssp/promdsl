package dsl

case class Labels(ls: Label*) {
  def -->(l: Label): RelabelConfig = ReplaceConfig(None, Labels(ls: _*), l)

  def -->(s: String): RelabelConfig = this --> Label(s)
}
