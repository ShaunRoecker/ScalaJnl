package dev.zio.quickstart

import zio.*


object MainApp extends ZIOAppDefault {
  import scala.io.StdIn
  import scala.concurrent.{ Future, ExecutionContext }

  val printSomething = ZIO.attempt(println("Hello, World!"))
  // lazy val readInt: IO[NumberFormatException, Int] =
  //   ZIO.attempt(StdIn.readLine("Enter a number: ").toInt)

  // lazy val readAndSumTwoInts: ZIO[Any, NumberFormatException, Int] = 
  //   for {
  //     x <- readInt
  //     y <- readInt 
  //   } yield x * y

  // def getUserByIdAsync(id: Int)(cb: Option[String] => Unit): Unit = ???

  // getUserByIdAsync(0) {
  // case Some(name) => println(name)
  // case None => println("User not found!")
  // }

  // def getUserById(id: Int): ZIO[Any, None.type, String] =
  //   ZIO.async { callback =>
  //       getUserByIdAsync(id) {
  //         case Some(name) => callback(ZIO.succeed(name))
  //         case None => callback(ZIO.fail(None))
  //       }
  //   }
  // implicit val ec: scala.concurrent.ExecutionContext = 
  //   scala.concurrent.ExecutionContext.global

  // def goShoppingFuture(
  //     implicit ec: ExecutionContext
  // ): Future[Unit] =
  //     Future(println("Going to the grocery store"))
  
  // val goShoppingZIO: Task[Unit] = 
  //     ZIO.fromFuture(implicit ec => goShoppingFuture)
  import java.util.concurrent.TimeUnit

  val inMiliseconds: URIO[Clock, Long] = 
    Clock.currentTime(TimeUnit.MILLISECONDS)
  val inDays: URIO[Clock, Long] = 
    Clock.currentTime(TimeUnit.DAYS)
  
  val printTime = ZIO.attempt(println(inDays))

  val readInt: ZIO[Any, Throwable, Int] = for {
    line <- Console.readLine
    int <- ZIO.attempt(line.toInt) 
  } yield int

  lazy val readIntOrRetry: ZIO[Any, Throwable, Int] = 
    readInt
      .orElse(Console.printLine("Please enter a valid integer")
      .zipRight(readIntOrRetry))


  // 2.11 Exercises
  // 1.
  def readFile(file: String): String =
    val source = scala.io.Source.fromFile(file)
    try source.getLines.mkString
    finally source.close()

  def readFileZio(file: String): ZIO[Any, Throwable, String] =
    ZIO.attempt(readFile(file))

  // 2. 
  def writeFile(file: String, text: String): Unit = { 
    import java.io._
    val pw = new PrintWriter(new File(file))
    try pw.write(text)
    finally pw.close 
  }

  def writeFileZio(file: String, text: String) =
    ZIO.attempt(writeFile(file, text))
  
  



  def run = 
    for {
      _ <- printSomething
      // _ <- goShoppingFuture
      // _ <- printTime
      // _ <- readIntOrRetry
      // _ <- readFileZio("not a file")
      _ <- writeFileZio("ggg", "hhh")
      
    } yield ()
}
