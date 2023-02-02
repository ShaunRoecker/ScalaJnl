

import zio._
import zio.Console._


object Example extends ZIOAppDefault {
  
    def fail(s: String): IO[String, Nothing] = ZIO.fail(s)

    val myApp: ZIO[Any, Throwable, String] =
      ZIO.attempt("Hello!") @@ ZIOAspect.debug

    val zio = ZIO.foreach(1 to 100) { _ =>
      ZIO.foreachPar(1 to 100)(ZIO.succeed(_)).map(_ == (1 to 100))
    }.map(_.forall(identity))

    def run = for {
        _ <- Console.printLine("February 1st, 2023!")
        _ <- fail("First ZIO failed").retryOrElse(
          Schedule.recurs(5),
          (_, _: Long) => Console.printLine("Second ZIO success after trying first 5 times")
        )
        _ <- myApp
        _ <- ZIO.debug(zio)

        
    } yield ()
    //


}

