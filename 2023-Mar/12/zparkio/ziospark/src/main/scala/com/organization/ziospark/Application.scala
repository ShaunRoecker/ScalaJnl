package com.organization.ziospark

import com.leobenkel.zparkio.Services._
import com.leobenkel.zparkio.Services.Logger.Logger
import com.leobenkel.zparkio.ZparkioApp
import com.organization.ziospark.Application._
import com.organization.ziospark.services._
import izumi.reflect.Tag
import zio.{ZIO, ZLayer}

trait Application extends ZparkioApp[Arguments, RuntimeEnv, OutputType] {
  implicit lazy final override val tagC:   zio.Tag[Arguments] = zio.Tag(Tag.tagFromTagMacro)
  implicit lazy final override val tagEnv: zio.Tag[RuntimeEnv] = zio.Tag(Tag.tagFromTagMacro)

  // To add new services
  lazy final override protected val env: ZLayer[ZPARKIO_ENV, Throwable, RuntimeEnv] =
    ZLayer.succeed(())

  override protected def sparkFactory:             FACTORY_SPARK = SparkBuilder
  lazy final override protected val loggerFactory: FACTORY_LOG = Logger.Factory(Log)

  lazy final override protected val cliFactory: FACTORY_CLI =
    new FACTORY_CLI {
      // handle specific errors
      final override protected def handleErrors(
        t: Throwable
      ): ZIO[Logger, Throwable, OutputType] = {
        ZIO.succeed(())
      }
    }

  lazy final override protected val makeConfigErrorParser: ERROR_HANDLER =
    new ERROR_HANDLER {
      // handle specific error code
      final override def handleConfigParsingErrorCode(e: Throwable): Option[Int] = {
        None
      }
    }

  override protected def makeCli(args: List[String]): Arguments = Arguments(args)

  // Where the core of your application goes
  override def runApp(): ZIO[COMPLETE_ENV, Throwable, OutputType] = {
    for {
      _ <- Logger.info("Start ziospark")
      _ <- Logger.info("Completed ziospark")
    } yield (())
  }
}

object Application {
  // To add new services
  type RuntimeEnv = Unit
  // To change output type
  type OutputType = Unit
}
