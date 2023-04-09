import sbt._
import Keys._
import Versions._

object Dependencies {
  object Zio {
    val zio             = "dev.zio" %% "zio" % zioVersion
    val zioHttp         = "dev.zio" %% "zio-http" % zioHttpVersion
    val zioStreams      = "dev.zio" %% "zio-streams" % zioVersion
    val zioParser       = "dev.zio" %% "zio-parser" % zioParserVersion
    val zioPrelude      = "dev.zio" %% "zio-prelude" % zioPreludeVersion
    val zioOptics       = "dev.zio" %% "zio-optics" % zioOpticsVersion
  }

  object Refined {
    val refinedCore     = "eu.timepit" %% "refined"% refinedVersion
  }
  
  val zioDependencies: Seq[ModuleID] = Seq(
    Zio.zio,
    Zio.zioHttp, 
    Zio.zioStreams, 
    Zio.zioParser,
    Zio.zioOptics
  ) 

  val commonDependencies: Seq[ModuleID] = 
    zioDependencies //++ Seq(Refined.refinedCore)
  

}

