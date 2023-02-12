import Dependencies._

val scala3Version = "3.2.2"

lazy val zioSessions = project
  .in(file("."))
  .settings(name := "zio-sessions")
  .settings(scalaVersion := scala3Version)
  .settings(libraryDependencies ++= backendDeps)


