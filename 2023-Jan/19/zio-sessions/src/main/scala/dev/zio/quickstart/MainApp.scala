

import zio._
import zio.test._
import zio.test.Assertion._
import zio.test.TestAspect._


// object TestingSuite extends ZIOSpecDefault:

object Example{
  // def spec = suite("ExampleSpec")( 
  //   test("addition works") {
  //     assert(1 + 1)(equalTo(2))
  //   }
  // ) 
  // def spec = suite("ExampleSpec")(
  //   test("ZIO.succeed succeeds with specified value") {
  //     assertZIO(ZIO.succeed(1 + 1))(equalTo(2))
  //   }
  // )

  // def spec = suite("ExampleSpec")( 
  //   test("and") {
  //     for {
  //       x <- ZIO.succeed(1) 
  //       y <- ZIO.succeed(2)
  //     } yield assert(x)(equalTo(1)) && 
  //         assert(y)(equalTo(2))
  //   } 
  // )

  // def spec = suite("ExampleSpec")( 
  //     test("hasSameElement") {
  //       assert(List(1, 1, 2, 3))(hasSameElements(List(3, 2, 1, 1)))
  //     }
  // ) 

  // def spec = suite("ExampleSpec")( 
  //   test("fails") {
  //     for {
  //       exit <- ZIO.attempt(1 / 0).catchAll(_ => ZIO.fail(())).exit
  //     } yield assert(exit)(fails(isUnit)) 
  //   }
  // )

  // val assertion: Assertion[Iterable[Int]] = isNonEmpty && forall(nonNegative)

  // val assertion: Assertion[Iterable[Any]] = isEmpty || hasSize(equalTo(3))

  // val assertion: Assertion[Iterable[Any]] = not(isDistinct)

  val greet: ZIO[Any, Nothing, Unit] = for {
      name <- Console.readLine.orDie
      _ <- Console.printLine(s"Hello, $name!").orDie 
  } yield ()


  def run = for {
    _ <- greet
  } yield ()



}

