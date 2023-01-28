

import zio._
import zio.Console._
import zhttp.http._
import zhttp.service.Server


object ZHttpExample extends ZIOAppDefault {

  // Create HTTP route
  val app: HttpApp[Any, Nothing] =
    Http.collect[Request] {
      case Method.GET -> !! / "text" => Response.text("Hello World!")
      case Method.GET -> !! / "json" =>
        Response.json("""{"greetings": "Hello World!"}""")
    }

  def run =
    Server.start(8090, app)
}

object REF2 extends ZIOAppDefault {
    val refOne = Ref.make("initial")
        .flatMap(_.get)
        .flatMap(current => Console.printLine(s"current value of ref: $current"))

    val refTwo = for {
        ref   <- Ref.make("initial")
        _     <- ref.set("update")
        value <- ref.get
    } yield assert(value == "update")

    val refThree = {
        val counterInitial = 0
        for {
            counterRef <- Ref.make(counterInitial)
            _          <- counterRef.update(_ + 1)
            value <- counterRef.get
        } yield assert(value == 1)
    }

    def run = refTwo
}

object CountRequests extends ZIOAppDefault {

    def repeat[E, A](n: Int)(io: IO[E, A]): IO[E, Unit] =
    Ref.make(0).flatMap { iRef =>
        def loop: IO[E, Unit] = iRef.get.flatMap { i =>
        if (i < n)
            io *> iRef.update(_ + 1) *> loop
        else
            ZIO.unit
        }
        loop
    }

    val refLoop = repeat(3)(Console.printLine("Hello"))

    def request(counter: Ref[Int]): ZIO[Any, Nothing, Unit] = {
        for {
            _ <- counter.update(_ + 1)
            reqNumber <- counter.get
            _ <- Console.printLine(s"request number: $reqNumber").orDie
        } yield ()
    }

    private val initial = 0
    private val myApp =
    for {
        ref <- Ref.make(initial)
        _ <- request(ref) zipPar request(ref)
        rn <- ref.get
        _ <- Console.printLine(s"total requests performed: $rn").orDie
    } yield ()

    def request2(counter: Ref[Int]) = {
        for {
            rn <- counter.modify(c => (c + 1, c + 1))
            _  <- Console.printLine(s"request number received: $rn")
        } yield ()
    }

    def run = refLoop
}


object REF3 extends ZIOAppDefault {
    val ref1 = for {
        ref <- Ref.make(0)
        _ <- ZIO.foreachParDiscard((1 to 10000).toList) {
                _ => ref.update(_ + 1)
            }
        result <- ref.get

    } yield result

    val ref2 = Ref.make(List("State1", "State2", "State3"))
    val ref3 = ref2.flatMap(x => x.update(_.map(r => r + "0")))
    

    // val meanAge =
    //     for {
    //         ref <- Ref.Synchronized.make(0)
    //         _ <- ZIO.foreachPar(users) { user =>
    //         ref.updateZIO(sumOfAges =>
    //             api.getAge(user).map(_ + sumOfAges)
    //         )
    //         }
    //         v <- ref.get
    //     } yield (v / users.length)

    def run = printLine(ref3)

}

object REF4 extends ZIOAppDefault {
    def updateAndLog[A](ref: Ref[A])(f: A => A): URIO[Any, Unit] = 
        ref.modify { oldValue =>
            val newValue = f(oldValue)
            ((oldValue, newValue), newValue) 
        }.flatMap { 
            case (oldValue, newValue) =>
                Console.printLine(s"updated $oldValue to $newValue").orDie
        }

    val ref = Ref.make(1)
    // val refChange = updateAndLog(ref)(x => x + 2)
    // val getRef = refChange.get

    def run = ???
}

object REFSYNC1 extends ZIOAppDefault {
   val refSync = for {
        ref <- Ref.Synchronized.make("current")
        updateEffect = ZIO.succeed("update")
        _ <- ref.updateZIO(_ => updateEffect)
        value <- ref.get
    } yield assert(value == "update")

    def run = refSync
}


object FiberRef extends ZIOAppDefault {
    // def log(ref: FiberRef[Log])(string: String): UIO[Unit] = ref.update(log => log.copy(head = log.head :+ string))
    
    def run = ???
        // for {
        //     ref <- loggingRef 
        //     left = for {
        //             a <- ZIO.succeed(1).tap(_ => log(ref)("Got 1"))
        //             b <- ZIO.succeed(2).tap(_ => log(ref)("Got 2")) 
        //         } yield a + b
        //     right = for {
        //             c <- ZIO.succeed(1).tap(_ => log(ref)("Got 3")) 
        //             d <- ZIO.succeed(2).tap(_ => log(ref)("Got 4"))
        //         } yield c + d 
        //     fiber1 <- left.fork
        //     fiber2 <- right.fork
        //     _      <- fiber1.join
        //     _      <- fiber2.join
        //     log    <- ref.get
        //     _      <- Console.printLine(log.toString)
        // } yield ()
}

object FiberRef2 extends ZIOAppDefault {

    val run = Console.printLine("FiberRef")
}














