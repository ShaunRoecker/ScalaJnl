import zio._

object fibreReef extends ZIOAppDefault {

    final case class User(id: Long)

    trait Database:
        def persistUser(id: Long): ZIO[Any, Nothing, Unit]

    def persistUser(user: User): ZIO[Database & Logging /*& PrinterLive*/, Any, Unit] =
        for {
            _ <- Logging.log(s"about to persist user ${user.id}")
            _ <- ZIO.serviceWithZIO[Database](_.persistUser(user.id))
            //_ <- PrinterLive.prettyPrint(s"${user.id} persisted...")
        } yield ()

    object Database:
        val dummy: ZLayer[Any, Nothing, Database] =
            ZLayer.succeed {
                new Database {
                    def persistUser(id: Long): ZIO[Any, Nothing, Unit] =
                        ZIO.debug(s"Persisting user: ${id}")
                }
            }

    trait Logging:
        def log(message: String): ZIO[Any, Nothing, Unit]

    
    object Logging:
        private val noop = new Logging {
            def log(message: String): ZIO[Any, Nothing, Unit] =
                Console.printLine("DEFAULT LOGGER " + message).orDie
        }
    
        val currentLogger: FiberRef[Logging] =
            Unsafe.unsafe { implicit unsafe =>
                FiberRef.unsafe.make(noop)
            }

        val silent = new Logging {
            def log(message: String): ZIO[Any, Nothing, Unit] =
                ZIO.unit
        }

        def log(message: String): ZIO[Any, Nothing, Unit] =
            currentLogger.get.flatMap(_.log(message))

        
        // val layer: ZLayer[Any, Nothing, Logging] =
        //     ZLayer.succeed {
        //         new Logging {
        //             def log(message: String): ZIO[Any, Nothing, Unit] =
        //                 ZIO.log(message)
        //         }
        //     } 
    
        
        
    
    trait Printer:
        def prettyPrint(s: String): ZIO[Any, Throwable, Unit]

    case class PrinterLive(s: String) extends Printer:
        override def prettyPrint(s: String): ZIO[Any, Throwable, Unit] = 
            ZIO.attempt {
                println { scala.Console.CYAN + s + scala.Console.RESET }  
            }.orDie.provideEnvironment(ZEnvironment(s))

    object PrinterLive:
        val layer: ZLayer[String, Throwable, Printer] =
            ZLayer.fromFunction(PrinterLive.apply _)

        def prettyPrint(s: String): ZIO[PrinterLive, Throwable, Unit] =
            ZIO.serviceWithZIO[PrinterLive](_.prettyPrint(s))
    
    val x34 = for {
        ref <- Ref.make(0)
        newValue <- ref.modify { nv =>
            (nv.toString + "d", nv + 1)
        }
        _ <- ZIO.debug(newValue)

    } yield ()

    def run = 
        for {
            // ref <- FiberRef.make(0)
            // left <- (ref.updateAndGet(_ + 1).debug("left1") *> 
            //             ref.updateAndGet(_ + 1).debug("left2")).fork
            // right <- (ref.updateAndGet(_ + 1).debug("right1") *> 
            //             ref.updateAndGet(_ + 1).debug("right2")).fork
            // _ <- (right.zip(left)).join
            // adding Database
            _ <- persistUser(User(1)).provide(Database.dummy)

            // _ <- x34
            

        } yield ()

}
