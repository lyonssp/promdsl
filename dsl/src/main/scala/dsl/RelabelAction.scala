package dsl

sealed trait RelabelAction {
  def name: String
}

case object keep extends RelabelAction {
  def name: String = "keep"
}

case object drop extends RelabelAction {
  def name: String = "drop"
}

case object replace extends RelabelAction {
  def name: String = "replace"
}
