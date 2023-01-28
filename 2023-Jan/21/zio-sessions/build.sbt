scalaVersion := "2.13.1"
organization := "zio.app"
name := "zio-sessions-01-20-2023"

enablePlugins(WebsitePlugin)

val zioVersion = "2.0.0"
val zioSchemaVersion = "0.2.1"


libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-test"     % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt" % zioVersion % "test",
  "io.getquill" %% "quill-zio" % "3.9.0",
  "io.getquill"  %% "quill-jdbc-zio" % "4.5.0",
  "org.postgresql"       %  "postgresql"     % "42.3.1",
  "io.d11" %% "zhttp"      % "2.0.0-RC7",
  "io.d11" %% "zhttp-test" % "2.0.0-RC7" % Test,
  "dev.zio" %% "zio-schema" % zioSchemaVersion,
  "dev.zio" %% "zio-schema-json" % zioSchemaVersion,
  "dev.zio" %% "zio-schema-protobuf" % zioSchemaVersion
)


