
import zio._
import zio.prelude._
import zio.prelude.newtypes._

import zio.prelude.Associative
import zio.prelude.Commutative
import zio.prelude.Inverse

// 2 Classes of operators
// Unary operators  ==    A => A
// Binary operators ==   (A, A) => A
object Examples extends scala.App {

    // final case class Event(description: String)
    // we cant compose this ^^^

    // So...
    sealed trait Event { self =>
        def ++(that: Event): Event =
            Event.Sequential(self, that)
        
        def &&(that: Event): Event =
            Event.Parallel(self, that)
    }

    object Event {
        case object Empty extends Event
        final case class Single(description: String) extends Event
        final case class Parallel(left: Event, right: Event) extends Event
        final case class Sequential(left: Event, right: Event) extends Event
    }

    val events: List[Event] = List()




}





