import sbt._

object Dependencies {
  // Versions
  lazy val zioVersion = "2.0.6"

  // Libraries
  // ZIO
  val zio        = "dev.zio" %% "zio" % zioVersion
  val zioStreams = "dev.zio" %% "zio-streams" % zioVersion
  val zioParser  = "dev.zio" %% "zio-parser" % "0.1.8"


  // Projects
  val backendDeps = 
    Seq(zio, zioStreams, zioParser)
}



