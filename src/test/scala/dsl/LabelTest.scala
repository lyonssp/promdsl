package dsl

import dsl.Ec2MetaLabels._
import org.scalatest._

class LabelTest extends FlatSpec {
  "A label" should "target the correct " in {
    val config = __meta_ec2_availability_zone --> Label("az")

    assert(config.action == replace)
    assert(config.targetLabel.s == "az")
  }
}