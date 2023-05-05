package functional

import functional.datastructures.containers.{List, Nil, Cons}
import functional.datastructures.errorhandling.{Option, None, Some}
import functional.datastructures.errorhandling.Various._
import functional.datastructures.errorhandling.Option._
import functional.problems.Solution1._


import scala.collection.immutable.{List => Lst}
import scala.{Option => Opt, Some => Sm, None => Nn}


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

        













        




