object SCALA_12_17_2022 extends App {
    println("12/19/2022")
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 1.7 && 1.8
    /////////////////////////////////////////////////////////
    // 1.7 (pg.203)  replace all spaces in a string with '%20'
    import scala.annotation.tailrec

    object Matrix {

        def rotate(matrix: Array[Array[Int]]): Unit = {
            @tailrec
            def rotateRec(shift: Int): Unit =
                val size = matrix.length - (2 * shift)
                if (size > 1)
                    val start = shift
                    val end = shift + size - 1
                    for (i <- 0 until size - 1)
                        val tmp1 = matrix(start)(start + i)
                        val tmp2 = matrix(start + i)(end)
                        val tmp3 = matrix(end)(end - i)
                        val tmp4 = matrix(end  - i)(start)

                        matrix(start + i)(end) = tmp1
                        matrix(end)(end - i) = tmp2
                        matrix(end  - i)(start) = tmp3
                        matrix(start)(start + i) = tmp4
                    rotateRec(shift + 1)
            rotateRec(0)
        }
    }
    val matrix = Array.ofDim[Int](4, 4)
    println(matrix.length)
    // 1.8 (pg.)
    /////////////////////////////////////////////////////////
    def zerofy(matrix: Array[Array[Int]]): Unit = {
        var rows = scala.collection.mutable.Set[Int]()
        var cols = scala.collection.mutable.Set[Int]()
        for(i <- 1 until matrix.length; j <- 1 until matrix.length)
            if(matrix(i)(j) == 0) {
                rows += i
                cols += j
            }
        rows.foreach(matrix(_) = new Array[Int](matrix.length))
        cols.foreach(col => matrix.foreach(_(col) = 0))
    }

    import scala.collection.mutable.ArrayBuffer

    // case class Sequence[A](initialElems: A*):
    //     private val elems = scala.collection.mutable.ArrayBuffer[A]()
    //     elems ++= initialElems

    //     def foreach(block: A => Unit): Unit =
    //         elems.foreach(block)

    //     def map[B](f: A => B): Sequence[B] =
    //         val abMap = elems.map(f) 
    //         new Sequence(abMap: _*)
        
    
    // val ints = Sequence(1,2,3)
    // println(ints)

    // for (i <- ints) println(i)

    case class Person(name: String)

    val myFriends = Seq(
        Person("Adam"),
        Person("Bob"),
        Person("Sam")
    )
    val adamsFriends = Seq(
        Person("Sam"),
        Person("David"),
        Person("Nick")
    )

    // which friends of mine are friends of adam
    val mutualFriends = 
        for
            myFriend <- myFriends //generator
            adamsFriend <- adamsFriends //generator
            if (myFriend.name == adamsFriend.name)
        yield myFriend
    
    mutualFriends.foreach(println)

    trait CustomClass[A]:
        def map[B](f: A => B): CustomClass[B] 
        def flatMap[B](f: A => CustomClass[B]): CustomClass[B]
        def withFilter(p: A => Boolean): CustomClass[A]
        def foreach(b: A=> Unit): Unit

    trait Example:
        def addToString(s: String): String = s + "XXXXXX"
    
    case class CaseClass(name: String) extends Example

    val e = CaseClass("YYYYYYYY")
    println(e.addToString("ZZZZZZZ"))

    def makeInt(s: String): Option[Int] = 
        try
            Some(Integer.parseInt(s))
        catch
            case e: Exception => None
    
    makeInt("1") match
        case Some(i) => println(s"i = ${i}")
        case None => println("Could not parse int")

    val result2 = makeInt("2").getOrElse(0)
    println(result2)

    val result3 = for
        x <- makeInt("1")
        y <- makeInt("2")
        z <- makeInt("3")
    yield x + y + z

    println(result3)
    
    val result4 = for
        x <- makeInt("1")
        y <- makeInt("2")
        z <- makeInt("3")
    yield x + y + z

    result4 match
        case Some(i) => println(s"i = ${i}")
        case None => println("Could not parse int")

    def extractSomeOrZero(x: Option[Int]): Int =
        x match
            case Some(i) => i
            case None => 0

    println(extractSomeOrZero(result4))

    import scala.util.{Try, Success, Failure}
    def makeIntTry(s: String): Try[Int] = Try(s.trim.toInt)

    println(makeIntTry("1"))
    println(makeIntTry("foo"))

    makeIntTry("foo") match
        case Success(i) => println(s"Success, value is: ${i}")
        case Failure(e) =>println(s"Failed, message: ${e.getMessage}")

    val answer = 
        for
            a <- makeIntTry("1")
            b <- makeIntTry("10")
        yield a + b

    println(answer)
    
    val t1 = makeIntTry("foo") match
        case Success(i) => println(s"Success, value is: ${i}")
        case Failure(e) => 0

    println(t1)




        

    

  

    
    



}