import zio._

object TimeClock extends ZIOAppDefault {
    

    lazy val clockZIO: ZIO[Any, Throwable, Unit] = 
        (Clock.currentDateTime.flatMap(Console.printLine(_)) *>
        ZIO.sleep(1.seconds))


    val run = clockZIO
}