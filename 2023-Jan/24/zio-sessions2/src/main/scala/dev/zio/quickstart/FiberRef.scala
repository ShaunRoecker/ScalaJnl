import zio._
import zio.Console._

// Ref vs. FiberRef

// Ref - One variable shared between different fibers
// Use Ref.update and Ref.modify instead of Ref.get and Ref.set 

// FiberRef - Each fiber gets it's own copy of the FiberRef
// When we fork a fiber it gets an initial value equal to its parent value
// When we join a fiber, we get the value of the fiber we joined

/////////////////////////////////////////////////////////////////////////////
// Logging Service

object LoggingService extends ZIOAppDefault {

    trait Logging {
        def logAnnotate[R, E, A](key: String, value: String)(zio: ZIO[R, E, A]): ZIO[R, E, A] 
        def logLine(line: String): ZIO[Any, Nothing, Unit]
    }

    case class ConsoleLogging(console: Console, annotations: FiberRef[Map[String, String]]) extends Logging {
        def logAnnotate[R, E, A](key: String, value: String)(zio: ZIO[R, E, A]): ZIO[R, E, A] =
            for {
                map <- annotations.get
                a <- annotations.locally(map.updated(key, value)(zio))
            } yield a

        def logLine(line: String): ZIO[Any, Nothing, Unit] = 
            for {
                anns <- annotations.get
                formatted = anns.map { case (k, v) => s"$k: $v" }.mkString(", ")
                _ <- Console.printLine(s"[LOG] ${formatted} $line").orDie
            } yield ()
    }

    object Logging {
        val live = Zlayer {
            for {
                annotations <- FiberRef.make(Map.empty[String, String])
                console <- ZIO.service[Console]
            } yield ConsoleLogging(console, annotations)
        }
        
        // accessors
        def logLine(line: String): ZIO[Logging, Nothing, Unit] = 
            ZIO.service[Logging].flatMap(_.logLine(line))

        def logAnnotate[R, E, A](key: String, value: String)(zio: ZIO[R, E, A]): 
            ZIO[Logging with R, E, A] =
                ZIO.service[Logging].flatMap(_.logAnnotate(key, value)(zio))

    }

    val loggingExample = 
        for {
            _ <- Logging.logLine("Starting program")
            _ <- Logging.logAnnotate("Kit", "cool") {
                Logging.logAnnotate("Adam", "nice") {
                    Logging.logLine("Hello")
                } *>
                Logging.logLine("Howdy")
            }
            _ <- Logging.logLine("All done")
        } yield ()
    
    def run = loggingExample.provideCustomLayer(Logging.live)
}