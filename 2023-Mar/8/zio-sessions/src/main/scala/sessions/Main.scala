
import zio._
import zio.prelude._
import zio.prelude.newtypes._

import zio.prelude.Associative
import zio.prelude.Commutative
import zio.prelude.Inverse

import zio.{Chunk, NonEmptyChunk}

object ListMethods {

    def reverseLeft[T](xs: List[T]): List[T] =
        xs.foldLeft(List[T]()){ (acc, x) => x :: acc } 

    val sortWith = List(1, 3, 5, -5, 6, 2).sortWith(_ < _)
    println(sortWith) //List(-5, 1, 2, 3, 5, 6)

    val sortWithChunk = (Chunk(2, 7, -3, -4, -5) <> Chunk(1, 6, -2)).sortWith(_ < _)
    println(sortWithChunk) //Chunk(-5,-4,-3,-2,1,2,6,7)

    val sortWithLength = List("first", "second", "third", "last").sortWith(_.length < _.length)
    println(sortWithLength) //List(last, first, third, second)

    println(List.fill(10)("A")) // List(A, A, A, A, A, A, A, A, A, A)
    println(List.fill(4)(1)) // List(1, 1, 1, 1)

    println(List.range(1, 5)) // List(1, 2, 3, 4)
    println(List.range(1, 9, 2)) // List(1, 3, 5, 7)
    println(List.range(9, 1, -3)) // List(9, 6, 3)

    println(List.fill(2, 3)(0)) // List(List(0, 0, 0), List(0, 0, 0))

    println(List.tabulate(5)(x => x * x)) //List(0, 1, 4, 9, 16)
    println(List.tabulate(5, 2)(_ * _)) //List(List(0, 0), List(0, 1), List(0, 2), List(0, 3), List(0, 4))

}

object CommutativePrelude  {
    println()
}

object DebugPrelude {
    // Example 1
    case class Person(name: String, age: Int)

    object Person {
        implicit val PersonDebug: Debug[Person] =
            Debug.make { case Person(name, age) =>
                Debug.Repr.Constructor(
                    List.empty,
                    "Person",
                    "name" -> name.debug,
                    "age"  -> age.debug
                )
            }
    }

    val person = Person("John", 34)
    
    // Example 2
    sealed trait Validation[+E, +A]

    object Validation {

        case class Success[+A](a: A) extends Validation[Nothing, A]
        case class Failure[+E](es: NonEmptyChunk[E]) extends Validation[E, Nothing]

        implicit def ValidationDebug[E: Debug, A: Debug]: Debug[Validation[E, A]] =
            Debug.make {
                case Success(a)  => 
                    Debug.Repr.VConstructor(List("zio", "prelude"), "Validation.Success", List(a.debug))
                case Failure(es) => 
                    Debug.Repr.VConstructor(List("zio", "prelude"), "Validation.Failure", List(es.debug))
        }
    }
}





