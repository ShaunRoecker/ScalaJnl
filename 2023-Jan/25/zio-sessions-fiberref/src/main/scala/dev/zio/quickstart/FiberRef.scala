import zio._
import zio.Console._

// Ref vs. FiberRef

// Ref - One variable shared between different fibers
// Use Ref.update and Ref.modify instead of Ref.get and Ref.set 

// FiberRef - Each fiber gets it's own copy of the FiberRef
// When we fork a fiber it gets an initial value equal to its parent value
// When we join a fiber, we get the value of the fiber we joined

object FiberRefZIO extends ZIOAppDefault {
    // Example of a use case that we would actually want to use FiberRef instead
    val refExample: ZIO[Any, Nothing, Unit] =
        for {
            ref <- Ref.make("Zymposium")
            from1 = ref.update(_ => "One") *> ref.get.debug("FROM ONE") *> ref.update(_ => "Zymposium")
            from2 = ref.update(_ => "Two") *> ref.get.debug("FROM TWO") *> ref.update(_ => "Zymposium")
            _ <- from1.zipPar(from2) *> ref.get.debug("FINISH")

        } yield ()

    val fiberRefExample: ZIO[Scope, Nothing, Unit] =
        for {
            ref <- FiberRef.make("Zymposium")
            from1 = ref.locally("One")(ref.get.debug("ONE"))
            from2 = ref.locally("Two")(ref.get.debug("TWO"))
            _ <- from1.zipPar(from2)
            _ <- ref.get.debug("ZYMPOSIUM")

        } yield ()

    val fiberRefWithForkAndJoin: ZIO[Scope, Nothing, Unit] =
        for {   // fork and join are there, but most cases us locally 
            ref <- FiberRef.make[Map[String, String]](Map.empty, join = _ ++ _)
            _ <- ref.get.debug("START")
            fiber1 = ref.set(Map("Key1" -> "Value1"))
            fiber2 = ref.set(Map("Key2" -> "Value2"))
            _ <- fiber1.zipPar(fiber2)
            _ <- ref.get.debug("END")

        } yield ()

    // The locally operator also composes, so you could for example use 
    // locally in one part of your application to turn off logging but 
    // then inside that part of your application call locally again in 
    // one particular area to turn logging back on.
    lazy val doSomething: IO[Throwable, Unit] = Console.printLine("Did something")
    lazy val doSomethingElse: IO[Throwable, Unit] = Console.printLine("Did something else")
    lazy val doSomethingElseAgain: IO[Throwable, Unit] = Console.printLine("Did something else again")

    lazy val onOffNested =
        for {
            logging <- FiberRef.make(true) 
            _ <- logging.locally(false) {
                for {
                    _ <- doSomething 
                    _ <- logging.locally(true)(doSomethingElse  *> doSomethingElseAgain)
                    // _ <- if (logging.get) doSomething
                    //      else doSomethingElse
                } yield () 
            }
        } yield ()

    // Implementations using FiberRef
    // Log annotations

    
    def run = ZIO.debug("--Ref--") *>
              refExample *>
              ZIO.debug("--FiberRef--") *> 
              fiberRefExample *> 
              ZIO.debug("--LocallyExample--") *>
              onOffNested *>
              ZIO.debug("--FiberRefWithForkAndJoin--") *>
              fiberRefWithForkAndJoin

}

/////////////////////////////////////////////////////////////////////////////
// Logging Service

object LoggingService extends ZIOAppDefault {

    trait Logging {
        def logAnnotate(key: String, value: String)(zio: ZIO[R, E, A]): ZIO[R, E, A] 
        def logLine(line: String): ZIO[Any, Nothing, Unit]
    }

    case class ConsoleLogging(console: Console) extends Logging {
        def logLine(line: String): ZIO[Any, Nothing, Unit] = 
            Console.printLine(s"[LOG] ${line}")

        def logAnnotate[R, E, A](key: String, value: String)(zio: ZIO[R, E, A]): 
            ZIO[Logging with R, E, A] =
                ???
    }

    object Logging {
        def logLine(line: String): ZIO[Logging, Nothing, Unit] = 
            ZIO.serviceWithZIO(_.logLine(line))

        def logAnnotate[R, E, A](key: String, value: String)(zio: ZIO[R, E, A]): 
            ZIO[Logging with R, E, A] =
                ZIO.serviceWithZIO(_.logAnnotate(key, value)(zio))

    }

    val loggingExample = 
        for {
            _ <- Logging.logLine("Starting program")
            _ <- Logging.logAnnotate("Kit") {
                Logging.logLine("Howdy")
            }
            _ <- Logging.logLine("All done")
        } yield ()
    
    def run = ???
}