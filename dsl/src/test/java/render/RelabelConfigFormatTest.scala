package render

import dsl.{Label, Labels, ReplaceConfig}
import net.jcazevedo.moultingyaml.{YamlString, YamlValue}
import org.scalatest.FlatSpec
import render.PrometheusYamlProtocol.RelabelConfigFormat

class RelabelConfigFormatTest extends FlatSpec {
  "The protocol for rendering RelabelConfigs" should "produce the correct yaml" in {
    val config = ReplaceConfig(Some(".*"), Labels(Array(Label("foo"))), Label("bar"))

    val yaml = RelabelConfigFormat.write(config)
    val fields = yaml.asYamlObject.fields

    val regexField: Option[YamlValue] = fields.get(YamlString("regex"))
    assert(regexField.isDefined)
    regexField.get match {
      case YamlString(raw: String) => assert(raw == config.regex.get)
      case _ => fail()
    }

    val sourceLabelsField: Option[YamlValue] = fields.get(YamlString("source_labels"))
    assert(sourceLabelsField.isDefined)

    val actionField: Option[YamlValue] = fields.get(YamlString("action"))
    assert(actionField.isDefined)
  }
}
