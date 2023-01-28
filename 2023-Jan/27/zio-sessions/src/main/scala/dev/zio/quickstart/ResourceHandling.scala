
import zio._

object Hubs extends ZIOAppDefault {

    // acquireReleaseWith
    // acquireReleaseExitWith

    // Every acquire release requires three actions:

    // 1. Acquiring Resource— An effect describing the acquisition of resource. 
    //   For example, opening a file.

    // 2. Using Resource— An effect describing the actual process to produce a result. 
    //   For example, counting the number of lines in a file.

    // 3. Releasing Resource— An effect describing the final step of releasing or cleaning up the resource. 
    //   For example, closing a file.

    // def use(resource: Resource): Task[Any] = ZIO.attempt(???)
    // def release(resource: Resource): UIO[Unit] = ZIO.succeed(???)
    // def acquire: Task[Resource]                = ZIO.attempt(???)

    // val result: Task[Any] = ZIO.acquireReleaseWith(acquire)(release)(use)

    val heartbeat: ZIO[Scope, Nothing, Fiber[Nothing, Unit]] = 
        Console.printLine(".").orDie.delay(1.second).forever.forkScoped

    lazy val myProgramLogic: IO[Throwable, Unit] =
        Console.printLine("Hello").delay(5.seconds) *>
        Console.printLine("World!").delay(5.seconds) 
        
        
    def run = ZIO.scoped {
        for {
            _ <- heartbeat
            _ <- myProgramLogic
        } yield ()
    }
    

  
}
