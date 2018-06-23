package render

import dsl.PrometheusConfiguration
import net.jcazevedo.moultingyaml._
import render.PrometheusYamlProtocol._

object dumper {

  def dump(pc: PrometheusConfiguration): String = pc.toYaml.prettyPrint

  def dump[A](a: A)(implicit format: YamlFormat[A]): String = a.toYaml.prettyPrint
}
