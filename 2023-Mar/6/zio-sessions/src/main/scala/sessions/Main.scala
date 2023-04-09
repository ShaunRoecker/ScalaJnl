
import zio._
import zio.prelude._
import zio.prelude.newtypes._

import zio.prelude.Associative
import zio.prelude.Commutative
import zio.prelude.Inverse


object Folding {

    val words = List("thick", "quick", "brown", "stick")

    println(List(1, 2, 3, -4, 5).takeWhile(_ > 0)) //List(1, 2, 3)
    println(words.takeWhile(_.contains("ck"))) //List(thick, quick)

    println(words.dropWhile(_.startsWith("t"))) //List(quick, brown, stick)

    // xs.span(p) == (xs.takeWhile(p), xs.dropWhile(p))
    println(words.span(_.contains("ck"))) //(List(thick, quick),List(brown, stick))

    val boolRes = words.forall(x => x.length > 4)
    println(boolRes) //true

    val fl1 = words.foldLeft("")(_ + " " + _).trim() //thick quick brown stick
    println(fl1)
    // or
    println(words.tail.foldLeft(words.head)(_ + " " + _))

    // List reversal using fold
    val reversed = List(1, 2, 3).foldLeft(List[Int]()) { (acc, x) => x :: acc }
    println(reversed) //List(3, 2, 1)

}

object PreludeIndex extends scala.App {
    // trait Associative[A]:
    //     def combine(left: => A, right: => A): A

    val intAssociative: Associative[Int] = 
        new Associative[Int] {
            def combine(left: => Int, right: => Int): Int = 
                left + right
        }
    
    val notIntAssociative: Associative[Int] =
        new Associative[Int] {
            def combine(left: => Int, right: => Int): Int = 
                left - right
        }   // This isn't associative (a + b) + c != a + (b + c)

    // This shows that abstractions are not meaningful without laws. 
    // Abstractions describe some common structure that is shared 
    // between different data types but without laws we don't know 
    // what this structure is supposed to be.

    // Below example uses the concept of associative combining operation without
    // actually using the abstractions in ZIO Prelude
    case class RunningAverage(sum: Double, count: Int) { self =>
        def average: Double =
            sum / count
        def combine(that: RunningAverage): RunningAverage =
            RunningAverage(self.sum + that.sum, self.count + that.count)
    }

    object RunningAverage {
        val empty: RunningAverage =
            RunningAverage(0.0, 0)
    }

    // The same idea as above, only using type classes:
    import zio.Chunk

    trait Associative[A] {
        def combine(left: => A, right: => A): A
    }

    object Associative {

        implicit val IntAssociative: Associative[Int] =
            new Associative[Int] {
                def combine(left: => Int, right: => Int): Int =
                    left + right
            }

        implicit def ListAssociative[A]: Associative[List[A]] =
            new Associative[List[A]] {
                def combine(left: => List[A], right: => List[A]): List[A] =
                    left ::: right
            }

        implicit final class AssociativeSyntax[A](private val self: A) {
            def <>(that: => A)(implicit associative: Associative[A]): A =
                associative.combine(self, that)
        }
    }
    
    //If the instance of the type class depends on other parameters, 
    // like the A in ListAssociative we define it as an implicit def. 
    // Otherwise, we define it as an implicit val.

    //In the type class pattern we also typically define extension methods 
    // that will be available on any data type for which an instance of 
    // the type class is defined.

    

    //This machinery allows us to use the <> operator to combine values 
    // of any type as long as an Associative instance is defined for it.

    // val int: Int = 1 <> 2

    val list: List[Int] =
        List(1, 2, 3) <> List(4, 5, 6)

    //println(int)
    println(list)
    

}







