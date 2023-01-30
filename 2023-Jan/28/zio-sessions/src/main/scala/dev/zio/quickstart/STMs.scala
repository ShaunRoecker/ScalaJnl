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



    def fund(senders: List[TRef[Int]], recipient: TRef[Int], amount: Int): STM[Throwable, Unit] =
        ZSTM.foreachDiscard(senders) { sender =>
            transfer(sender, recipient, amount)
        }

    // This will transfer funds from each sender to the recipient account. If any transfer fails 
    // because of insufficient funds the entire transaction will fail and all transactional 
    // variables will be rolled back to their original values.

    
    def autoDebit( account: TRef[Int], amount: Int): STM[Nothing, Unit] =
        account.get.flatMap { balance =>
            if (balance >= amount) account.update(_ - amount)
            else STM.retry 
        }
    
    // This retrying won’t happen in a busy loop where we are wasting system 
    // resources as we check the same balance over and over while nothing has changed. Rather, 
    // the STM implementation will only try this transaction again when one of the transactional 
    // variables, in this case the account balance, has changed.

    val retryTransaction = for {
        ref <- TRef.make(0).commit
        fiber <- autoDebit(ref, 100).commit.fork
        _     <- ref.update(_ + 100).commit
        _     <- fiber.await
    } yield ()

    // Don't include arbitray effects inside STM transactions,
    // because we don't know how many times the transaction will be retried.
    // Effects can be useful for debugging purposes, otherwise stay away from
    // them WITHIN an STM transaction.

    // For the same reason, you will not see operators related to concurrency 
    // or parallelism defined on ZSTM.

    //Forking a fiber is an observable effect and if we forked a fiber within 
    // an STM transaction we would potentially fork multiple fibers if the 
    // transaction was retried.


    // Note that while we can’t perform concurrency within an STM transaction, 
    // we can easily perform multiple STM transactions concurrently.
    // For example:
    val concurrencyWithSTMs = for { 
        alice      <- TRef.make(100).commit
        bob        <- TRef.make(100).commit
        carol      <- TRef.make(100).commit
        transfer1  = transfer(alice, bob, 30).commit
        transfer2  = transfer(bob, carol, 40).commit
        transfer3  = transfer(carol, alice, 50).commit
        transfers  = List(transfer1, transfer2, transfer3)
        _          <- ZIO.collectAllParDiscard(transfers)
    } yield ()


    val run = retryTransaction
}

object TArraySTM extends ZIOAppDefault {
    def swap[A](
        array: TArray[A], 
        i: Int,
        j: Int
    ): STM[Nothing, Unit] = for {
        a1 <- array(i)
        a2 <- array(j)
        _  <- array.update(i, _ => a2)
        _  <- array.update(j, _ => a1)
    } yield ()

    def run = for {
        _ <- Console.printLine("TArray")

    } yield ()
}