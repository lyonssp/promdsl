import sbt._

object Dependencies {
  val dslDependencies: Seq[ModuleID] = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
    "org.scalactic" %% "scalactic" % "3.0.5",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    "net.jcazevedo" %% "moultingyaml" % "0.4.0"
  )
}

