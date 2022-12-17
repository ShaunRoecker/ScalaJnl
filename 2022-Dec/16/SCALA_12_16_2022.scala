object SCALA_12_16_2022 extends App {
    println("Hello World!")

    import scala.collection.mutable.StringBuilder
    // def strbldr(s: String): String =
    //     val sb = StringBuilder("")

    // DAILY CTCI 1.1 && 1.2
    /////////////////////////////////////////////////////////
    // 1.1 (pg.90)
    // checking if an input string has all unique characters
    def strCharIsUnique(str: String): Boolean =
        if (str.distinct == str) then true else false
    
    println(strCharIsUnique("abcdefghijklmnopqrstuvwxyzABCDEF"))

    // another idea:
    def strCharIsUnique2(str: String): Boolean =
        if (str.toSet.size == str.size) then true else false
    
    println(strCharIsUnique2("abcdefghijklmnopqrstuvwxyzABCDEF"))


    // 1.2 (pg.90)
    // check permutation: Given two strings write a method to decide
    // if one is a permutation of the other

    def isPermuation(s1: String, s2: String): Boolean = 
        if s1.sorted == s2.sorted then true else false

    println(isPermuation("ACBAB", "BCAAB")) //true
    println(isPermuation("ACBAB", "CAAB")) //false
    /////////////////////////////////////////////////////////

    case class Person(name: String = "Unknown")
    val personList = Vector(Person("A"), Person("B"), Person("C"))
    println(personList) //Vector(Person(A), Person(B), Person(C))

    def aFunction(personList: Vector[Person]): Vector[Person] = 
        def isA(s: String): String =
            s match
                case "A" => "Alpha"
                case "B" => "Bravo"
                case "C" => "charlie"
                case _ => s
        
        personList.map(x => x.copy(name = isA(x.name) + "X"))
        
    
    val newPersonList = aFunction(personList)
    println(newPersonList) //Vector(Person(AlphaX), Person(BravoX), Person(charlieX))

    // FP is Algebra...

    // f(x) = x + 1
    // f(x, y) = x + y
    // f(a, b, c, x) = a * x^2 + b*x + c

    def f1(x: Int) = x + 1
    def f2(x: Int, y: Int) = x + y
    def f3(a: Int, b: Int, c: Int, x: Int) = a*x*x + b*x + c

    // val emailDoc = getEmailFromServer(src)     // val b = f(a)
    // val emailAddr = getAddr(emailDoc)          // val c = g(b)
    // val domainName = getDomainName(emailAddr)  // val d = h(c)

    // val domainName = getDomainName(emailAddr(getEmailFromServer(src)))

    val x = 2 + 2
    val doubles = List(1,2,3,4,5).map(_*2)
    val a = 2
    val b = 3
    val greater = if (a > b) then a else b
    println(greater)

    val i = 3
    val evenOrOdd = i match
        case 1|3|5|7|9 => println("odd")
        case 2|4|6|8|10 => println("even")
     
    def toInt(s: String): Int =
        try
            s.toInt
        catch
            case _: Throwable => 0
    
    println(toInt("2"))
    println(toInt("two"))

    def cut(strings: Seq[String],
            delimiter: String,
            field: Int): Seq[String] = ???
        
    // implicit return type syntax
    val isEven = (i: Int) => i % 2 == 0
    val isEven2 = (i: Int) => { i % 2 == 0 }
    val isEven3 = (i: Int) => if (i % 2 == 0) then true else false
    val isEven4 = (i: Int) => if (i % 2 == 0) true else false
    val isEven5 = (i: Int) => { if (i % 2 == 0) true else false }
    val isEven6 = (i: Int) => {  //scala2 (I love Scala3)
        if (i % 2 == 0) {
            true 
        } else {
            false
        }  
    }

    // the compiler converts this:
    val sumA = (a: Int, b: Int) => a + b
    // into this:
    val sumB = new Function2[Int, Int, Int] {
        def apply(a: Int, b: Int): Int = a + b
    }


    val isEvenList = List(1,2,3,4,5,6,7,8,9).filter(isEven)
    println(isEvenList) //List(2, 4, 6, 8)

    val x1 = isEvenList.map(_ * 2)
    println(x1) //List(4, 8, 12, 16)

    def double(i: Int): Int = i * 2
    val x2 = isEvenList.map(double)
    println(x2)  //List(4, 8, 12, 16)

    println('B'.getClass()) //char
    println((1).compare(2)) // -1
    println((1).compare(12)) // -1

    import scala.util.Properties.*

    println(isLinux) //false
    println(isMac) //true
    println(isWin) //false

    println(javaHome) ///usr/local/Cellar/openjdk/19.0.1/libexec/openjdk.jdk/Contents/Home

    val sample = 1 to 10
    val isEven9: PartialFunction[Int, String] = 
        case x if x % 2 == 0 => x+" is even"

    val x4 = isEven9.applyOrElse(1, "Not Found")
    println(x4)




    


















}
