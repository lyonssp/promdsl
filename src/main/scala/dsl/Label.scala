package dsl

case class Label(s: String) {
  def -->(relabel: Label): RelabelConfig = ReplaceConfig(None, Labels(Array(this)), relabel)
}

