package dev.zio.quickstart

import zio.*



  // type ZIO[-R, +E, +A] = R => Either[E, A]
  // values of the ZIO type are called "functional effects"
  // You can think of a ZIO as a function that takes an environment 
  // of type R and returns either a failure of type E or a success of type A

  // Effects that don't require an environment
  // type IO[+E, +A] = ZIO[Any, E, A]

  // Effects that don't fail
  // type UIO[+E, +A] = ZIO[Any, Nothing, A]

  // Variety of combinators for creating effects:
    // - From existing values - ZIO#succeed
    // - From side effects - ZIO#effect
    // - From callback based APIs - ZIO#effectAsync
    // - From blocking APIs - ZIO#effectBlocking
    // - From futures - ZIO#fromFuture
    // 


object MainApp extends ZIOAppDefault:
  def sayHello(name: String): ZIO[Console, Nothing, Unit] = ???

  val goShopping =
    ZIO.attempt(println("Going to the grocery store"))

  val goShoppingLater = 
    goShopping.delay(1.seconds)

  import scala.io.StdIn

  val readLine = 
    ZIO.attempt(StdIn.readLine("Type something: "))

  def printLine(line: String) =
    ZIO.attempt(println(line))

  def printLineTuple(line: Tuple2[String, String]) =
    ZIO.attempt(println(line))

  val echo =
    readLine.flatMap(line => printLine(line))

  val echoFor =
    for {
      line <- readLine
      _    <- printLine(line)
    } yield ()

  val firstName = 
    ZIO.attempt(StdIn.readLine("Enter first name: "))

  val lastName = 
    ZIO.attempt(StdIn.readLine("Enter last name: "))

  val fullNameZipWith = 
    firstName.zipWith(lastName)((first, last) => s"$first $last")
  // the zipWith operator is less powerful than flatMap because it doesn't
  // let the second effect to depend on the first
  val fullNameZip = 
    firstName.zip(lastName)

  // <* is an alias for zipLeft and *> is a alias for zipRight
  // returns a tuple of Unit 
  //  These operators are particularly useful to combine a number of effects 
  // sequentially when the result of one or more of the effects are not needed.
  val helloWorld =
    ZIO.attempt(print("Hello, ")) *> ZIO.attempt(print("World!\n"))

  val printNumbers = ZIO.foreach(1 to 10) { n =>
    printLine(n.toString)
  }

  val prints = List(
    printLine("The"),
    printLine("quick"),
    printLine("brown"),
    printLine("fox")
  )

  // collectAll returns a single effect that collects 
  // the results of a whole collection of effects.
  val printWords = ZIO.collectAll(prints)
  

  def run =
    for {
      _ <- printWords
      // _ <- printNumbers
      // _ <- helloWorld
      // zipW <- fullNameZipWith
      // _    <- printLine(zipW)
      // zipX <- fullNameZip
      // _    <- printLineTuple(zipX)
      // _ <- echoFor
      // line <- readLine
      // _    <- printLine(line) 
      // _ <- echo
      // _    <- Console.printLine("Please enter your name: ")
      // name <- Console.readLine
      // _    <- Console.printLine(s"Hello, $name!")
      _    <- goShopping 
      // _    <- goShoppingLater
    } yield ()



// Procedural way
object ConcurrentProcedural:
    import java.util.concurrent.{ Executors, ScheduledExecutorService }
    import java.util.concurrent.TimeUnit._

    def goShoppingUnsafe: Unit = {
      println("Going to the grocery store")
    }

    val scheduler: ScheduledExecutorService =
      Executors.newScheduledThreadPool(1)

    scheduler.schedule(
      new Runnable { def run: Unit = goShoppingUnsafe },
      3,
      SECONDS
    )
    scheduler.shutdown() 



