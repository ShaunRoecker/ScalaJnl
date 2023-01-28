

import zio._
import zio.Console._







object ZIODaily extends ZIOAppDefault{
    val someInt: ZIO[Any, Nothing, Option[Int]] = ZIO.some(3)
    val noneInt: ZIO[Any, Nothing, Option[Nothing]] = ZIO.none

    val zioNoneOrFail: ZIO[Any, String, Unit] = ZIO.noneOrFail(Some("g"))
    val zioNoneOrFail2: ZIO[Any, String, Unit] = ZIO.noneOrFail(None)

    val r2: ZIO[Any, Unit, String] = ZIO.getOrFailUnit(Some("r"))
    val mappedValue: UIO[Int] = ZIO.succeed(21).map(_ * 2)

    val list = List(1, 2, 3, 4, 5, 6, 7, 8)
    val mappedList = list.map(_ * 2).filter(_ >= 30)
    val zioList: UIO[List[Int]] = ZIO.succeed(list)


    def run = for {
        _ <- printLine("Hello, world")
    } yield ()
}

import java.io.IOException

object MainApp extends ZIOAppDefault {
  def isPrime(n: Int): Boolean =
    if (n <= 1) false else (2 until n).forall(i => n % i != 0)

  val myApp: ZIO[Any, IOException, Unit] =
    for {
      ref <- Ref.make(List.empty[Int])
      prime <-
        Random
          .nextIntBetween(0, Int.MaxValue)
          .tap(random => ref.update(_ :+ random))
          .repeatUntil(isPrime)
      _ <- Console.printLine(s"found a prime number: $prime")
      tested <- ref.get
      _ <- Console.printLine(
        s"list of tested numbers: ${tested.mkString(", ")}"
      )
    } yield ()

  def run = myApp
}

object ZIO2 extends ZIOAppDefault {
    val zipRight1 =
        Console.printLine("What is your name?").zipRight(Console.readLine)

    val zipRight2 =
        Console.printLine("What is your name?") *>
        Console.readLine

    val foreachZIO = ZIO.foreach(Range.inclusive(2, 10)){ x => Console.printLine(x) }
    
    val race = for {
        winner <- ZIO.succeed("Hello").race(ZIO.succeed("Goodbye"))
    } yield winner

    val timeout = ZIO.succeed("Hello").timeout(5.seconds)

    val zeither: UIO[Either[String, Int]] = ZIO.fail("Uh oh!").either
    // produces a ZIO[R, Nothing, Either[E, A]]
    def sqrt(io: UIO[Double]): IO[String, Double] =
        ZIO.absolve(
            io.map(value =>
                if (value < 0.0) Left("Value must be >= 0.0")
                else Right(Math.sqrt(value))
            )
        )

    def run = for {
        // _ <- zipRight1
        // _ <- zipRight2
        _ <- foreachZIO
    } yield ()


}

object ZIO3 extends ZIOAppDefault {
    val effect1: UIO[String] = ZIO.succeed("Effect1")
    val effect2: UIO[String] = ZIO.succeed("Effect2")

    val zipPar1 = effect1.zipPar(effect2)

    val raceEither1 = effect1.raceEither(effect2)

    val foreachParZIO = ZIO.foreachPar(1 to 5){ x => Console.printLine(x) }

    val nameList = List("Alan", "Betty", "Charlie", "Daniel", "Eric", "Francis")
    val foreachParName = ZIO.foreachPar(nameList){ x => Console.printLine(x.toLowerCase) }
    // val
    // foreachParDiscard (use the "Discard" variants if you don't want to return a value,
    // this optimized the process )
    // Makes more sense when we are doing something other than returning Unit 
    // with "printLine" anyway

    val printList = List(printLine("A"), printLine("B"), printLine("C"))
    val printIO = ZIO.succeed(printList)

    val collectPrintPar = ZIO.collectAllPar(printList)
    val collectPrintParDiscard = ZIO.collectAllParDiscard(printList)

    // use these variants with the "N" suffix to limit the degree of parallelism,
    // which makes sense for massive collections

    

    

    def run = for {
        _ <- zipPar1
        _ <- raceEither1
        _ <- foreachParZIO
        _ <- foreachParName
        _ <- collectPrintPar
        _ <- collectPrintParDiscard

    } yield ()


}

// object ZIO4 extends ZIOAppDefault {

    // val promises = for { 
    //     ref <- ref.make(false)
    //     promise <- Promise.make[Nothing, Unit]
    //     effect = promise.succeed(()) *> ZIO.never
    //     finalizer = ref.set(true).delay(1.second) 
    //     fiber <- effect.ensuring(finalizer).fork
    //     _         <- promise.await
    //     _         <- fiber.interrupt
    //     value1     <- ref.get
    // } yield value1


    // val race: IO[String, Int] = for {
    //     p     <- Promise.make[String, Int]
    //     _     <- p.succeed(1).fork
    //     _     <- p.complete(ZIO.succeed(2)).fork
    //     _     <- p.completeWith(ZIO.succeed(3)).fork
    //     _     <- p.done(Exit.succeed(4)).fork
    //     _     <- p.fail("5")
    //     _     <- p.failCause(Cause.die(new Error("6")))
    //     _     <- p.die(new Error("7"))
    //     _     <- p.interrupt.fork
    //     value <- p.await
    // } yield value

    // def run = value1
// }

object RefOne extends ZIOAppDefault {

    // def make[A](a: A): UIO[Ref[A]]

    val counterRef = Ref.make(0)

    val stringRef = Ref.make("initial")

    sealed trait State
    case object Active  extends State
    case object Changed extends State
    case object Closed  extends State

    val stateRef = Ref.make(Active) 


    

    def run = for {
        _ <- ZIO.succeed(1)
    } yield ()
}















