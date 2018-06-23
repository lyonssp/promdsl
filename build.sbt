organization := "com.github.lyonssp"
name := "promdsl"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.4"

lazy val dsl = project
  .settings(Common.settings: _*)
  .settings(libraryDependencies ++= Dependencies.dslDependencies)
