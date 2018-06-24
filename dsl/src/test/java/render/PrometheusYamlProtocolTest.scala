package render

import net.jcazevedo.moultingyaml.YamlString
import org.scalatest.FlatSpec
import static.StaticConfig

class PrometheusYamlProtocolTest extends FlatSpec {
  "A static config" should "exclude labels when none are given" in {
    val conf = StaticConfig(Array("foo.com", "bar.com"), Map.empty)

    val yaml = PrometheusYamlProtocol.StaticConfigFormat.write(conf)

    val yamlObj = yaml.asYamlObject
    assert(yamlObj.getFields(YamlString("labels")).isEmpty)
  }

  it should "include labels when they are given" in {
    val conf = StaticConfig(Array("foo.com", "bar.com"), Map("foo" -> "bar"))

    val yaml = PrometheusYamlProtocol.StaticConfigFormat.write(conf)

    val yamlObj = yaml.asYamlObject
    assert(yamlObj.fields.contains(YamlString("labels")))

    val labelsObj = yamlObj.fields(YamlString("labels")).asYamlObject
    assert(labelsObj.getFields(YamlString("foo")).contains(YamlString("bar")))
  }
}
