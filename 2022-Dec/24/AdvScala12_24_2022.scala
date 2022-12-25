object AdvScala12_24_2022 extends App:
    println("Advanced Scala")

    val pFunc = new PartialFunction[Int, Int] {
        def apply(i: Int) = 42 / i
        def isDefinedAt(x: Int): Boolean =  x != 0 
    }

    // val lista = List(0, 2, 3, 4)
    // val mapa = lista.map(pFunc)
    // println(mapa)

    println(pFunc)

    // syntax sugar: methods with single parameter
    def singleArgMethod(arg: Int): String = s"$arg little ducks..."

    val description = singleArgMethod {
        42
    }

    import scala.util.Try
    val aTryInstance = Try { //Java Try {...}
        0
    }

    // this is used with map, flatMap, and filter
    List(1, 2, 3).map { x =>
        val y = 10
        (x * 15) + y
    }

    // syntax sugar #2: single abstract method
    trait Action:
        def act(x: Int): Int 

    val anInstance: Action = new Action {
        override def act(x: Int): Int = x + 1
    }

    val aFunkyAction: Action = (x: Int) => x + 1 //lambda to single abstract type conversion
    // example: Runnables

    // Java way to instantiate runnables
    val aThread = new Thread(new Runnable {
        override def run(): Unit = println("hello, Scala")
    })

    // Scala way to instantiate runnables (laambda to single abstract type conversion)
    val aBetterTread = new Thread(() => println("hello, Scala"))

    abstract class AnAbstractType:
        def implemented: Int = 23
        def f(a: Int): Unit

    val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet")

    // syntax sugar #3: the :: and #:: methods are special
    val prependedList = 2 :: List(3, 4) //infix method
    val prependedList2 = List(3, 4).::(2)

    class MyStream[T] {
        def -->:(value: T): MyStream[T] = this
    }

    val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

    class TeenGirl(name: String) {
        def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
    }

    val lilly = new TeenGirl("Lilly")
    lilly `and then said` "Scala is so sweet"
    // Out:
    // Lilly said Scala is so sweet

    def `print that shit`(a: String): Unit = println(a)

    `print that shit`("Steve")

    class Composite[A, B]

    val composite: Int Composite String = new Composite[Int, String]  //can infix generic types

    class -->[A, B]
    val towards: Int --> String = new -->[Int, String]

    // syntax sugar 6: update() is very special, much like apply()
    val anArray = Array(1,2,3)
    anArray(2) = 7 //rewritten to anArray.update(2, 7)

    class Person(name: String):
        override def toString(): String = s"Person(${this.name})"

    object Person:
        def apply(name: String): Person = new Person(name)

    val person = Person("Roger")
    println(person)

    // syntax sugar #7: setters for mutable containers
    class Mutable {
        private var internalMember: Int = 0 // private for OO encapsulation
        def member: Int = internalMember // "getter"
        def member_=(value: Int): Unit =
            internalMember = value // "setter"
    }

    val aMutableContainer = new Mutable
    aMutableContainer.member = 42 // rewritten as 

    // from before, single arg methods/functions can use curly braces
    // map, flatMap, filter, etc. do this
    def doSomething(i: Int): Int = i + 10
    val didSomething = doSomething{ 10 }
    println(didSomething)

    // just practice with foldLeft
    val listq = List(1, 2, 4)
    val sum = listq.foldLeft(0)(_ + _)
    println(sum) //7
    val listw = List("A", "B", "C", "D", "E", "F")
    val concatenate = listw.foldLeft("")(_ + " " + _).trim
    println(concatenate) //A B C D E F

    // back to syntax sugar
    // Methods with ":" are special
    //  >> right associative if ending with ":"

    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)
    val listLeftAssoc = list1 +: list2 //left associative
    println(listLeftAssoc) //List(List(1, 2, 3), 4, 5, 6)
    // the list on the right is prepended to the list on the LEFT
    val listRightAssoc = list1 :+ list2 //right associative
    println(listRightAssoc) // List(1, 2, 3, List(4, 5, 6))
    // the list on the left is appended to the list on the RIGHT

    import patternmatching.PatternMatch
    PatternMatch.something




    











    








