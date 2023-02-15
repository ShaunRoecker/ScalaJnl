import zio._
import zio.Clock._ 
import zio.Console._


object Semaphore1 extends ZIOAppDefault {
  def queryDatabase( connections: Ref[Int]): URIO[Any, Unit] =
    connections.updateAndGet(_ + 1).flatMap { n =>
      Console.printLine(s"Aquiring, now $n connections").orDie *>
        ZIO.sleep(1.second) *>
        Console.printLine(s"Closing, now ${n - 1} connections").orDie
    }

  def run = for { 
    ref       <- Ref.make(0)
    semaphore <- Semaphore.make(4)
    query     = semaphore.withPermit(queryDatabase(ref))
    _         <- ZIO.foreachParDiscard(1 to 10)(_ => query)
  } yield ()


}

