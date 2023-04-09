import zio._

object Kata extends ZIOAppDefault {

    val myInt: ZIO[Any, Nothing, Int] =
        ZIO.succeed(42)

    val myFakeStream: ZIO[Any, Throwable, Int] =
        Console.printLine(42)
            .delay(100.milliseconds)
            .as(42)
            .debug("FINAL OUTPUT")


    def p(value: Any): Unit = {
        scala.util.Random.between(1, 7) match {
            case 1 => println(scala.Console.CYAN + value + scala.Console.RESET)
            case 2 => println(scala.Console.RED + value + scala.Console.RESET)
            case 3 => println(scala.Console.GREEN + value + scala.Console.RESET)
            case 4 => println(scala.Console.MAGENTA + value + scala.Console.RESET)
            case 5 => println(scala.Console.BLUE + value + scala.Console.RESET)
            case 6 => println(scala.Console.YELLOW + value + scala.Console.RESET)
        }
    }

    def run =
        for {
            _ <- ZIO.attempt(p("Hello, ZIO!"))
            _ <- ZIO.attempt(p("Hello, ZIO!"))
            _ <- ZIO.attempt(p("Hello, ZIO!"))
            _ <- ZIO.attempt(p("Hello, ZIO!"))
            _ <- ZIO.attempt(p("Hello, ZIO!"))
            _ <- myFakeStream.delay(1.second)
        } yield ()
    
}
