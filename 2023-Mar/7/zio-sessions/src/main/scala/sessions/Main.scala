
import zio._
import zio.prelude._
import zio.prelude.newtypes._

import zio.prelude.Associative
import zio.prelude.Commutative
import zio.prelude.Inverse


object ListMethods  {

    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)

    // Alias for concat. Concat - Returns a new sequence containing the elements 
    // from the left hand operand followed by the elements from the right hand operand.
    println(list1 ++ list2) // List(1, 2, 3, 4, 5, 6)


    // Alias for appendedAll. appendedAll - Returns a new list containing the elements 
    // from the left hand operand followed by the elements from the right hand operand.
    println(list1 ++: list2) // List(1, 2, 3, 4, 5, 6)

    println(list1 :++ list2) //List(1, 2, 3, 4, 5, 6)


    // Alias for appended. appended - A copy of this sequence with an element appended.
    println(list1 :+ 4) //List(1, 2, 3, 4)

    println( 4 +: list1) //List(4, 1, 2, 3)


    // Adds an element at the beginning of this list. 
    println(list1 :: list2) //List(List(1, 2, 3), 4, 5, 6)

    println(list2 :: list1) // List(List(4, 5, 6), 1, 2, 3)


    // Adds the elements of a given list in front of this list.
    println(list1 ::: list2) //List(1, 2, 3, 4, 5, 6)

    println(list2 ::: list1) //List(4, 5, 6, 1, 2, 3)
    

}

object AssociativePrelude {

    // Less ideal way of doing this:

    // final case class Topic(value: String)

    // final case class Votes(value: Int) { self =>
    //     def combine(that: Votes): Votes =
    //         Votes(self.value + that.value)
    // }

    // final case class VoteMap(map: Map[Topic, Votes]) {self =>
    //     def combine(that: VoteMap): VoteMap =
    //         VoteMap(that.map.foldLeft(self.map) { case (map, (topic, votes)) =>
    //             map + (topic -> map.getOrElse(topic, Votes(0)).combine(votes))
    //         })
    // }

    // Using Prelude:
    case class Topic(value: String)

    case class Votes(value: Int)

    object Votes {
        implicit val VotesAssociative: Associative[Votes] =
            new Associative[Votes] {
                def combine(left: => Votes, right: => Votes): Votes =
                    Votes(left.value + right.value)
            }
    }

    case class VoteMap(map: Map[Topic, Votes])

    object VoteMap {
        implicit val VoteMapAssociative: Associative[VoteMap] =
            new Associative[VoteMap] {
                def combine(left: => VoteMap, right: => VoteMap): VoteMap =
                    VoteMap(left.map <> right.map)
            }
    }

    def wordCount(lines: List[String]): Int =
        lines.map(_.split(" ").length).sum

    println(wordCount(List("This is the first line", "and the second", "and third"))) // 10

    def mapReduce[F[+_]: ForEach, A, B: Identity](as: F[A])(f: A => B): B =
        as.foldMap(f)

    
}

object ParameterizedTypes {
    // Parameterized types

    // Covariant- containers or producers of A values
    trait Covariant[+A]
        // Types that contain A values:
        // Option
        // Either
        // List
        // Vector

        // Types that maybe doesn't have an A inside it now, but at some point it 
        // will or could have an A value inside it:
        // ZIO
        // ZManaged
        // ZStream
        // Future
        // Iterator
        // Decoder (String => A) (JSON serialization, for example)

        // All of these ^^ are like "producers" of A values, or like "sources" of A values

        // Stream[+A]
        // Transducer[-A, +B]
        // Sink[-A]
            // concrete
        // Stream[String]
        // Transducer[String, Double] ==> take the length of each string
        // Sink[Double]

    // Contravariant -  takes in or consumers of A values and does something with them
    trait Contravariant[-A]  
        //  Functions with their input type A => Boolean 
        // Ordering (A, A) => OrderingResult
        // ZSink => Chunk[A] => (Chunk[L], Result)
        // ZIO Environment
        // Encoder (A => String) (JSON deserialization, for example)


    // Invariant - Both produces or contains A values, as well as consumes A values
    // These types tend to have less ways we can compose them
    trait Invariant[A]


    // Container examples- +A (Covariant)

    // AssociativeBoth
    // AssociativeEither


    case class Box[+A](value: A) { self =>
        def map[B](f: A => B): Box[B] = 
            Box(f(value))

        // the zip operator is an example of this
        def both[B](that: Box[B]): Box[(A, B)] =  // Product type
            Box(self.value, that.value)

        def either[B](that: Box[B]): Box[Either[A, B]] = ??? //Sum type
            // Box(Either(self.value, that.value))
    }

    // case class FallibleBox[+A](value: Either[Throwable, A]) { self =>
    //     def map[B](f: A => B): FallibleBox[B] = 
    //         FallibleBox(value.map(f))

    //     // 1. Can I combine?
    //     // 2. Zip -> Product type -> Tuple
    //     // 3. OrElse -> Sum type -> Either

    //     def both[B](that: FallibleBox[B]): FallibleBox[(A, B)] =
    //         FallibleBox(
    //             for {
    //                 a <- self
    //                 b <- that
    //             } yield (a, b)
    //         )

    //     def either[B](that: FallibleBox[B]): FallibleBox[Either[A, B]] = 
    //         FallibleBox(
    //             self match {
    //                 case Left(_) => that.value.map(Right(_))
    //                 case Right(a) => Right(a) //
    //             }
    //         )
    // }

    trait Stream[+A] {

    }

    // Producer examples- +A (Covariant)

}







