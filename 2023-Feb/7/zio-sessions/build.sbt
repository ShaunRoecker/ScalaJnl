import sbt.protocol.ExecCommand
ThisBuild / scalaVersion := scala3Version
ThisBuild / name := "zio-sessions"
ThisBuild / version := "0.1.0-SNAPSHOT"

Global / excludeLintKeys += name

val scala3Version = "3.2.2"
val zioVersion = "2.0.6"

val startServer = taskKey[Unit]("start server")
val stopServer = taskKey[Unit]("stop server")
val sampleStringTask = taskKey[String]("A sample string task.")
val sampleIntTask = taskKey[Int]("A sample int task.")
val posix = taskKey[Unit]("Is Posix?")
val listdir = taskKey[Unit]("ls command")

lazy val ziosessions = project
  .in(file("."))
  //.enablePlugins(FooPlugin, BarPlugin)
  //.disablePlugins(plugins.IvyPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "dev.zio" %% "zio" % zioVersion
    )
  )
  .settings(
    sampleStringTask := System.getProperty("user.home"),
    sampleIntTask := {
      val sum = 1 + 2
      println("sum: " + sum)
      sum
    },
    posix := {
      startServer.value
      if (IO.isPosix) println("Yep, it's Posix") else println("Nope, save up!")
      stopServer.value
    },
    listdir := { ExecCommand.apply("ls") }

  )
  // stopping...
  // starting...
  // Yep, it's Posix
  .settings(
    startServer := {
      println("starting...")
      Thread.sleep(500)
    },
    stopServer := {
      println("stopping...")
      Thread.sleep(500)
    }
  )


