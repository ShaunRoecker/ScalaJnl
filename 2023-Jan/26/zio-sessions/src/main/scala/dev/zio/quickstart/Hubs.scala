
import zio._

object Hubs extends ZIOAppDefault {

    Hub.bounded[String](2).flatMap { hub =>
        ZIO.scoped {
            hub.subscribe.zip(hub.subscribe).flatMap { case (left, right) =>
                for {
                    _ <- hub.publish("Hello from a hub!")
                    _ <- left.take.flatMap(Console.printLine(_))
                    _ <- right.take.flatMap(Console.printLine(_))
                } yield ()
            }
        }
    }

    
    def run = ???
  
}
