import zio._
// REF

object RefDocs extends ZIOAppDefault {

  // enum State:
  //   case Active, Changed, Closed

  sealed trait State
  case object Active extends State
  case object Changed extends State
  case object Closed extends State

  val stateRef = Ref.make(Active)
  // note: Refs must contain immutable data

  val init = 0
  val counterRef = Ref.make(init)

  def run = for {
    _ <- Console.printLine("Ref")
    counterRef <- Ref.make(0)
    stringRef <- Ref.make("initial")
    current <- Ref.make("initial")
                .flatMap(_.get)
                .flatMap(c => Console.printLine(s"current value of ref: $c")) 
    currentWFor <- for {
                    ref <- Ref.make("initial2")
                    ref1 <- ref.get
                    _ <- Console.printLine(s"current ref2 value: $ref1")
                  } yield ()
    // Note that, there is no way to access the shared state outside 
    // the monadic operations.

    ref5 <- Ref.make("initial?")
    _ <- ref5.update(_.dropRight(1) + "ized")
    _ <- ZIO.debug(ref5)

    _ <- ZIO.debug(current)


  } yield ()

}

object CountRequest extends ZIOAppDefault {
  def request(counter: Ref[Int]) = counter.update(_ + 1)

  private val initial = 0
  def run = for {
    ref <- Ref.make(initial)
    _ <- request(ref) zipPar request(ref)
    rn <- ref.get
    _ <- Console.printLine(s"total requests performed: ${rn}")
  } yield ()

}

object repeatCombo extends ZIOAppDefault {
  def repeatX[E, A](n: Int)(io: IO[E, A]): IO[E, Unit] =
    Ref.make(0).flatMap { iRef => 
      def loop: IO[E, Unit] = iRef.get.flatMap { i =>
        if (i < n)
          io *> iRef.update(_ + 1) *> loop
        else
          ZIO.unit
      }
      loop
    }

  def run = repeatX(5)(Console.printLine("repeatX"))
}

// modify performs get - set - get atomically on a Ref
object RefModify extends ZIOAppDefault {
  def request(counter: Ref[Int]) = {
    for {
      rn <- counter.modify(c => (c + 1, c + 1))
      _ <- Console.printLine(s"request number recieved: $rn")
    } yield ()
  }
  

  def run = ???
}

object RefModify2 extends ZIOAppDefault {
  def run = for {
    ref <- Ref.make(0)
    aref <- ref.updateAndGet(_ + 10)
    _ <- ZIO.debug((s"initail ref value: $ref", s"after updateAndGet: $aref")) 

    ref2 <- Ref.make(0)
    _ <- ref2.update(_ + 20)
    _ <- ZIO.debug(ref2)

    ref3 <- Ref.make(0)
    newValueReturned <- ref3.modify(value => ("new value is string", value + 100))
    _ <- ZIO.debug(
          (s"new value returned: $newValueReturned", s"value ref3 was set to: $ref3")
        )
  } yield ()
}

