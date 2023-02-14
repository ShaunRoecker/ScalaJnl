
import zio._

object Hub1 extends ZIOAppDefault {
    
    val hub1 = for {
        hubb <- Hub.bounded[String](16)
        sub1 <- hubb.subscribe
        sub2 <- hubb.subscribe
        _ <- hubb.publish("Hello, world!")
        _ <- sub1.take.flatMap(Console.printLine(_))
        _ <- hubb.publish("Hello, world!2222")
        _ <- sub1.take.flatMap(Console.printLine(_))
        _ <- sub2.take.flatMap(Console.printLine(_))


    } yield ()

    def run = hub1
    //     for {
    //     _ <- Hub.bounded[String](2).flatMap { hub =>
    //         ZIO.scoped {
    //             hub.subscribe.zip(hub.subscribe).flatMap { case (left, right) =>
    //                 for {
    //                     _ <- hub.publish("Hello from a hub!")
    //                     _ <- left.take.flatMap(Console.printLine(_))
    //                     _ <- right.take.flatMap(Console.printLine(_))
    //                 } yield ()
    //             }
    //         }
    //     }
    // } yield ()
}
