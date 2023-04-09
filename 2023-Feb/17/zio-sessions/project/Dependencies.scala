import sbt._


object Dependencies {
    // Versions
  lazy val zioVersion = "2.0.6"
  lazy val zioHttpVersion = "0.0.4"
  lazy val zioParserVersion = "0.1.8"

  // Libraries
  // ZIO
  val zio        = "dev.zio" %% "zio" % zioVersion
  val zioHttp    = "dev.zio" %% "zio-http" % zioHttpVersion
  val zioStreams = "dev.zio" %% "zio-streams" % zioVersion
  val zioParser  = "dev.zio" %% "zio-parser" % zioParserVersion


  // Projects
  val backendDeps = 
    Seq(zio, zioHttp, zioStreams, zioParser) 
}

