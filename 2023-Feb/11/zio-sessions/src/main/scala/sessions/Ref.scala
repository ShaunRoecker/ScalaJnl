import zio._

object App1 extends ZIOAppDefault {
    val refOne = for {
        ref0 <- Ref.make(0)
        refNewValue <- ref0.modify{ value => 
            (s"Old ref value: ${value.toString}", value + 10)
        }
        _ <- ZIO.debug(
                (s"new ref value: ${ref0}", s"value returned from ref.modify: ${refNewValue}")
            )
    } yield ()

    def run = for {
        _ <- refOne
    } yield ()
}

object App2 extends ZIOAppDefault {
    def run = for {
        ref1 <- Ref.make(10)
        ref2 <- ref1.modify(value => (value + 10, value + 5))
                    .flatMap(newRefVal => Ref.make(newRefVal))
        _ <- ZIO.debug((s"Ref1: $ref1", s"Ref2: $ref2")) //(Ref1: Ref(15),Ref2: Ref(20))
    } yield ()
}

// In real-world applications, there are cases where we want to run an effect, 
// e.g. query a database, and then update the shared state.
object RefSync extends ZIOAppDefault {
    val refSync1 = for {
        refZync <- Ref.Synchronized.make("current")
        updateEffect = ZIO.succeed("update")
        _ <- refZync.updateZIO(_ => updateEffect)
        value <- refZync.get
    } yield value

    val refSync2 = for {
        ref <- Ref.Synchronized.make(0)
        //ops = Console.printLine("Calling a Database") *> ZIO.succeed(20)
        _ <- ref.updateZIO{ _ => 
            Console.printLine("Calling a Database") *> ZIO.succeed(20)
        }
        value <- ref.get
    } yield value


    def run = for {
        _ <- refSync1.debug
        _ <- refSync2.debug

    } yield ()

}

object RefSync2 extends ZIOAppDefault {
    import scala.collection.mutable.HashMap

    val users: List[String] = List("a", "b")

    object api {
        val users = scala.collection.mutable.HashMap("a" -> 20, "b" -> 30)

        def getAge(user: String): UIO[Int] = ZIO.succeed(users(user))
    }

    val meanAge =
        for {
            refSumOfAges <- Ref.Synchronized.make(0)
            _ <- ZIO.foreachPar(users) { user =>
                refSumOfAges.updateZIO(sumOfAges =>
                    api.getAge(user).map(_ + sumOfAges)
                )
            }
            v <- refSumOfAges.get
        } yield (v / users.length)

    def run = meanAge.debug
}

object RefSyncModifyZIO extends ZIOAppDefault {
    def run = for {
        ref <- Ref.Synchronized.make(0)
        newValue <- ref.modifyZIO{ value =>
                Console.printLine("effecting the effecting effect") *>
                ZIO.succeed((value.toString, value + 1))    
            }
        _ <- ZIO.debug(ref)
        _ <- Console.printLine(newValue)
        
    } yield ()
}

object RefSyncUpdateSomeZIO extends ZIOAppDefault {
    def run = for {
        ref <- Ref.Synchronized.make(10)
        _ <- ref.updateSomeZIO{
                case x if x > 10 => { Console.printLine(s"subtracting one to $x") *> 
                                       ZIO.succeed(x - 1)
                                    }
                case x if x < 10 => { Console.printLine(s"adding one to $x") *> 
                                       ZIO.succeed(x + 1)
                                    }
                case x if x == 10 => { Console.printLine(s"adding ten to $x") *> 
                                       ZIO.succeed(x + 10)
                                    }
            }
        x <- ref.get
        _ <- ZIO.debug(x)
    } yield ()
}

object RefModifySomeZIO extends ZIOAppDefault {
    def run = for {
        ref <- Ref.Synchronized.make(10)
        newValue <- ref.modifySomeZIO(1000){  // <- default value to return if PF isn't defined
                case x if x > 10 => { Console.printLine(s"subtracting one to $x") *> 
                                       ZIO.succeed(x - 1 , x - 1)
                                    }
                case x if x < 10 => { Console.printLine(s"adding one to $x") *> 
                                       ZIO.succeed(x + 1, x + 1)
                                    }
                case x if x == 10 => { Console.printLine(s"adding ten to $x") *> 
                                       ZIO.succeed(x + 10, x + 10)
                                    }
            }
        x <- ref.get
        _ <- ZIO.debug(s"Ref: $x", s"newValue: $newValue")
    } yield ()
}


object RefCompose extends ZIOAppDefault {
    def run = for {
        ref <- Ref.make(0)
        _ <- ref.update(_ + 1) *> ref.update(_ + 2)
        newRef <- ref.get
        _ <- ZIO.debug(newRef)
    } yield ()
}

object Ref11 extends ZIOAppDefault {
    def run = for {
        ref1 <- Ref.Synchronized.make(0)
        ref2 <- ref1.modifyZIO { value =>
            ZIO.succeed(value + 100, value + 1)    
        }.flatMap(Ref.make(_))
        ref1Value <- ref1.get
        ref2Value <- ref2.get
        _ <- ZIO.debug((ref1Value, ref2Value))
    } yield ()
}