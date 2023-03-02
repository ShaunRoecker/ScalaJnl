package sessions

import zio._
import zio.Console._
import zio.prelude._
import zio.prelude.newtypes._
import zio.prelude.Newtype
import zio.Chunk


object Preludez extends scala.App {
    // trait MyTrait { self => }
    // // is equivalent to
    // trait MyTrait2 {
    //      private val self = this
    // }
    trait Function1Option[-A, +B] { self => // use this self alias when the compiler
        def apply(a: A): Option[B]          // may get confused about inner and outer 
                                            // function contexts: use "self =>" to "force"
                                            // the outer context
        final def andThen[C](that: Function1Option[B, C]): Function1Option[A, C] =
            new Function1Option[A, C] {
                override def apply(a: A): Option[C] = self.apply(a) match {
                    case Some(b) => that.apply(b)
                    case None => None
                }
            }

    }

}