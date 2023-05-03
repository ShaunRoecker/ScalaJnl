package functional

import functional.datastructures.{List, Nil, Cons}
import scala.collection.immutable.{List => Lst}


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

        def toInt(s: String): Option[Int] =
            try
                Some(s.trim.toInt)
            catch
                case e: Exception => None

        
        val strings = Seq("1", "2", "foo", "3", "bar")

        println(strings.map(toInt))
        println(strings.flatMap(toInt))
        println(strings.flatMap(toInt).sum)
        




