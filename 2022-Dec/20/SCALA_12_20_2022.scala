object SCALA_12_20_2022 extends App {
    println("12/20/2022")
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 1.9 && 2.1
    /////////////////////////////////////////////////////////
    // 1.9 (pg.207)  
    def isSubstring(s1: String, s2: String): Boolean =
        if s1.length > 0 && s2.length > 0 then
            val s1sorted = s1.toList.map(_.toLower).sorted
            val s2sorted = s2.toList.map(_.toLower).sorted
            s1sorted == s2sorted
        else
            false


    println(isSubstring("Helloborld", "elollbhord")) //true
    println(isSubstring("", "")) //true

    // option2
    def isRotation(s1: String, s2: String): Boolean = 
        (s1.length == s2.length) && (s1 + s1).contains(s2)

    
    // 2.1 (pg.208)
    /////////////////////////////////////////////////////////
    def remove(input: List[Int]): List[Int] =
        input.foldLeft((List[Int](), Set[Int]()))(
            (acc, x) => {
                if (!acc._2.contains(x))
                    (x :: acc._1, acc._2 + x)
                else
                    acc 
            }
        )._1.reverse
    
    val x3 = remove(List(1,2,3,4,4,3,2,3,4,4,5,6,7))
    println(x3)

    println(List(1,2,3,4).reduce(_ + _))
    println(List(1,2,3,4).reduce((x,y) => x + y))
    println(List(1,2,3,4).foldLeft(100)((x,y) => x + y))
    println(List(1,2,3,4).foldLeft(100)(_ + _))

    def makeInt(s: String): Either[String, Int] =
        try
            Right(s.trim.toInt)
        catch
            case e: Exception => Left(e.toString)

    println(makeInt("1"))
    println(makeInt("foo"))

    makeInt("11") match
        case Left(s) => println("Error Message: " + s)
        case Right(i) => println("Desired answer: " + i)

    // works with for expressions
    val answer = 
        for
            a <- makeInt("1")
            b <- makeInt("10")
        yield a + b

    println(answer)


    def timer[A](blockOfCode: => A) =
        val startTime = System.nanoTime
        val result = blockOfCode
        val stopTime = System.nanoTime
        val delta = stopTime - startTime
        (result, delta/100000d)
    

    val (result, time) = timer {
        Thread.sleep(1000)
        42
    }
    println(result)
    println(time) //10041.32201

    case class FOO(f: Int => Int)

    val f = FOO {
        x => x + 3
    }
    println(f)
    
    case class StringToInt(run: String => Int)

    val stringToInt = StringToInt { 
        (s: String) => s.length
    }

    println(stringToInt.run("bananas"))

    // with a named function instead of anonymous function
    def len(s: String) = s.length
    val stringToInt2 = StringToInt(len)
    val stringToInt3 = StringToInt(
        x => x.length
    )

    case class Transform2ParamsTo1Param[A, B](fun: (A, A) => B)

    val x2 = Transform2ParamsTo1Param { (a: String, b: String) =>
        a.length + b.length
    }

    println(x2.fun("foo", "bar"))

    val y2 = Transform2ParamsTo1Param { (a: Int, b: Int) =>
        a + b
    }

    println(y2.fun(1, 2))

    def s2i(s: String)(f: String => Int) = f(s)

    println(s2i("Stir")(x => x.length))

    val res = s2i("hello") {
        x => x.length
    }

    println(res)

    // def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    //     try
    //         f(resource)
    //     finally
    //         resource.close()
    
    
    // using(io.Source.fromFile("example.txt")) { source =>
    //     for (line <- source.getLines) {
    //         println(line)
    //     }
    // }

    val newFunc = (s: String) => {
        val x = s.trim
        val p = s.length
        p
    }

    val x = List(1,2,3,4)
    val y = List(1,2,3)

    val j = x.map(a => y.map(b => a + b))
    println(j)

    val k = for
                a <- x
                b <- y
            yield a + b
    println(k)

    // //////////////////////////////////////////////////////////////////
    // adding each element of a list to the corresponding element of another list
    val p = x.zipAll(y, 0, 0).map { case (a, b) => a + b }
    println(p) //List(2, 4, 6)

    val u = x.zipAll(y, 0, 0)
    println(u) //List((1,1), (2,2), (3,3))

    val u2 = x.zipAll(y, "x default if shorter than y", 0)
    println(u2) //List((1,1), (2,2), (3,3), (x default if shorter than y,4))

    val u3 = x.zipAll(y, 0, "y default if shorter than x")
    println(u3) //List((1,1), (2,2), (3,3), (4,y default if shorter than x))

    

















  

}
