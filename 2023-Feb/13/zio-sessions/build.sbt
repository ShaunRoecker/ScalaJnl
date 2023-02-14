import Dependencies._

val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(name := "zio-sessions")
  .settings(scalaVersion := scala3Version)
  .settings(libraryDependencies ++= backendDeps)



  


