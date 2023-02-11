import Dependencies._

val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(name := "zio-sessions")
  .settings(version := "0.1.0-SNAPSHOT")
  .settings(scalaVersion := scala3Version)
  .settings(libraryDependencies ++= backendDeps)


