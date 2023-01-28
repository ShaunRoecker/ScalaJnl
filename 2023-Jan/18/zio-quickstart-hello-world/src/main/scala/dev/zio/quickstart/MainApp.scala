package dev.zio.quickstart

import zio.*

object MainApp extends ZIOAppDefault:
  // Clock: sleep
  // Console: readLine, print, printLine

  val printX = ZIO.attempt(println("XXXXXXX"))
  val printY = ZIO.attempt(println("YYYYYYY"))

  val waitX = printX.delay(2.seconds)

  val zipX = waitX.zip(printX)

  val zipWithX = printX.zipWith(printY)((x, y) => s"$x, $y")
  val zipRightX = printX.zipRight(printY)

  val printNumbers = ZIO.foreach(1 to 100) { n =>
    Console.printLine(n.toString)
  }

  object Exercise4:
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

      def copyFileZio(source: String, dest: String) =
        readFileZio(source).flatMap(contents => writeFileZio(dest, contents))

      def copyFileZio2(source: String, dest: String) = for {
        contents <- readFileZio(source)
        out <- writeFileZio(dest, contents)
      } yield out

      def printLine(line: String) = ZIO.attempt(println(line))
      val readLine = ZIO.attempt(scala.io.StdIn.readLine())

      val ex4 = for {
        _    <- printLine("What is your name?")
        name <- readLine
        _    <- printLine(s"Hello, ${name}")
      } yield ()
  
  import Exercise4._

  object Exercise5:
      def printLine(line: String) = ZIO.attempt(println(line))
      val readLine = ZIO.attempt(scala.io.StdIn.readLine())
      val random = ZIO.attempt(scala.util.Random.nextInt(3) + 1) 
      
      // val ex5 = for {
      //   int  <- random
      //   _    <- printLine("Guess a number between 1 to 3:")
      //   num  <- readLine
      //   _    <- if (num == int.toString) println("You guessed right!")
      //           else println(s"You guessed wrong, the number was ${int}")
      // } yield ()
      for {
        int <- random
        _   <- printLine("Guess a number from 1 to 3:")
        num <- readLine
        _ <- if (num == int.toString) printLine("You guessed right!")
            else printLine(s"You guessed wrong, the number was $int!")
      } yield ()
    
  import Exercise5._

  object Exercise6 {

    final case class ZIO[-R, +E, +A](run: R => Either[E, A])

    def zipWith[R, E, A, B, C](self: ZIO[R, E, A], that: ZIO[R, E, B])(f: (A, B) => C): ZIO[R, E, C] =
      ZIO(r => self.run(r).flatMap(a => that.run(r).map(b => f(a, b))))
  }

  import java.io.IOException
  def readUntil(acceptInput: String => Boolean): ZIO[Any, IOException, String] =
    for {
      input <- Console.readLine
      output <- if (acceptInput(input)) ZIO.succeed(input)
                else readUntil(acceptInput)
    } yield output

  


  def inputCheck(input: String) = if (input.length == 4) true else false

  def run = readUntil(inputCheck)
    

    // def printLine(line: String) = ZIO.attempt(println(line))
    // val readLine = ZIO.attempt(scala.io.StdIn.readLine())
    // val random = ZIO.attempt(scala.util.Random.nextInt(3) + 1)
    // for {
    //   int <- random
    //   _   <- printLine("Guess a number from 1 to 3:")
    //   num <- readLine
    //   _ <- if (num == int.toString) printLine("You guessed right!")
    //        else printLine(s"You guessed wrong, the number was $int!")
    // } yield ()
    
    // for {
      // something <- Console.readLine("Enter something: ")
      // _ <- Console.printLine("Hello")
      // _ <- Clock.sleep(2.seconds)
      // _ <- Console.printLine("Hello Again")
      // _ <- Console.printLine(something)
      // _ <- waitX
      // _ <- zipX
      // _ <- zipWithX
      // _ <- ex4
      // _ <- ex5


    // } yield ()







