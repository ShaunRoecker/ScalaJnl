object SCALA_12_14_2022 extends App {

    // 
    val name = Seq("Nidhi", "Singh")
    val toLowerName = name.map(_.toLowerCase).flatten
    println(toLowerName) //List(n, i, d, h, i, s, i, n, g, h)

    val flatMapName = name.flatMap(_.toLowerCase)
    println(flatMapName)

    //// Applying flatMap with another functions.
    val list = List(2, 3, 4)
    def f(x: Int) = List(x - 1, x, x + 1)

    val result = list.flatMap(x => f(x))
    println(result) //List(1, 2, 3, 2, 3, 4, 3, 4, 5)
    
    val resultMapOnly = list.map(x => f(x))
    println(resultMapOnly) //List(List(1, 2, 3), List(2, 3, 4), List(3, 4, 5))

    val numbers = List(1,2,3,4)
    val chars = List("a", "b", "c", "d")
    val colors = List("black", "white")
    // we want -> List("a1", "b2", "c3", "d4")

    val combinations = numbers.flatMap(n => chars.map(c => c + n))
    println(combinations) //List(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4)

    val numsFirst = chars.flatMap(x => numbers.map(y => y + x))
    println(numsFirst) //List(1a, 2a, 3a, 4a, 1b, 2b, 3b, 4b, 1c, 2c, 3c, 4c, 1d, 2d, 3d, 4d)

    val loop3 = numbers.flatMap(x => chars.flatMap(y => colors.map(z => x + y + "-" + z)))
    println(loop3) 
    //List(1a-black, 1a-white, 1b-black, 1b-white, 1c-black, 1c-white, 
    // 1d-black, 1d-white, 2a-black, 2a-white, 2b-black, 2b-white, 2c-black, 
    // 2c-white, 2d-black, 2d-white, 3a-black, 3a-white, 3b-black, 3b-white, 
    // 3c-black, 3c-white, 3d-black, 3d-white, 4a-black, 4a-white, 4b-black, 
    // 4b-white, 4c-black, 4c-white, 4d-black, 4d-white)

    // Can use flatmap as a way to loop through multiple iterators in a functional
    // way

    // for-comprehensions
    val forCombinations = for {
        x <- numbers if x % 2 == 0
        y <- chars if y != "b" || y != "d"
        z <- colors if z.startsWith("w")
    } yield x + y + "-" + z

    println(forCombinations) 
    //List(2a-white, 2b-white, 2c-white, 2d-white, 4a-white, 4b-white, 4c-white, 4d-white)
    
    // function currying
    def plainSum(x: Int, y: Int): Int = x + y

    def curriedSum(x: Int)(y: Int): Int = x + y

    def first(x: Int) = (y: Int) => x + y

    val add10 = first(10)
    println(add10(5))

    def function1(x: Int, y: Int) = (z: Int) => x + y + z

    val newFunction1 = function1(5, 10)

    println(newFunction1(2))

    def runTwice(op: Double => Double, x: Double) = op(op(x))
    val zel = runTwice(x => x * 2, 10)
    println(zel) //40.0

    val del = runTwice(_ + 2, 3)
    println(del)

    def runXTimes(f: Int => Int, n: Int, opr: Int): Int =
        if (n <= 0) opr
        else runXTimes( f, n-1, f(opr))
    
    println(runXTimes(_ + 1, 10, 1))

    val run10Times = runXTimes(_, 10, _)
    println(run10Times(_ + 10, 0))

    def runXtimes2(func: Int => Int, n: Int, opr: Int): Int =
        if (n <= 0) opr
        else runXtimes2(func, n - 1, func(opr))
    
    println(runXtimes2(_ + 1, 1, 1))


    def runXTimes3(func: Int => Int, n: Int, opr: Int): Int =
        if (n <= 0) opr
        else runXTimes3(func, n - 1, func(opr))
    
    val run3Times = runXTimes3(_, 3, _)
    println(run3Times(x => x*x, 2))

    // Options
    val optionOne: Option[Int] = Some(4)
    val optionNone: Option[Int] = None

    println(optionOne)

    def unsafeMethod(): String = null

    val result4 = Option(unsafeMethod())

    def backupMethod(): String = "A valid result"

    val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
    println(chainedResult)
    val extractedResult = chainedResult match
        case Some(x) => x
        case None => None
    
    val otherWay = chainedResult.fold{}{x => x}
    println(otherWay)

    def betterUnsafeMethod(): Option[String] = None
    def betterBackupMethod(): Option[String] = Some("A valid result")

    val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()



    // Network configuration
    val config: Map[String, String] = Map(
        // fetched from elsewhere
        "host" -> "176.45.36.1",
        "port" -> "80"
    )

    class Connection {
        def connect = "Connected" //connect to some server

    }
    object Connection {
        import scala.util.Random
        val random = new Random(System.nanoTime())
        def apply(host: String, port: String): Option[Connection] =
            if (random.nextBoolean()) Some(new Connection)
            else None
    }
    
    val host = config.get("host")
    val port = config.get("port")
    // val connection = host.flatMap(h => port.flatmap(p => Connection.apply(h, p)))
    // val connectionStatus = connection.map(c => c.connect)
    // connectionStatus.forEach(println)


    println(host)
    println(port)

    val forConnectionStatus = for {
        host <- config.get("host")
        port <- config.get("port")
        connection <- Connection(host, port)
    } yield connection.connect
    println(forConnectionStatus)


    import scala.util.{Try, Success, Failure}

    val aSuccess = Success(3)
    val aFailure = Failure(new RuntimeException("NO STRING FOR YOU!"))

    println(aSuccess)
    println(aFailure)

    def unsafeMethod2(): String = throw new RuntimeException("Method Failure")

    val potentialFailure = Try(unsafeMethod2())
    println(potentialFailure)




    

    
}
