import zio._
import zio.Console._
// import scala.util.Random
import zio.Random._


object Promises extends ZIOAppDefault {

    val promise1 = for {
        promise <- Promise.make[Nothing, Unit] 
        left <- (Console.print("Hello, ") *> 
                 promise.succeed(())).fork
        right <- (promise.await *> Console.printLine(" World!")).fork
        _     <- left.join *> right.join
    } yield ()

    val randomInt: UIO[Int] = Random.nextIntBetween(1, 20)

    val complete: UIO[(Int, Int)] = for {
        p <- Promise.make[Nothing, Int]
        _ <- p.complete(randomInt)
        l <- p.await
        r <- p.await
    } yield (l, r)

    val completeWith: UIO[(Int, Int)] = for {
        p <- Promise.make[Nothing, Int]
        _ <- p.completeWith(randomInt)
        l <- p.await
        r <- p.await
    } yield (l, r)

    val promiseABC: Task[Unit] = for {
        promiseA <- Promise.make[Nothing, Int]
        promiseB <- Promise.make[Nothing, Unit]
        promiseC <- Promise.make[Nothing, Unit]
        a <- (Console.printLine("A") *> promiseA.succeed((1))).fork
        b <- (promiseA.await *> Console.printLine(promiseA) *> promiseB.succeed(())).fork
        c <- (promiseB.await *> Console.printLine("C")).fork
        _ <- a.join *> b.join *> c.join
        _ <- ZIO.log("Finished!")
        _ <- ZIO.debug("Finished!")

    } yield ()

    val copyOnFork = 
        for {
            fiberRef <- FiberRef.make(5)
            promise <- Promise.make[Nothing, Int]
            _ <- fiberRef
                .updateAndGet(_ => 6)
                .flatMap(promise.succeed).fork
            childValue <- promise.await
            parentValue <- fiberRef.get
        } yield assert(parentValue == 5 && childValue == 6)
    

    

    // complete: : (9,9)
    // completeWith: : (9,17)

    // complete is similar to a call-by-value parameter, and completeWith is similar to
    // a call-by-name parameter.  With `complete`, the effect is executed in the complete 
    // method, so any fibers that are awaiting that promise will recieve the same value,
    // as opposed to `completeWith`, which is stores an effect in the Promise value, and 
    // is not evaluated until it is grabbed by the caller fiber.  

    // All the ways to complete a promise

    val successPromise: IO[String, Int] = for {
        p     <- Promise.make[String, Int]
        _     <- p.succeed(1).fork // Returns an A (a value)
        value <- p.await
        _ <- ZIO.debug(value) //1
    } yield value

    val completePromise: IO[String, (Int, Int)] = for {
        p     <- Promise.make[String, Int]
        _     <- p.complete(ZIO.succeed(2)).fork // Returns result of this effect (A value)
        value1 <- p.await                        
        value2 <- p.await                        
        _ <- ZIO.debug((value1, value2)) // (2,2)
    } yield (value1, value2) // They are the same with complete because the complete effect
                            //  is evaluated upon completion and not when each fiber calls 
                            // promise.await

    val completeWithPromise: IO[String, (Int, Int)] = for {
        p     <- Promise.make[String, Int]
        _     <- p.completeWith(Random.nextIntBetween(1, 20)).fork // Returns an effect A (a value)
        value1 <- p.await  // With completeWith, the effect is ran to generate a value EACH time
        value2 <- p.await  // a fiber calls await on it, so it's similar to cal-by-name
        _ <- ZIO.debug((value1, value2)) //(10,15)
    } yield (value1, value2)  //(10,15) Notice they are different

    // Use complete instead of completeWith, unless this behavior is specifically
    // what you are going for

    val donePromise: IO[String, Int] = for {
        p     <- Promise.make[String, Int]
        _     <- p.done(Exit.succeed(4)).fork //
        value <- p.await
        _ <- ZIO.debug(value) //4
    } yield value

    val failPromise: IO[String, Int] = for {
        p     <- Promise.make[String, Int]
        _     <- p.fail("failed promise, be better.").fork //
        value <- p.await
        _ <- ZIO.debug(value) //timestamp=2023-01-26T01:50:03.605917Z level=ERROR 
    } yield value             //thread=#zio-fiber-0 message="" cause="Exception in thread "zio-fiber-6" 
                              //java.lang.String: failed promise, be better.

    val failCausePromise: IO[String, Int] = for {
        p     <- Promise.make[String, Int]
        _     <- p.failCause(Cause.die(new Error("failed Promise with Cause, study your losses"))).fork //
        value <- p.await
        _ <- ZIO.debug(value) //4
    } yield value

    val diePromise: IO[String, Int] = for {
        p     <- Promise.make[String, Int]
        _     <- p.die(new Error("Fatality o-|-<")).fork //
        value <- p.await
        _ <- ZIO.debug(value) //
    } yield value

    val interuptPromise: IO[String, Int] = for {
        p     <- Promise.make[String, Int]
        _     <- p.interrupt.fork //
        value <- p.await
        _ <- ZIO.debug(value) //
    } yield value

    val ioPromise1: UIO[Promise[Exception, String]] = Promise.make[Exception, String]
    val ioBooleanSucceeded: UIO[Boolean] = ioPromise1.flatMap(promise => promise.succeed("I'm done"))

    //Polling
    // If we don't want to suspend the fiber, but we only want to query the state of
    //  whether or not the Promise has been completed, we can use poll:      
    val usePoll = for {
        p <- Promise.make[Exception, String]
        polled <- p.poll
        pollSome <- p.poll.some
        _ <- ZIO.debug((polled, pollSome))
    } yield ()

    import java.io.IOException

    val program: ZIO[Any, IOException, Unit] = 
    for {
        promise         <-  Promise.make[Nothing, String]
        sendHelloWorld  =   (ZIO.succeed("hello world") <* ZIO.sleep(1.second)).flatMap(promise.succeed)
        getAndPrint     =   promise.await.flatMap(Console.printLine(_))
        fiberA          <-  sendHelloWorld.fork
        fiberB          <-  getAndPrint.fork
        _               <-  (fiberA zip fiberB).join
    } yield ()

    val program2: ZIO[Any,IOException,Unit] =
        for {
            promise         <-  Promise.make[Nothing, String]
            fiber1 <- (promise.complete(ZIO.succeed("Hello"))).fork
            fiber2 <- (promise.await *> Console.printLine("After awaiting Fiber1...")).fork
            _ <-  fiber1.join *> fiber2.join
        } yield ()

    
    def run = for {
        // _ <- promise1
        // _ <- randomInt.debug("RANDOMINT: ")
        // _ <- complete.debug("complete: ")
        // _ <- completeWith.debug("completeWith: ")
        // bool <- Random.nextBoolean //
        // _ <- promiseABC
        // _ <- promiseABC.debug
        // _ <- successPromise
        // _ <- completePromise
        // _ <- completeWithPromise
        // _ <- donePromise
        // _ <- failPromise
        // _ <- failCausePromise
        // _ <- diePromise
        // _ <- interuptPromise
        // _ <- Console.printLine("finished")
        // _ <- usePoll
        _ <- program2

    } yield ()

}

object Cache extends ZIOAppDefault {

    def run = ???
}