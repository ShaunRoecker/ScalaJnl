package app

import zio._


object Promises1 extends ZIOAppDefault {
  val race: IO[String, Int] = for {
    p     <- Promise.make[String, Int]
    _     <- p.succeed(1).fork
    _     <- p.complete(ZIO.succeed(2)).fork
    _     <- p.completeWith(ZIO.succeed(3)).fork
    _     <- p.done(Exit.succeed(4)).fork
    _     <- p.fail("5")
    _     <- p.failCause(Cause.die(new Error("6")))
    _     <- p.die(new Error("7"))
    _     <- p.interrupt.fork
    value <- p.await //
  } yield value

  def run = for {
    _ <- Console.printLine("Promises")
    promise <- Promise.make[Nothing, Int]
    a <- (promise.await *> ZIO.succeed(promise).tap(Console.printLine(_))).fork
    b <- (ZIO.sleep(1.seconds) *> promise.complete(ZIO.succeed(10))).fork
    _ <- (a *> b).join
    _ <- race.debug
    _ <- Console.printLine(race)
    promise2 <- Promise.make[Nothing, String]
    _ <- promise2.succeed("hello there")
    c <- promise2.await.flatMap(Console.printLine(_)) //hello there
    _ <- ZIO.debug(c) // ()
    promise3 <- Promise.make[Nothing, String]
    _ <- promise3.succeed("abcd")
    d <- promise3.await
                  .map(a => a.toList.reverse)
                  .flatMap(Console.printLine(_)) //List(d, c, b, a)
    _ <- ZIO.succeed(5).map(_ + 5).flatMap(Console.printLine(_)) //10
    
  } yield ()
}

object Promise2 extends ZIOAppDefault {
  val ioPromise1: UIO[Promise[Exception, String]] = 
    Promise.make[Exception, String]
  val ioBooleanSucceeded = 
    ioPromise1.flatMap(promise => promise.succeed("I'm done"))

  val ioPromise2: UIO[Promise[Exception, Nothing]] = 
    Promise.make[Exception, Nothing]
  val ioBooleanFailed: UIO[Boolean] = 
    ioPromise2.flatMap(promise => promise.fail(new Exception("boom")))

  
  def run = for {
    _ <- ioPromise1.debug
    _ <- ioBooleanSucceeded.debug //true
    promise1 <- Promise.make[Exception, Unit]
    fiber <- (ZIO.succeed(promise1).flatMap(promise => promise.await)).fork
    _ <- promise1.succeed(Console.printLine("completed"))
    _ <- fiber.join


  } yield ()
}

object Promise3 extends ZIOAppDefault {
    import java.io.IOException
    val program: ZIO[Any, IOException, Unit] =
      for {
        promise <- Promise.make[Nothing, String]
        sendHelloWorld = (ZIO.succeed("hello world") <* ZIO.sleep(2.second)).flatMap(promise.succeed(_))
        getAndPrint = promise.await.flatMap(Console.printLine(_))
        fiberA <- sendHelloWorld.fork
        fiberB <- getAndPrint.fork
        _ <- (fiberA zip fiberB).join

      } yield ()

    def run = program
}

