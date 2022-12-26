

import java.util.regex.Pattern
import scala.util.matching.Regex

object SCALA_12_25_2022 extends App:
    REGEX
    def rgxOne(s: String) =
        val r1 = """(\.\d\d[1-9]?)\d*""".r


    // reading a text file to a list
    import scala.io.Source

    val file = Source.fromFile("myFile.txt").getLines.toList
    println(file.head)

    for (line <- file) do println(line)

    val date = raw"(\d{4})-(\d{2})-(\d{2})".r

    "2004-01-20" match 
        case date(year, _*) => println(s"$year was a good year for PLs.")

    "2004-01-20" match 
        case date(_*) => println("It's a date!")

    // In a pattern match, regex usually matches the entire input
    // However, an unanchored regex finds a pattern anywhere in the input
    val imbeddedDate = date.unanchored
    val longString = """Date: 2004-01-20 2005-01-20 17:25:18 GMT (10 years,  
                       |8 weeks, 5 days, 17 hours and 51 minutes ago)""".stripMargin
    
    println(longString)

    // this finds the first match
    longString match 
        case imbeddedDate(year, _*) => println(s"In $year, a star is born 1")

    // In 2004, a star is born
    
    val replaceYear = longString match 
        case imbeddedDate(year, _*) => longString.replaceAll(year, "2005")

    println(replaceYear)

    // Date: 2005-01-20 17:25:18 GMT (10 years,  
    // 8 weeks, 5 days, 17 hours and 51 minutes ago)

    val dates = "Important dates in history: 2004-01-20, " +
                "1958-09-05, 2010-10-06, 2011-07-15"

    val firstDate = date.findFirstIn(dates).getOrElse("No date found")
    println(firstDate) //2004-01-20


    val firstYear = for (m <- date.findFirstMatchIn(dates)) yield m.group(1)
    println(firstYear) //Some(2004)

    firstYear match 
        case Some(x) => println(x)
        case None => println("None found")



    val allYears = for (m <- date.findAllMatchIn(dates)) yield m.group(1)
    println(allYears.isInstanceOf[List[String]]) //false
    val allYearsList = allYears.toList
    println(allYearsList) //List(2004, 1958, 2010, 2011)

    val allYM = for (m <- date.findAllMatchIn(dates)) yield (m.group(1), m.group(2))
    val list1 = allYM.toList
    println(list1) //List((2004,01), (1958,09), (2010,10), (2011,07))
    val unzippedListY = list1.unzip._1
    val unzippedListM = list1.unzip._2
    println(unzippedListY) //List(2004, 1958, 2010, 2011)
    println(unzippedListM) //List(01, 09, 10, 07)

    println(date.matches("2018-03-01"))                     // true
    println(date.matches("Today is 2018-03-01"))            // false
    println(date.unanchored.matches("Today is 2018-03-01")) // true

    val mi = date.findAllIn(dates) //this is an iterator
    while (mi.hasNext) 
        val d = mi.next
        if (mi.group(1).toInt < 1960) println(s"$d: An oldie but goodie.")

    val num = raw"(\d+)".r
    val all = num.findAllIn("123").toList  // List("123"), not List("123", "23", "3")
    println(all)

    val redacted = date.replaceAllIn(dates, "XXXX-XX-XX")
    println(redacted)
    // Important dates in history: XXXX-XX-XX, XXXX-XX-XX, XXXX-XX-XX, XXXX-XX-XX

    val yearsOnly = date.replaceAllIn(dates, m => m.group(1))
    println(yearsOnly) //Important dates in history: 2004, 1958, 2010, 2011

    import java.util.Calendar
    val months = (0 to 11).map { i => 
        val c = Calendar.getInstance 
        c.set(2014, i, 1) 
        f"$c%tb" 
    }

    println(months) //Vector(Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec)

    val c = Calendar.getInstance
    println()
    println(c)
    println()
    println(c.getTimeZone())
    println()
    println(c.get(Calendar.YEAR)) //2022
    println(c.get(Calendar.WEEK_OF_YEAR)) //53

    println(c.get(Calendar.SECOND)) //14
    // a few seconds later....
    // Thread.sleep(5000)
    println(c.get(Calendar.SECOND)) //14

    // The calendar only continues between runtimes, its doesn't cahange
    // when pausing the thread

    // get the max possible value for a calender value
    val maxWeeksInYear = c.getMaximum(Calendar.WEEK_OF_YEAR)
    println(maxWeeksInYear) //53

    val reformatted = date.replaceAllIn(dates, _ match { 
        case date(y,m,d) => f"${months(m.toInt - 1)} $d, $y" 
    })

    println(reformatted)
    // Important dates in history: Jan 20, 2004, Sep 05, 1958, Oct 06, 2010, Jul 15, 2011

    // //////////////////////////////////////////////////////////////////
    // Scala FP

    // trait Monad:
    //     def lift[A](a: A): Monad[A]
    //     def map[A, B](f: A => B): Monad[B]
    //     def flatMap[A, B](f: A => Monad[B]): Monad[B]

    // implicit val OptionMonad = new Monad[Option] {
    //     def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] =
    //         ma.flatMap(f)
    //     def lift[A](a: => A): Option[A] =Some(a)
    // }

    // implicit val IOMonad = new Monad[IO] {
    //     def flatMap[A, B](ma: IO[A])(f: A => IO[B]): IO[B] =ma.flatMap(f)
    //     def lift[A](a: => A): IO[A] = IO(a)
    // }

    ////////////////////////////////////////////////////////////////
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Pattern Matching".toUpperCase)
    
    val numbers1 = List(1)
    val numbers2 = List(1, 2, 3)
    val numbers3 = List()
    val description = numbers3 match
        case head :: Nil => println(s"the only element is $head")
        case head :: tail => println(s"there's a ${head} and a ${tail}")
        case Nil => println(s"List is empty")

    // case classes can use pattern matching out of the box
    case class CaseClass(param1: String, param2: Int)

    class NormalClass(val param1: String, val param2: Int):
        override def toString(): String = s"($param1, $param2)"

    object NormalClass:
        def unapply(normal: NormalClass): Option[(String, Int)] =
            Some((normal.param1, normal.param2))

        def unapply(param2: Int): Option[String] =
            Some(if (param2 < 21) "minor" else "major")

        def apply(param1: String, param2: Int): NormalClass =
            new NormalClass(param1, param2)
    

    val nClass = NormalClass("C", 1)
    println(nClass) //(A, 1)

    nClass match 
        case NormalClass("A", _) => println(s"Eh, A is ok") 
        case NormalClass("B", n) => println(s"Eh, B is ok, but you have a $n there") 
        case NormalClass("C", _) => println(s"C is for cookie, that's good enough for me") 

    // C is for cookie, that's good enough for me
    // We can do this with a normal class because we created an unapply method,
    // and we could instantiate without the "new" keyword because of the apply method

    // All these come right out of the box with case classes, very niccce
    
    // Since we overload the unapply method, and use that param specifically
    // with a match expression
    val legalStatus = nClass.param2 match 
        case NormalClass(status) => println(s"Legal status is $status")

    // Legal status is minor

    
    object even:
        def unapply(arg: Int): Boolean = arg % 2 == 0
        
    object singleDigit:
        def unapply(arg: Int): Boolean = arg > -10 && arg < 10

    val n: Int = 45
    val mathProperty = n match
        case singleDigit() => "single digit"
        case even() => "even number"
        case _ => println(s"I don't know")

    case class Or[A, B](a: A, b: B) //Either
    val either = Or(2, "two")
    val humanDescription = either match
     // case Or(number, string) => s"$number is written as $string"
        case number Or string => s"$number is written as $string"

    println(humanDescription)

    // decomposing sequences
    val numbers = List(1)
    // val vargarg = numbers match
    //     case List(1, _*) => "starting with one"
    
    abstract class MyList[+A]:
        def head: A = ???
        def tail: MyList[A] = ???

    case object Empty extends MyList[Nothing]
    case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

    object MyList:
        def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
            if (list == Empty) Some(Seq.empty)
            else unapplySeq(list.tail).map(list.head +: _)

        
    val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
    val decomposed = myList match
        case MyList(1, 2, _*) => "starting with 1, 2"
        case _ => "something else"

    println(decomposed)
    
    // The above is when you need to do in order to allow the varargs
    // capability in pattern matching for a new class you have created.
    // Basically, you need to write a unapplySeq method

    ////////////////////////////////////////////////////////////////
    // Custom Return Types for unapply
    // Needs: isEmpty: Boolean, and get: something
    case class Person(name: String)
    val bob = Person("Bob")

    abstract class Wrapper[T]:
        def isEmpty: Boolean
        def get: T

    object PersonWrapper:
        def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
            def isEmpty = false
            def get: String = person.name
        }
    
    println(bob match {
        case PersonWrapper(n) => s"This person's name is ${n}"
        case _ => "Default"
    })

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Partial functions".toUpperCase)
    println()

    val aFunction = (x: Int) => x + 1 // Function[Int, Int] === Int => Int

    val aPartialFunction: PartialFunction[Int, Int] = { //<--[PartialFunction Literal]
        case 1 => 42
        case 2 => 56
        case 5 => 999
    } // partial function value

    println(aPartialFunction(2)) //5
    // println(aPartialFunction(44)) // crashes

    // How to check if a partial function is defined at some value:
    println(aPartialFunction.isDefinedAt(67)) //false

    val lifted = aPartialFunction.lift // Int => Option[Int]
    println(lifted(2)) //Some(2)
    println(lifted(67))  //None

    val pfChain = aPartialFunction.orElse[Int, Int] {
        case 45 => 67
    }

    println(pfChain)

    // Partial Functions extend normal functions
    val aTotalFunction: Int => Int = {
        case 1 => 99
    }

    // HOFs accept partial functions as well
    val aMappedList = List(1,2,3). map {
        case 1 => 42
        case 2 => 78
        case 3 => 1000
    }
    println(aMappedList) //List(42, 78, 1000)

    // Partial Functions can only have ONE parameter type


    val aManualFussyFunction = new PartialFunction[Int, Int] {
       override def apply(x: Int): Int = x match 
            case 1 => 42
            case 2 => 65
            case 5 => 999
       override def isDefinedAt(x: Int): Boolean = 
            x == 1 || x == 2 || x == 3
    }


    val aFullPF = new PartialFunction[Int, Int] {
        override def apply(x: Int): Int = 42 / x
        override def isDefinedAt(x: Int): Boolean = x != 0 
    }

    val collectWithPartial = List(1, 0, 2, 0).collect(aFullPF)
    println(collectWithPartial) // List(42, 21)

    val chatBot: PartialFunction[String, String] = {
        case "hello" => "Hi, my name is HAL9000"
        case "goodbye" => "Goodbye, see you later"
        case "call mom" => "calling mom..."
    }

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Functional Collections".toUpperCase)
    println()

    // Sets are actually functions
    trait MySet[A] extends (A => Boolean):
        def apply(elem: A): Boolean =
            contains(elem)

        def contains(elem: A): Boolean
        def +(elem: A): MySet[A]
        def ++(anotherSet: MySet[A]): MySet[A]

        def map[B](f: A => B): MySet[B]
        def flatMap[B](f: A => MySet[B]): MySet[B]
        def filter[A](predicate: A => Boolean): MySet[A]
        def foreach[A](f: A => Unit): Unit


    class EmptySet[A] extends MySet[A]
        def contains(elem: A): Boolean = false // there isn't an element contained in an EmptySet

        def +(elem: A): MySet[A] = new NonEmptySet[A](elem, tail = this)

        def ++(anotherSet: MySet[A]): MySet[A]  = anotherSet 

        def map[B](f: A => B): MySet[B] = new EmptySet[B]

        def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

        def filter[A](predicate: A => Boolean): MySet[A] = this

        def foreach[A](f: A => Unit): Unit = ()

    class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A]
        def contains(elem: A): Boolean = 
            elem == head || tail.contains(elem)

        def +(elem: A): MySet[A] =
            if (this.contains(elem)) this
            else new NonEmptySet[A](elem, tail = this)

        def ++(anotherSet: MySet[A]): MySet[A] =
            tail ++ anotherSet + head

        def map[B](f: A => B): MySet[B] = (tail.map(f)) + f(head)

        def flatMap[B](f: A => MySet[B]): MySet[B] = (tail.flatMap(f)) ++ f(head)

        def filter[A](predicate: A => Boolean): MySet[A] = 
            val filteredTail = tail.filter(predicate)
            if (predicate(head)) filteredTail + head
            else filteredTail

        def foreach[A](f: A => Unit): Unit = 
            f(head)
            tail.foreach(f)

        def -(elem: A): MySet[A] =
            if (head == elem) tail
            else tail - elem + head

        def --(anotherSet: MySet[A]): MySet[A] = ???

        def &(anotherSet: MySeq[A]): MySet[A] = filter(anotherSet) //   

    import scala.annotation.tailrec
    object MySet:
        def apply[A](values: A*): MySet[A] =  
            @tailrec
            def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
                if (valSeq.isEmpty) acc
                else buildSet(valSeq.tail, acc + valSeq.head)
            buildSet(values.toSeq, new EmptySet[A])


    import scala.annotation.tailrec
    def sum(list: List[Int]): Int =
        @tailrec
        def sumrec(list: List[Int], acc: Int): Int = list match
            case Nil => acc
            case head :: tail => sumrec(tail, acc + head)
        sumrec(list, 0)
    
    println(sum(List(1,2,3))) //6

    val superAdder: Int => Int => Int = x => y => x + y

    val add3 = superAdder(3)
    println(add3(2)) //5

    def curriedAdder(x: Int)(y: Int): Int = x + y //curried method

    val add4: Int => Int = curriedAdder(4)

    // to convert a method to be used as a function

    val add5 = curriedAdder(5) _ 
    println(add5(10)) //15

    println(
        List(1, 2, 3).map(add5)
    )
    // List(6, 7, 8)

    def add5d(i: Int) = i + 5

    println(
        List(1, 2, 3).map(add5d)
    )
    // List(6, 7, 8)

    val add7 = curriedAdder(7) _

    val add7_2 = (x: Int) => curriedAdder(7) _
    val add7_3 =  curryMethod.curried(7)
    

    // method to turn a method into a function
    def curryMethod(x: Int, y: Int): Int = x + y
    val add7_5 = curryMethod(7, _: Int)

    // underscores are powerful
    def concatenator(a: String, b: String, c: String): String = a + b + c
    val insertName = concatenator("Hello, I'm ", _: String, ", how are you?")
    println(insertName("Violet"))

    val fillInTheBlanks = concatenator("Hello, ", _: String, _: String)
    println(fillInTheBlanks("Violet", "Scala is awesome"))

    println("%4.2f".format(Math.PI))
    println("%4.2f".format(10000.34543))

    def curriedFormatter(s: String)(number: Double): String = s.format(number)
    val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

    val simpleFormat = curriedFormatter("%4.2f") _
    val seriousFormat = curriedFormatter("%8.6f") _
    val preciseFormat = curriedFormatter("%14.12f") _
    
    println(numbers.map(simpleFormat)) 
    //List(3.14, 2.72, 1.00, 9.80, 0.00)
    println(numbers.map(seriousFormat)) 
    //List(3.141593, 2.718282, 1.000000, 9.800000, 0.000000)
    println(numbers.map(preciseFormat)) 
    //List(3.141592653590, 2.718281828459, 1.000000000000, 9.800000000000, 0.000000000001)

    def byName(n: => Int): Int = n + 1
    def byfunction(f: () => Int): Int = f() + 1

    byName(23) //ok
    byName(method) //ok

    val function = (x: Int, y: Int) => x + y
    def method(x: Int, y: Int): Int = x + y

