package functional

import functional.datastructures.containers.{List, Nil, Cons}
import functional.datastructures.errorhandling.{Option, None, Some, Either}
import functional.datastructures.errorhandling.Various._
import functional.datastructures.errorhandling.Option._
import functional.datastructures.errorhandling.safeDiv
import functional.datastructures.errorhandling.Either._
import functional.datastructures.errorhandling.attempt
import functional.datastructures.strictness.{Stream, Empty, Consx}
import functional.datastructures.strictness.Stream._






import functional.problems.Solution1._


import scala.collection.immutable.{List => Lst}
import scala.{Option => Opt, Some => Sm, None => Nn, Either => Et}


object Program:
    @main
    def main =
        println("Functional Data Structures")

        val list1 = List(1,2,3,4,5,6,7)
        println(list1.sum) // 6
        println(list1.product) // 6
        println(List(1,2,3) ++ List(4,5,6))
        println(List(1,2,3).append(List(4,5,6)))
        // val list2: List[Double] = Nil // The data constructor Nil, since it doesn't contain any
        // // elements, can be used to construct a List of whatever type we want
        println(list1.tail)
        println(list1.setHead(1000))
        println(list1.drop(2))
        println(list1.init)
        println(list1.sum2)
        println(list1.length)
        println(List(1, 2, 3).foldRight(Nil: List[Int])(Cons(_, _)))
        println(list1.foldLeft(0)(_ + _))
        println(list1.length2)
        println(list1.incrementEach)

        val listD: List[Double] = List(1.0, 2.0, 3.0)
        println(listD.doublesToStrings)
        println(list1.filterViaFlatMap(_ < 5))

        def toInt(s: String): Opt[Int] =
            try
                Sm(s.trim.toInt)
            catch
                case e: Exception => Nn

        
        val strings = Seq("1", "2", "foo", "3", "bar")

        println(strings.map(toInt))
        println(strings.flatMap(toInt))
        println(strings.flatMap(toInt).sum)
        println(list1.take(5))
        println(list1.takeWhile(_ < 5))
        println(List(1,2,3,4).forall(_ < 5)) // true
        println(List(1,2,3,4).forall(_ < 4)) // false
        println(List[Int]().forall(_ > 1)) // true
        println(list1.exists(_ == 3)) // true
        println(list1.exists(_ == 30)) // false

        println(list1.hasSubsequence(List(3, 4, 5)))
        println(list1.startsWith(List(1, 2, 3)))
        println(Lst(1, 2, 3, 4).mean) // Some(2.0)
        println(Lst(1, 2, 3, 4).mean.map(math.abs)) // Some(2.0)
        println(Lst(1, 2, 3, 4).mean.map(x => x + 2.0)) // Some(4.0)
        println(List(2, 4, 6).headOption) // Some(2)
        println(List[String]().headOption) // None

        println(list1.lastOption) // Some(7)
        println(List[Int]().lastOption)

        println(removeDuplicates(Array(1,2, 3, 3, 4, 4, 4, 5)))
        println(removeDuplicates(Array(1, 1)))
        println(removeDuplicates(Array()))

        println(removeDuplicatesRL(Array(1,2, 3, 3, 4, 4, 4, 5)))
        println(removeDuplicatesRL(Array(1,1)))
        println(removeDuplicatesRL(Array()))

        println(removeDuplicatesRL2(Array(1,2, 3, 3, 4, 4, 4, 5)))
        println(removeDuplicatesRL2(Array(1,1)))
        println(removeDuplicatesRL2(Array()))

        // lift converts a function A => B into a function Option[A] => Option[B],
        // so we can "lift" functions so that they work with Option values
        val optAbs = Option.lift(math.abs)
        println(optAbs(Some(-42))) // Some(42)

        def insuranceRateQuote(age: Int, numberOfSpeedingTickets: Int): Double = 
            age * numberOfSpeedingTickets


        def parseInsuranceRateQuote(age: String, numSpdTkts: String): Option[Double] =
            val optAge: Option[Int] = Try(age.toInt)
            val optTickets: Option[Int] = Try(numSpdTkts.toInt)
            optAge.map2(optTickets)(insuranceRateQuote)

        println(parseInsuranceRateQuote("25", "2"))

        println(1.safeDiv(0)) // Left(java.lang.ArithmeticException: / by zero)

        println {
            { 
                val x = 100 / 42 
                val y = 0
                x / y
            }.attempt
        }

        def parseInsuranceRate(age: String, tickets: String): Either[Exception, Int] =
            for {
                a <- age.toInt.attempt
                t <- tickets.toInt.attempt
            } yield a * t

        println(parseInsuranceRate("30", "2")) // Right(60)
        println(parseInsuranceRate("3!", "2")) // Left(java.lang.NumberFormatException: For input string: "3!")

        def if2[A](cond: Boolean, onTrue: => A, onFalse: => A): A =
            if (cond) onTrue else onFalse

        println{
            if2(true, "A", "B")
        }

        def maybeTwice(b: Boolean, i: => Int): Int =
            if (b) i + i else 0

        // this method will evaluate i twice if b is true
        val x = maybeTwice(true, { println("hi"); 1 + 41 }); println(x)
        // hi
        // hi
        // 84

        // adding the lazy keyword to a val declaration will both delay
        // evaluation of the right-hand side of that lazy val until
        // it is first referenced as well as cache the result so future
        // references to it won't be evaluated more than once.
        
        def maybeTwice2(b: Boolean, i: => Int): Int =
            lazy val j = i
            if (b) j + j else 0

        val y = maybeTwice2(true, { println("hi"); 1 + 41 }); println(y)
        // hi
        // 84

        case class Person(name: String)

        val people = scala.List(
            Person("Jon"),
            Person("Sarah"),
            Person("Sam"),
        )

        println(people.tail.foldLeft(people.head: Person) { case (prevPer, currPer) =>
            if (prevPer.name < currPer.name) prevPer else currPer
        })
        // Person(Jon)

        println{
            people.maxBy(_.name.length)
        }
        // Person(Sarah)

        val newStream: Stream[Int] = Stream(1, 2, 3)
        val fls: Int = newStream.foldLeft(0)((acc, i) => acc + i)
        println(fls) // 6

        val forallfl: Boolean = newStream.forAll(_ < 5)
        println(forallfl) // true

        println(newStream.forAll(_ == 1)) // false

        









        

        













        




