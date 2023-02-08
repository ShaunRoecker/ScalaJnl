import zio._
import zio.Console._
import java.io.IOException


object MainApp extends ZIOAppDefault:

  def printTimeForever: ZIO[Any, Throwable, Nothing] =
    Clock.currentDateTime.flatMap(Console.printLine(_)) *>
      ZIO.sleep(1.seconds) *> printTimeForever

  case class Name(value: String)
  object Name:
    def make(value: String): Option[Name] = {
      val name = Name(value)
      Some(name)
    }
  
  val a = ZIO.succeed {
      println("t1 started")
      Thread.sleep(2000)
      println("t1 finished")
      1
  }
  val b = ZIO.succeed {
      println("t2 started")
      Thread.sleep(1000)
      println("t2 finished")
      2
  } 
  val c = ZIO.succeed {
      println("t3 started")
      Thread.sleep(3000)
      println("t3 finished")
      3
  }
  val d = ZIO.succeed {
      println("t4 started")
      Thread.sleep(1000)
      println("t4 finished")
      4
  }

  def getUserInput(message: String): ZIO[Console, IOException, String] =  
    printLine(message) *> readLine

  lazy val getName: ZIO[Console, IOException, Name] =
    for {
      input <- getUserInput("What's your name?")
      name <- Name.make(input) match {
              case Some(name) => ZIO.succeed(name)
              case None => printLine("Invalid input. Please try again...") *> getName
            }
    } yield name

  def run = for {
    _ <- Console.printLine("ZIO Clock")
    //_ <- getName
    name <- readLine("Enter your name: ")
    _ <- printLine("A") <&> printLine("B")
    fiber1 <- ZIO.collectAllPar(List(a, b, c, d)).fork
    fiber2 <- (a <&> b <&> c <&> d).fork
    _ <- (fiber1 <*> fiber2).join
    
    

  } yield ()

// t1 started
// t2 started
// t4 started
// t3 started
// t2 finished
// t4 finished
// t1 finished
// t3 finished


