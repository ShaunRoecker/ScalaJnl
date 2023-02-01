

import zio._
import zio.Console._


object Example extends ZIOAppDefault {
  
    

    def run = for {
        _ <- Console.printLine("Hello, world!")
        _ <-  ZIO.succeed(42).tap(a => printLine(a))
        _ <- ZIO.succeed(List(0, 1, 3 , 4)).tapSome {
          case list if list.length >= 1 && list.map(_ + 1).head == 1 => printLine(list.head)
        }
        error <- ZIO.fail(new Exception("Nope")).absorb.ignore
        _ <- ZIO.debug(error)
        error2 <- ZIO.fail("Nope").mapError(_ + "!")
        _ <- ZIO.debug(error2)
    } yield ()
    //


}

