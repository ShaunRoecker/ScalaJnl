package configuration

import zio._
// import configuration.MainApp.validateEnv

object MainApp extends ZIOAppDefault {
  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(message()))

  def run =
    for {
      _ <- ZIO.log("Application started!")
      _ <- {
        for {
          _ <- ZIO.log("I'm not going to be logged!")
          _ <- ZIO.log("I will be logged by the simple logger.").provide(addSimpleLogger)
          _ <- ZIO.log("Reset back to the previous configuration, so I won't be logged.")
        } yield ()
      }.provide(Runtime.removeDefaultLoggers)
      _ <- ZIO.log("Application is about to exit!")
    } yield ()
}


// import configuration.Runtime2.validateEnv
object Runtime2 extends ZIOAppDefault {
  val aspectZIO: UIO[String] = ZIO.succeed("Succeeded") @@ ZIOAspect.debug
  val aspectParaZIO = ZIO.Parallelism 

  def run = for {
    _ <- ZIO.log("Application started!")
    _ <- {
      for {
        _ <- ZIO.log("won't be logged!").provide(Runtime.removeDefaultLoggers)
        _ <- ZIO.log("will be logged!")
      } yield ()
    }.provide(Runtime.enableRuntimeMetrics)
    _ <- aspectZIO
    _ <- ZIO.debug(aspectParaZIO)
  } yield ()

}

object Main extends ZIOApp.Proxy(MainApp <> Runtime2)

object MainApp2 extends ZIOAppDefault {
  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(message()))

  override val bootstrap: ZLayer[Any, Nothing, Unit] =
    Runtime.removeDefaultLoggers ++ addSimpleLogger

  def run =
    for {
      _ <- ZIO.log("Application started!")
      _ <- ZIO.log("Application is about to exit!")
    } yield ()
}


object MainApp3 extends ZIOAppDefault {
  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(message()))
  
  val effectfulConfiguration: ZLayer[Any, Nothing, Unit] =
    ZLayer.fromZIO(ZIO.log("Started effectful workflow to customize runtime configuration"))

  // the bootstrap configuration is only implemented through the run method
  // so the above configuration will be logged through the DefaultLogger, not
  // the simpleLogger
  override val bootstrap: ZLayer[Any, Nothing, Unit] =
    Runtime.removeDefaultLoggers ++ addSimpleLogger ++ effectfulConfiguration

  def run =
    for {
      _ <- ZIO.log("Application started!")
      _ <- ZIO.log("Application is about to exit!")
    } yield ()
}

object MainApp extends ZIOAppDefault {

  // In a real-world application we might need to implement a `sl4jlogger` layer
  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(message()))

  val layer: ZLayer[Any, Nothing, Unit] =
    Runtime.removeDefaultLoggers ++ addSimpleLogger

  override val runtime: Runtime[Any] =
    Unsafe.unsafe { implicit unsafe =>
      Runtime.unsafe.fromLayer(layer)
    }

  def run = ZIO.log("Application started!")
}