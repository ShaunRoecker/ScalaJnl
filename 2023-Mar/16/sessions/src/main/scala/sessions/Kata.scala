

object Kata extends scala.App {

    def countBool(bool: Array[Boolean]): Int = 
        bool.map(if (_) 1 else 0).sum
    

    println(countBool(Array(true, false, false, false, false, false, false, true)))

    def countBoolBetter(bool: Array[Boolean]): Int = 
        bool.count(_ == true)


    def countBoolClever(bool: Array[Boolean]): Int = 
        bool.count(identity)

    /////////////////////////////////////////////////
    def barTriang(pointA: (Int, Int), pointB: (Int, Int), pointC: (Int, Int)): List[Double] = {
        val x = (pointA._1 + pointB._1 + pointC._1) / 3
        val y = (pointA._2 + pointB._2 + pointC._2) / 3
        List(("%.3f".format(x)).toDouble, ("%.3f".format(y)).toDouble)
    }

    /////////////////////////////////////////////////////
    def count(string: String): Map[Char,Int] = {
        string.groupMapReduce(identity)(_ => 1)(_ + _)
    }

    println(count("asdgadadfa"))
    //HashMap(s -> 1, f -> 1, a -> 4, g -> 1, d -> 3)

    println(List("sam", "alex", "sam").groupMapReduce(identity)(_ => 1)(_ + _))
    //Map(alex -> 1, sam -> 2)

    println(List("sam", "alex", "sam").grouped(2).toList)
    // List(List(sam, alex), List(sam))

    val list1 = List(1, 2, 3)
    println(list1.combinations(2).toList)
    // note: .combinations(n) returns an Iterator
    //List(List(1, 2), List(1, 3), List(2, 3))

    val list2 = List(1, 2, 3, 2)
    println(list2.groupMapReduce(identity)(_ => 2)(_ + _))
    // Map(1 -> 2, 2 -> 4, 3 -> 2)

    println("*****")
    
    val map1 = Map("A" -> 1, "B" -> 2, "C" -> 3)
    val map2 = Map("A" -> 2, "B" -> 3)

    // multiply 2 maps
    val mapsMultiplied = map1.map{ case (k, v) => (k, v * map2.getOrElse(k, 1))}
    println(mapsMultiplied) //Map(A -> 2, B -> 6, C -> 3)

    ////////////////////
    // Century Year
    def centuryFromYear(year: Int): Int = {
        (year % 100 == 0) match {
            case true => year / 100
            case false => (year / 100) + 1
        }
    }
    println(centuryFromYear(1850))
    
    ////////////////////
    def zeroFuel(distance: Int, mpg: Int, fuel: Int): Boolean = {
        if (distance > mpg * fuel) false else true
    }

    println(zeroFuel(100, 20, 4))

}
