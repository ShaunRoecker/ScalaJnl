scalaVersion := "2.13.1"
organization := "zio.app"
name := "zio-sessions"

enablePlugins(WebsitePlugin)

val projectName = "zio-sessions"
val zioVersion = "2.0.0"
val zioSchemaVersion = "0.2.1"
val zioHttpVersion = "2.0.0-RC7"


libraryDependencies ++= Seq(
  "dev.zio"         %% "zio"                  % zioVersion,
  "dev.zio"         %% "zio-test"             % zioVersion      % "test",
  "dev.zio"         %% "zio-test-sbt"         % zioVersion      % "test",
  "io.d11"          %% "zhttp"                % zioHttpVersion,
  "io.d11"          %% "zhttp-test"           % zioHttpVersion  % Test,
  "dev.zio"         %% "zio-schema"           % zioSchemaVersion,
  "dev.zio"         %% "zio-schema-json"      % zioSchemaVersion,
  "dev.zio"         %% "zio-schema-protobuf"  % zioSchemaVersion,
  "io.d11"          %% "zhttp"                % zioHttpVersion,
  "io.d11"          %% "zhttp-test"           % zioHttpVersion  % Test,
  "dev.zio"         %% "zio-streams"          % zioVersion

)

libraryDependencies ++= Seq(
  // Syncronous JDBC Modules
  "io.getquill" %% "quill-jdbc" % "4.5.0",
  // Or ZIO Modules
  "io.getquill" %% "quill-jdbc-zio" % "4.5.0",
  // Or Postgres Async
  "io.getquill" %% "quill-jasync-postgres" % "4.5.0",
  // Or Cassandra
  "io.getquill" %% "quill-cassandra" % "4.5.0",
  // Or Cassandra + ZIO
  "io.getquill" %% "quill-cassandra-zio" % "4.5.0"
)
