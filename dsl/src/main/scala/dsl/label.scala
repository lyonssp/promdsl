import job.ScrapeConfig

package object label {

  case class Label(s: String) {
    def -->(relabel: Label): RelabelConfig = ReplaceConfig(None, Labels(this), relabel)

    def -->(relabel: String): RelabelConfig = ReplaceConfig(None, Labels(this), Label(relabel))
  }

  case class Labels(ls: Label*) {
    def -->(l: Label): RelabelConfig = ReplaceConfig(None, Labels(ls: _*), l)

    def -->(s: String): RelabelConfig = this --> Label(s)
  }

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

  case class RelabelConfigs(list: Seq[RelabelConfig]) {
    def +(rc: RelabelConfig*): RelabelConfigs = RelabelConfigs(list ++ rc)
  }

  def relabel(sc: ScrapeConfig, rc: RelabelConfig*): ScrapeConfig = {
    sc.relabelConfig match {
      case None => sc.copy(relabelConfig = Some(RelabelConfigs(rc)))
      case Some(rcs) =>
        sc.copy(relabelConfig = Some(RelabelConfigs(rcs.list ++ rc.toSeq)))
    }
  }
}
