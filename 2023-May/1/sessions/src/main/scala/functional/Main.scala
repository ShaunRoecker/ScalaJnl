package functional

import functional.datastructures.List


object Program:
    @main
    def main =
        println("Functional Data Structures")

        val list1 = List(1,2,3)
        println(list1.sum) // 6
        println(list1.product) // 6
        println(List(1,2,3) ++ List(4,5,6))
        println(List(1,2,3).append(List(4,5,6)))


