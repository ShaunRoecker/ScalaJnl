

object Kata extends App {
    // scanLeft: Is similar to foldLeft, however it returns the results of all the intermediate results
    // as a sequence, instead of a single value
    val list1 = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val scanList1 = list1.scanLeft(0)(_ + _)
    println(scanList1) // List(0, 1, 3, 6, 10, 15, 21, 28, 36, 45)

    val foldLeft1 = list1.foldLeft(0)(_ + _)
    println(foldLeft1) // 45

    // Notice scanLeft, with all other parameters equal, is equivalent to foldLeft.last

    val map1 = Map("a" -> 1)
    val map2 = Map("a" -> 1)
    val map3 = map1 ++ map2
    println(map3) // Map("a" -> 1)

    // To combine the maps with addition   v- map is the acc,  (k, v) is the components of the map1 map 
    val map4 = map1.foldLeft(map2){ case (map, (k, v)) =>
        map.get(k) match {
            case Some(newv) => map + (k -> (newv + v))
            case None => map + (k -> v)
        }   
    }
    println(map4) //Map(a -> 2)

    // Turn a sequence of things into an aggregated map
    val listT = List("john", "fred", "sally", "fred", "eric", "fred", "john")

    val mapagg = listT.groupMapReduce(identity)(_ => 1)(_ + _)
    println(mapagg) // Map(eric -> 1, sally -> 1, john -> 2, fred -> 3)

    // finding the largest or smallest key or value in a map:
    val largestValue = mapagg.maxBy(_._2)
    println(largestValue) //(fred,3)

    val smallestValue = mapagg.minBy(_._2)
    println(smallestValue) //(eric,1)

    // create a multiplication table of n size
    def multiTable(n: Int) = {
        List.tabulate(n, n)((a, b) => (a + 1) * (b + 1))
    }
    println(multiTable(4)) // List(List(1, 2, 3, 4), List(2, 4, 6, 8), List(3, 6, 9, 12), List(4, 8, 12, 16))

    
}