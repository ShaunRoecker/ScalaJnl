import zio._
import zio.stm._

object TPriorityQueueX extends ZIOAppDefault {
    final case class Event(time: Int, action: UIO[Unit])

    object Event {
        implicit val EventOrdering: Ordering[Event] =
            Ordering.by(_.time)
        }
    
    val tPQueue = for {
        queue <- TPriorityQueue.empty[Event]
    } yield queue

    def run = for {
        _ <- Console.printLine("Hello World!")

        
    } yield ()
}
