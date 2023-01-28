import zio._

object TimeClock extends ZIOAppDefault {
    
    val faultyRandom = 
        OpinionatedRandomNumberService.get

    val orElseExample =
        faultyRandom orElse faultyRandom
    
    lazy val clockZIO: ZIO[Any, Throwable, Unit] = 
        (Clock.currentDateTime.flatMap(Console.printLine(_)) *>
        ZIO.sleep(1.seconds))


    val run = clockZIO
}