object SCALA_12_17_2022 extends App {
    println("12/17/2022")
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 1.3 && 1.4
    /////////////////////////////////////////////////////////
    // 1.3 (pg.)  replace all spaces in a string with '%20'
    def replaceRGX(s: String): String = ???
        // wait to finish the regex book, that's going to easily solve this problem


    // 1.4 (pg.)
    /////////////////////////////////////////////////////////
    import scala.annotation.tailrec
    def isPalindromePermutation(str: String): Boolean =
        @tailrec
        def parities(s: String, current: Map[Char, Boolean] = Map()): Map[Char, Boolean] =
            if (s.isEmpty) then current
            else parities(s.tail, current + (s.head -> !current.getOrElse(s.head, false)))
        
        @tailrec
        def countParities(m: Map[Char, Boolean], current: Map[Boolean, Int] = Map()): Map[Boolean, Int] =
            if (m.isEmpty) then current
            else countParities(m.tail, current + (m.head._2 -> (1 + current.getOrElse(m.head._2, 0))))

        countParities(parities(str)).getOrElse(true, 1) == 1
    
    println(isPalindromePermutation("racecar"))


    def isPalindromePermutation2(str: String): Boolean = 
        val strLength = str.size
        val strDistinctSize = str.toLowerCase.distinct.size
        val isEven = strLength % 2 == 0

        if (isEven) then 
            if ((strLength / 2) == strDistinctSize) then true
            else false
        else 
            if ((strLength - strDistinctSize + 1) == strDistinctSize) then true
            else false

    println(isPalindromePermutation2("racecar"))


    def isPalindromePermutation3(str: String): Boolean = 
        val strLength = str.size
        val strDistinctSize = str.toLowerCase.replace(" ", "").distinct.size
        val isEven = strLength % 2 == 0

        isEven match
            case true => if ((strLength / 2) == strDistinctSize) 
                            then true else false
            case false => if ((strLength - strDistinctSize + 1) == strDistinctSize)
                            then true else false
        

    println(isPalindromePermutation3("racecario"))
    println(isPalindromePermutation3("atco cta"))

    /////////////////////////////////////////////////////////

    val double = (i: Int) => i * 2
    val triple = (i: Int) => i * 3

    val functions: Map[String, Int => Int] = Map(
        "2x" -> double,
        "3x" -> triple,
    )

    val dub = functions("2x")
    val trip = functions("3x")
    println(dub(2)) //4
    println(trip(2)) //6

    // Map[String, Int => Int]
    //  Int => Int means that its an instance of the Function1 trait

    // Map[String, (Int, Int) => Int]  // signature of a Function2 trait in Map

    // Using Methods as if they were functions:
    def doubleMethod(i: Int): Int = i * 2
    val x1 = List(1,2,3).filter(_ % 2 == 0).map(doubleMethod).headOption.fold{}{x => x}
    println(x1) //4

    // difference between val functions and methods
    // in an application, a method need to be inside a class, object or trait

    // How to treat a method like a function, that can be passed around
    def methodOne(i: Int): Int = i * 2

    val methodAsFunc = methodOne _

    val functionMap = Map(
        "2x" -> methodAsFunc
    )

    println(functionMap("2x")(10)) //20

    def isTrue(i: Int): Boolean = if (i % 2 == 0) true else false
    
    def functionPass(f: (Int) => Boolean, i: Int): Int = 
        if f(i) then i * 100 else  i / 100

    println(functionPass(isTrue, 2))

    // if we didn't have the 'filter' method, we would have to create custom functions like...
    def getEvens(list: List[Int]): List[Int] =
        val tempArray = scala.collection.mutable.ArrayBuffer[Int]()
        for (elem <- list) 
            if(elem % 2 == 0) 
                tempArray += elem
        tempArray.toList

    println(getEvens(List(1,2,3,4,5,6,7,8))) //List(2, 4, 6, 8)

    println(List(1,2,3,4,5,6,7,8).filter(_ % 2 == 0)) //List(2, 4, 6, 8))

    def sayHello(callback: () => Unit): Unit =
        callback()
    
    sayHello(() => println("Hello"))
    // redundant, but it works

    def func4(f: (Int, Int) => Int, i: Int): Int = ???

    def toInt(s: String): Option[Int] = ???
        
    def runAFunction(f: Int => Int): Unit = f(42)

    def printIntPlusOne(i: Int): Unit = println(i + 1)


    def executeNTimes(f: () => Unit, n: Int) =
        for (i <- 1 to n) f()

    def holla(): Unit = println("HOLLA")
    executeNTimes(holla, 10)

    def executeAndPrint(f: (Int, Int) => Int, x: Int, y: Int): Unit =
        val result = f(x, y)
        println(result)

    def sum(x: Int, y: Int): Int = x + y
    def multiply(x: Int, y: Int): Int = x * y
    executeAndPrint(sum, 5, 6)
    executeAndPrint(multiply, 5, 6)

    def execTwoFs(f1: (Int, Int) => Int,
                  f2: (Int, Int) => Int,
                  a: Int,
                  b: Int): Tuple2[Int, Int] = 
        val result1 = f1(a, b)
        val result2 = f2(a, b)
        (result1, result2)
    
