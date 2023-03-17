

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

    /////////////////////
    def moveZeros(arrN: List[Int], isRight: Boolean): List[Int] = {
        isRight match {
            case true => arrN.sortWith(_ > _)
            case _ => arrN.sortWith(_ < _)
        }
    }

    val list3 = List(1, 3, 0, 5, 0 , 2, 2, 3, 0, 0, 8, 1)
    
    def last(s: String): Array[String] = {
        s.split(" ").sortBy(_.last)
    }
    
    def last2(s: String): Array[String] = {
        s.split(" ").sortBy(x => (x.last, x.length))
    }

    def last3(s: String): Array[String] = {
        s.split(" ").sortWith((s1, s2) => s1.last < s2.last)
    }

    println(last("what time are we climbing up the volcano"))


    val mapa = Map("Al" -> 85, "Emily" -> 91, "Hannah" -> 92, "Kim" -> 90, "Melissa" -> 95)

    val sortMapa = mapa.toSeq.sortWith(_._2 > _._2)
    println(sortMapa)

    val sortByMapa = mapa.toSeq.sortBy(_._1.last)
    println(sortByMapa)

    def multiplicationTable(size: Int): List[List[Int]] = {
        List.tabulate(size, size)((x, y) => (x + 1) * (y + 1))
    }
    
    println(multiplicationTable(3))

    println(List.tabulate(2, 2)((a, b) => a * b))

    // ////////////////////////////////////////////////////////
    val xs = Seq(4, 5, 2, -1, -3, 4)
    val ws = Seq("Thing", "is", "a", "sequence", "of", "strings")

    val even = xs.filter(_ % 2 == 0)
    val odd = xs.filterNot(_ % 2 == 0)

    val (even1, odd1) = xs.partition(_ % 2 == 0)
    println(even1)
    println(odd1)

    val minByAbs = xs.minBy(x => Math.abs(x))
    val minByAbs2 = xs.minBy(Math.abs)
    println(minByAbs)
    println(minByAbs2)

    val maxBy1 = ws.maxByOption(_.head)
    println(maxBy1)

    val minByOpt = xs.minByOption(Math.abs) match { case Some(x) => x; case None => None }
    println(minByOpt)

    val doubleIfPositive = xs.collect { case x if x > 0 => x * 2 }
    // The above is the same as...
    // xs.filter(_ > 0).map(_ * 2)
    println(doubleIfPositive) //List(8, 10, 4, 8)

    // collect can be used as a filterMap
    val usingCollect = ws.collect { 
        case a if (a.contains("ing")) => a + "s"
        case b if (b.length > 5) => b + "greater than 5"
    }

    println(usingCollect) // List(Things, sequencegreater than 5, stringss)

    val identity1 = identity(4)
    println(identity1) // 4

    val map5 = Map("a" -> 3.0, "b" -> 1.0, "c" -> 2.0)
    println(map5.minBy(_._2)) // ("b", 1.0)

    println(map5.minBy(_._2)._1) // b

    



}
