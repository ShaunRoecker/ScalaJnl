package functional

import functional.datastructures.List


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



