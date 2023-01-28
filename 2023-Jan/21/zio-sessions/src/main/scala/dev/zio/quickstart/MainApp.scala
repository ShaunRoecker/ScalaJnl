

import zio._
import zhttp.http._
import zhttp.service.Server
// import zio.Clock._ 
// import zio.Console._


//  extends ZIOAppDefault 
object ZHttpExample {

  // Create HTTP route
  val app: HttpApp[Any, Nothing] =
    Http.collect[Request] {
      case Method.GET -> !! / "text" => Response.text("Hello World!")
      case Method.GET -> !! / "json" =>
        Response.json("""{"greetings": "Hello World!"}""")
    }

//   def run =
//     Server.start(8090, app)
}

object ZIOExample extends ZIOAppDefault {
    // val child: ZIO[Clock with Console, Nothing, Unit] = 
    //     printLine("Child fiber beginning execution...").orDie *>
    //         ZIO.sleep(5.seconds) *>
    //         printLine("Hello from a child fiber!").orDie

    // val parent: ZIO[Clock with Console, Nothing, Unit] = 
    //     printLine("Parent fiber beginning execution...").orDie *>
    //         child.fork *>
    //         ZIO.sleep(3.seconds) *>
    //         printLine("Hello from a parent fiber!").orDie

    // val example: ZIO[Clock with Console, Nothing, Unit] = 
    //     for {
    //         fiber <- parent.fork
    //         _     <- ZIO.sleep(1.second)
    //         _     <- fiber.interrupt
    //         _     <- ZIO.sleep(10.seconds)
    //     } yield () 
    import java.io.IOException
    val readLine: ZIO[Any, IOException, String] = Console.readLine

    val s1: Task[Int] = ZIO.succeed(42)

    val f1 = ZIO.fail(new Exception("Uh Oh! Something Broke"))

    val zoption: IO[Option[Nothing], Int] = 
        ZIO.fromOption(Some(2))

    val zoption2: IO[String, Int] = 
        zoption.mapError(_ => "It wasn't there!")

    

    val someInt: ZIO[Any, Nothing, Option[Int]] = ZIO.some(3)
    val noneInt: ZIO[Any, Nothing, Option[Nothing]] = ZIO.none

    def parseInt(input: String): Option[Int] = input.toIntOption

    val r1: ZIO[Any, Throwable, Int] =
        ZIO.getOrFail(parseInt("1.2"))

    val r2: ZIO[Any, Unit, Int] =
        ZIO.getOrFailUnit(parseInt("1.2"))

    val r3: ZIO[Any, NumberFormatException, Int] =
        ZIO.getOrFailWith(new NumberFormatException("invalid input"))(parseInt("1.2"))


    def run = for {
        _ <- Console.printLine("ZIO")
        _ <- Console.printLine(r1)
        _ <- Console.printLine(r2)
        _ <- Console.printLine(r3)
        
    } yield ()

}






