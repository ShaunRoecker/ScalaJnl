

import zio._
import scala.concurrent.Future
import scala.util._
import scala.io.StdIn
import java.io.IOException
import java.net.{ ServerSocket, URI }
import scala.io.{ Codec, Source }
import java.nio.charset.MalformedInputException
import zio.Console._
import scala.concurrent.ExecutionContext



object ZIODaily{
    // Convert Asynchronous Effects into ZIO
    lazy val future = Future.successful("Hello")

    val zFuture: Task[String] = 
        ZIO.fromFuture { implicit ec =>
            future.map(_ => "Goodbye")
        }
    
    val func: String => String = s => s.toUpperCase
    val fromPromise = for {
        promise <- ZIO.succeed(scala.concurrent.Promise[String]())
        _       <- ZIO.attempt {
            Try(func("Hello world from the future")) match {
                case Success(value) => promise.success(value)
                case Failure(e) => promise.failure(e)
            }
        }.fork
        value   <- ZIO.fromPromiseScala(promise)
        _       <- Console.printLine(s"Hello world in UpperCase: $value")
        
    } yield ()

    val io: IO[Nothing, String] = ZIO.fromFiber(Fiber.succeed("Hello from Fiber!"))

    // Convert Synchronous side effects into ZIO
    val getLine: Task[String] = ZIO.attempt(StdIn.readLine())

    //If a given side-effect is known to not throw any exceptions, 
    // then the side-effect can be converted into a ZIO effect using ZIO.succeed:
    def printLine(line: String): UIO[Unit] = ZIO.succeed(println(line))

    val succeedTask: UIO[Long] = ZIO.succeed(java.lang.System.nanoTime())

    val printLine2: IO[IOException, String] =
        ZIO.attempt(scala.io.StdIn.readLine()).refineToOrDie[IOException]


    // A blocking side-effect can be converted directly into a ZIO effect 
    // blocking with the attemptBlocking method:
    val sleeping = ZIO.attemptBlocking(Thread.sleep(Long.MaxValue))   

    def accept(l: ServerSocket) = ZIO.attemptBlockingCancelable(l.accept())(ZIO.succeed(l.close()))

    def download(url: String) =
        ZIO.attempt {
            Source.fromURL(url)(Codec.UTF8).mkString
        }

    def safeDownload(url: String) = ZIO.blocking(download(url))

    def google = ZIO.attempt(Source.fromURL(URI.create("https://www.google.com/").toASCIIString))
    
    val printLine3: IO[IOException, Unit] = Console.printLine("Hello from ZIODaily")
    // def run = for {
    //     _ <- fromPromise
    //     _ <- io
    //     _ <- Console.printLine(succeedTask)
    //     _ <- google
        
        
    // } yield ()
}

object ZIO2 extends ZIOAppDefault {
    def run = Console.printLine("ZIO2")
}

object ZIO3 extends ZIOAppDefault {
    def run = Console.printLine("ZIO3")
}

object Main extends ZIOApp.Proxy(ZIO2 <> ZIO3)

object ZIO4 extends ZIOAppDefault {
    val printLine4 = ZIODaily.printLine3.delay(2.seconds)
    val printLine5 = ZIODaily.printLine3

    def run = for {
        _ <- printLine4
        _ <- printLine5
    } yield ()

}

object ZIO5 extends ZIOAppDefault {
    def validateName(name: String) = {
        val nameRegex = """^[-a-zA-Z]+$""".r
        nameRegex.matches(name)
    }

    val helloWorld = ZIO.attempt(print("Hello, ")) *> 
                       ZIO.attempt(print("World")) *> 
                        ZIO.attempt(print("!\n"))

    val printNumbers = ZIO.foreach(1 to 5){ n => Console.printLine(n.toString) }

    val list = List(4, 5, 8, 3, 45)
    val printNumbers2 = ZIO.foreach(list){ n => Console.printLine(n.toString) }


    val prints = List(
        printLine("The"),
        printLine("quick"),
        printLine("brown"),
        printLine("fox")
    )
    val collectFox = ZIO.collectAll(prints)
    val collectFoxPar = ZIO.collectAllPar(prints)



    def run = for {
        // name <- Console.readLine("Enter your name: ")
        name <- ZIO.succeed("John")
        _    <- {
                    if (validateName(name)) Console.printLine(s"Hello, ${name}") 
                    else Console.printLine("Not a valid name")
                }
        _    <- helloWorld
        _    <- printNumbers
        _    <- printNumbers2
        _    <- collectFox
        _    <- collectFoxPar


    } yield ()

}

object ZIO6 extends ZIOAppDefault {
   val list: List[String] = List("a", "b", "c")
   val listZIO = ZIO.succeed(list)
   val listOfZIOs = List(
    ZIO.succeed(printLine("A")),
    ZIO.succeed(printLine("B")),
    ZIO.succeed(printLine("C")),
   )

    // ZIO.reduceAll?? How to use? (also, there is a ZIO.reduceAllPar)

    // ZIO.replicate
    val nZIOSuccesses = ZIO.replicate(3)(ZIO.succeed("3 replicated"))
    val useThem = ZIO.foreach(nZIOSuccesses){ n => printLine(Right(n)) }

    // ZIO.merge ???
    

   def run = for {
    // _ <- useThem
    _ <- ZIO.attempt(printLine("A")).zip(ZIO.attempt(printLine("B")))
   } yield ()

}

object ZIO7 extends ZIOAppDefault {
    val raceZIO = for {
        winner <- ZIO.succeed("Hello").race(ZIO.succeed("Goodbye"))
    } yield winner

    val timeOutZIO = ZIO.succeed("Hello").timeout(1.nanoseconds)
    val printTimeout = printLine(timeOutZIO.map(Right(_)))
    val threadName = Thread.currentThread.getName
    val printThread = Console.printLine(threadName)
    def run = for {
        // _ <- printLine(raceZIO)
        // _ <- printTimeout
        _ <- printThread //sbt-bg-threads-13
    } yield ()

}

object ZIO8 extends ZIOAppDefault {
    
    def run = printLine("Hello")

}


















