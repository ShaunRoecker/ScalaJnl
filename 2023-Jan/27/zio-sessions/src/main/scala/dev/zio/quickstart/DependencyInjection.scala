import zio._
import zio.Console._

object DependencyInjection extends ZIOAppDefault {
    // We access a sercive and then can use ZIO to add combinators to do something with it:
    // ZIO.service[Scope].flatMap(scope => scope.addFinalizer(???))

    // Below is a shorthand for doing the same thing, we can drop the flatMap:
    // ZIO.serviceWithZIO[Scope](scope => scope.addFinalizer(???))
    lazy val needsAnInt: ZIO[Int, Nothing, Unit] = 
        ZIO.serviceWithZIO[Int](ZIO.debug(_))

    lazy val readyToRun: ZIO[Any, Nothing, Unit] = 
        needsAnInt.provideEnvironment(ZEnvironment(1))




    def run =readyToRun
  
}

