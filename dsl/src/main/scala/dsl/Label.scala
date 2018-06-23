package dsl

case class Label(s: String) {
  def -->(relabel: Label): RelabelConfig = ReplaceConfig(None, Labels(this), relabel)

  def -->(relabel: String): RelabelConfig = ReplaceConfig(None, Labels(this), Label(relabel))
}

