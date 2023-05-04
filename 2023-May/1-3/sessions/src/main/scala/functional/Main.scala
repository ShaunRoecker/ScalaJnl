package functional

import functional.datastructures.containers.{List, Nil, Cons}
import functional.datastructures.errorhandling.{Option, None, Some}
import functional.datastructures.errorhandling.Various._


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







        




