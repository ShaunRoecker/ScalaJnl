package com.upcr.app

import zio._


object AppZIO extends ZIOAppDefault:
  ZIO.fail("Oh uh!").cause.debug @@ 
      ZIOAspect.logged("fail log") @@
      ZIOAspect.debug

  // Fail(Oh uh!,Trace(Runtime(2,1646395627),Chunk(<empty>.MainApp.run(MainApp.scala:3))))

  (ZIO.fail("Oh uh!") *> ZIO.dieMessage("Boom!") *> ZIO.interrupt).cause.debug
  // Fail(Oh uh!,Trace(Runtime(2,1646396370),Chunk(<empty>.MainApp.run(MainApp.scala:6))))

  (ZIO.fail("Oh uh!") <*> ZIO.fail("Oh Error!")).cause.debug
  // Fail(Oh uh!,Trace(Runtime(2,1646396419),Chunk(<empty>.MainApp.run(MainApp.scala:9))))

  val myApp: ZIO[Any, String, Int] =
    for {
      i <- ZIO.succeed(5)
      _ <- ZIO.fail("Oh uh!")
      _ <- ZIO.dieMessage("Boom!")
      _ <- ZIO.interrupt
    } yield i
  
  val myApp2: ZIO[Any, String, Unit] =
      for {
        f1 <- ZIO.fail("Oh uh!").fork
        f2 <- ZIO.dieMessage("Boom!").fork
        _ <- (f1 <*> f2).join
      } yield ()
  def run = for {
    _     <- Console.printLine("Welcome to ZIO 2.0 with Scala3")
    //empty <- ZIO.failCause(Cause.empty).cause.debug
    //fail  <- ZIO.failCause(Cause.fail("Uh oh!")).cause.debug
    _ <- myApp.cause.debug
    _ <- ZIO.succeed(5 / 0).cause.debug
    _ <- ZIO.dieMessage("Boom!").cause.debug
    _ <- ZIO.interrupt.cause.debug
    _ <- ZIO.never.fork
            .flatMap(f => f.interrupt *> f.join)
            .cause
            .debug
    _ <- Console.printLine("breakpoint")
    _ <- ZIO.dieMessage("Boom!").cause.debug
    _ <- ZIO.die(new Throwable("Boom!")).cause.debug
    _ <- Console.printLine("breakpoint")
    _ <- myApp2.cause.debug

  } yield ()


// object MainApp extends ZIOApp.Proxy(AppZIO <> ZIOApp2)
