scalaVersion := "3.1.3"
organization := "zio.app"
name := "zio-sessions-01-19-2023"


val zioVersion = "2.0.0"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-test"     % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt" % zioVersion % "test",
  "io.getquill" %% "quill-jdbc-zio" % "4.1.1-SNAPSHOT",
  "org.postgresql" % "postgresql" % "42.3.1"
)