    println(execTwoFs(sum, multiply, 2, 5)) //(7,10)

    // making a map method
    def mapped[A](f: Int => A, list: List[Int]): List[A] = 
        for x <- list yield f(x)

    val nums = List(1,2,3,4,5)
    val mappedDouble = mapped(double, nums)
    println(mappedDouble) //List(2, 4, 6, 8, 10)

    def mapped2[A,B](f: A => B, seq: Seq[A]): Seq[B] = 
        for x <- seq yield f(x)
    
    val x19 = mapped2(double, Vector(1,2,3,4,5,6,7,8,9))
    println(x19) //Vector(2, 4, 6, 8, 10, 12, 14, 16, 18)

    def inToStr(i: Int): String = i.toString + "SCALA"

    val x20 = mapped2(inToStr, Vector(1,2,3,4,5))
    println(x20) //Vector(1SCALA, 2SCALA, 3SCALA, 4SCALA, 5SCALA)

    def filtered[A](f: A => Boolean, seq: Seq[A]): Seq[A] =
        for x <- seq 
            if f(x) 
        yield x
    
    def isOdd(i: Int): Boolean = i % 2 != 0
    val numberSeq = Vector(1,2,3,4,5)

    val filteredSeq = filtered(isOdd, numberSeq)
    println(filteredSeq) //Vector(1, 3, 5)

    case class Person(var name: String)

    // by-name parameters (called when used parameters)
    // def timer(blockOfCode: => theReturnType)

    def timer[A](blockOfCode: => A) =
        val startTime = System.nanoTime
        val result = blockOfCode
        val stopTime = System.nanoTime
        def delta(start: Long, stop: Long): Double =
            (stop - start) / 1_000_000d

        (result, delta(startTime, stopTime))

    
    
    println(timer((1 to 10).toList))

    def readFile(filename: String) = io.Source.fromFile(filename).getLines
    val file = readFile("./test.txt")
    println(timer(readFile("./test.txt")))

    // var asserttionEnambled = true
    
    // def myAssert(p: () => Boolean) =
    //     if (asserttionEnambled && !p())
    //         throw new AssertionError
    
    // myAssert(() => 5 > 3)

    // def byNameAssert(p: => Boolean) =
    //     if (asserttionEnambled && p())
    //         throw new AssertionError

    // byNameAssert(5 > 3)

    // function can have multiple parameter groups
    def foo(a: Int, b: String)(c: Double) = ???

    // This allows you to write your own control stuctures
    def singleAdd(a: Int, b: Int, c: Int): Int = a + b + c
    def multiAdd(a: Int)(b: Int)(c: Int): Int = a + b + c
    println(multiAdd(1)(3)(2)) // 6

    def p(x: Any): Unit = println(x)

    def whilst(testCondition: => Boolean)(codeBlock: => Unit) = 
        while (testCondition) codeBlock

    def ifBothTrue(testCond1: => Boolean)
                    (testCond2: => Boolean)
                      (codeBlock: => Unit): Unit =
            if (testCond1 && testCond2) codeBlock 

    ifBothTrue(5 > 2)(1 == 1)(println("Both are true"))

    def printIntIfTrue(a: Int)(implicit b: Boolean) = if (b) println(a)
    //  implicit keyword - if there is an implicit boolean value difined
    // in scope when printIntIfTrue is called, it will use that boolean value
    // if one is not provided as a parameter.

    // printIntIfTrue(1) <-doesnt work

    // val boo = true
    // printIntIfTrue(1) <-still doesnt work

    // however...
    implicit val boo: Boolean = true  // type needs to be explicit for these
    printIntIfTrue(1) // 1

    def f2(a: Int = 1)(b: Int = 2) = a + b
    f2()()

    // currying and partially applied functions (not PartialFunction)
    def plus(a: Int, b: Int) = a + b
    val plus2 = plus(_, 2)
    println(plus2(2)) //4

    def plusX(a: Int)(b: Int) = a + b
    val plus2X = plusX(2)(_)
    println(plus2X(2)) //4

    // also
    val plus2Xb = plusX(2)_
    println(plus2Xb(3)) //5

    def wrap(prefix: String)(html: String)(suffix: String) =
        prefix + html + suffix
    
    val hello = "Hello, World!"
    val result2 = wrap("<div>")(hello)("</div>")
    println(result2)

    val wrapWithDiv = wrap("<div>")(_: String)("</div>")
    p(wrapWithDiv("div goes here"))

    def add(x: Int, y: Int): Int = x + y
    val addFunction = add _

    println((add _).isInstanceOf[Function2[_,_,_]]) //true

    val addCurried = (add _).curried

    // now you can use the new curried function like this

    p(addCurried(1)(2)) //3
    val partialAddCurried = addCurried(100)
    p(partialAddCurried(100)) //200

    val list1 = List(1,2,3)
    val list2 = 1 :: 2 :: 3 :: Nil
    p(list1 == list2) //true

    // RECURSION: how to write a sum function
    val list = List(1,2,3,4)
    def sum(list: List[Int]): Int = list match
        case Nil => 0
        case x :: xs => x + sum(xs)

    println(sum(list))

    












}
