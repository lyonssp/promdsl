import sbt._
import Keys._

object Common {
  val appVersion = "0.0.1"

  val settings: Seq[Def.Setting[_]] = Seq(
    version := appVersion,
    scalaVersion := "2.12.4",
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    resolvers += Opts.resolver.mavenLocalFile,
    resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
  )
}