import zio._
import zio.stm._


object STMs extends ZIOAppDefault {
    
    val stm1: Task[Int] = for {
        ref <- TRef.make(0).commit
        increment = ref.get.flatMap(n => ref.set(n + 1)).commit
        _ <- ZIO.collectAllPar(ZIO.replicate(100)(increment)) 
        value <- ref.get.commit
    } yield value

    def transfer( from: TRef[Int], to: TRef[Int], amount: Int): STM[Throwable, Unit] = 
        for {
            senderBalance <- from.get
            _             <- if (amount > senderBalance)
                                STM.fail(new Throwable("insufficient funds")) 
                            else
                                from.update(_ - amount) *>
                                to.update(_ + amount)
        } yield ()


    val printSomething: IO[Throwable, Unit] = Console.printLine("Something")


    val run = for {
        _ <- ZIO.collectAllPar(ZIO.replicate(5)(printSomething))
    } yield ()
}