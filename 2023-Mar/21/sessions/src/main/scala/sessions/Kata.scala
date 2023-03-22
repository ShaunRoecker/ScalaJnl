import zio._

object Kata extends ZIOAppDefault {

    val myInt: ZIO[Any, Nothing, Int] =
        ZIO.succeed(42)

    val myFakeStream: ZIO[Any, Throwable, Int] =
        Console.printLine(42)
            .delay(100.milliseconds)
            .as(42)
            .debug("FINAL OUTPUT")

    def run = myFakeStream.delay(1.second)
}
