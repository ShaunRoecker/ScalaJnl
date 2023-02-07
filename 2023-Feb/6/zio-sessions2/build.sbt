
Global / excludeLintKeys += name
Global / excludeLintKeys += something

ThisBuild / name := "zio-sessions"
ThisBuild / organization := "com.upcr"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := scala3Version



val zioVersion = "2.0.6"
val scala3Version = "3.2.2"


lazy val something = settingKey[String]("This settings key does something")
lazy val someTask = taskKey[Unit]("This task does something")

lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(name := "zio-sessions2")
  .settings(                 
      libraryDependencies ++= Seq(
        "dev.zio" %% "zio" % zioVersion,
        "dev.zio" %% "zio-http" % "0.0.4",
        "dev.zio" %% "zio-streams" % "2.0.6",
        "org.scalameta" %% "munit" % "0.7.29" % Test
      )
  )
  .settings(something := scala3Version + zioVersion + "!")
  .settings(someTask := { val x = "did something"; println(x) })





    
  
