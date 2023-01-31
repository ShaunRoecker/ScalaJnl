import zio._
import zio.stm._

object SomeUnsome extends ZIOAppDefault {

    def sqrt(io: UIO[Double]): IO[String, Double] = 
        ZIO.absolve {
            io.map( value =>
                if (value < 0.0) Left("Value must be >= 0.0") 
                else Right(Math.sqrt(value))
            )
        }
    
    def mapValue(io: UIO[List[Int]]): UIO[List[Int]] =
        io.map( value => value.map(_ + 1))

    val someUnsome: Task[Option[String]] = ZIO.attempt(Option("something"))// ZIO[Any, Throwable, Option[String]]
        .some                          // ZIO[Any, Option[Throwable], String]
        .unsome                        // ZIO[Any, Throwable, Option[String]]


    val constant = 43

    val asSomeVal: UIO[Option[Int]] =
        ZIO.succeed(42).asSome




    def run = for {
        effect <- ZIO.fail("43").either
        _ <- ZIO.debug(effect)
        sqroot <- sqrt(ZIO.succeed(2.0d))
        _ <- ZIO.debug(sqroot)
        mpVal <- mapValue(ZIO.succeed(List(1, 2, 3, 4)))
        _ <- ZIO.debug(mpVal) //List(2, 3, 4, 5)
        x <- ZIO.succeed(42).map(a => a * 20)
        _ <- someUnsome.debug
        _ <- asSomeVal.debug
    } yield ()

}

object TArraySTM2 extends ZIOAppDefault {
    val tarray1 = TArray.make[Int](1, 2, 3)

    val tarray2 = TArray.fromIterable(List(1, 2, 3, 4))

    val printSomething = Console.printLine("Something").replicateZIODiscard(5)

    val transaction = for {
        array <- TArray.make(1, 2, 3)
        _ <- array.transform(_ + 1) 
        list <- array.toList
    } yield list

    val transaction2 = for {
        array <- TArray.make(1, 2, 3)
        _ <- array.transform(_ + 1) 
        count <- array.count(_ == 2)
        list <- array.toList
    } yield list
    


    def run = for {
        _ <- Console.printLine(tarray1)
        _ <- ZIO.debug(tarray2)
        _ <- printSomething
        _ <- transaction2.map(x => Console.printLine(x))
        
    } yield ()
}
