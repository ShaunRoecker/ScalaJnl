import java.util.Date


object GivensUsing:

    def >>> = {println("..............SCALA_12_31_2022....................");println()}
    >>>
    val aList = List(2, 4, 6, 5)
    val orderedList = aList.sorted; println(orderedList)

    case class Name(firstName: String, middleName: String, lastName: String)
    case class Person(name: Name, age: Int)

    // With Scala 2 Implicits
    object PersonNameOrdering:
        implicit val personLNOrderingAZ: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => {
                a.name.lastName.compare(b.name.lastName) < 0 
            })
           
        implicit val personLNOrderingZA: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => a.name.lastName.compare(b.name.lastName) > 0)

        implicit val personFNOrderingAZ: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => a.name.firstName.compare(b.name.firstName) < 0)
           
        implicit val personFNOrderingZA: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => a.name.firstName.compare(b.name.firstName) > 0)

    //import PersonNameOrdering.personFNOrderingZA
    
    val personList = List(
        Person(Name("Alex", "M", "Peters"), 1),
        Person(Name("Sam", "A", "Adams"), 1),
        Person(Name("Zed", "W", "Ziggins"), 1)
    )

    //val orderedPersonLNAZ = personList.sorted; println(orderedPerson)
    //List(Person(Name(Sam,A,Adams),1), Person(Name(Alex,M,Peters),1), Person(Name(Zed,W,Ziggins),1))
    //val orderedPersonFNZA = personList.sorted; println(orderedPersonFNZA)

    // Scala 3 Givens
    object PersonOrderingGivens:
        given descendingOrdering: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => {
                    a.name.lastName.compare(b.name.lastName) > 0 
                })

    import PersonOrderingGivens.given // to import all givens
    println(personList.sorted)

    object GivenAnonymousClassNaive:
        given DescendingOrdering: Ordering[Int] = new Ordering[Int] {
            override def compare(a: Int, b: Int): Int = b - a
        }

    object GivenWith:
        given descendingOrdering_v3: Ordering[Int] with {
            override def compare(x: Int, y: Int) = y - x
        }

    import GivenWith.descendingOrdering_v3 //to import specific given

    val list2 = List(1, 2, 5, 4, 10).sorted; println(list2)

    // implicit arguments -> using
    def extremes[A](list: List[A])(using ordering: Ordering[A]): (A, A) =
        val sortedList = list.sorted
        (sortedList.head, sortedList.last)

    println(extremes(List(-67, 7, 34, 99, 500))) //(500,-67)

    // So, implicit vals are now "given"
    // And implicit arguments are now "using"

    // implicit defs?
    // used to synthesize new implicit values

    trait Combinator[A]: // a semigroup
        def combine(x: A, y: A): A

    // List(1, 2, 3) < List(4, 5, 6)

    implicit def listOrdering_2[A](implicit simpleOrdering: Ordering[A], 
        combinator: Combinator[A]): Ordering[List[A]] = 
            new Ordering[List[A]] {
                override def compare(x: List[A], y: List[A]) =
                    val sumX = x.reduce(combinator.combine)
                    val sumY = y.reduce(combinator.combine)
                    simpleOrdering.compare(sumY, sumY)
            }

    given listOrdering_3[A](using simpleOrdering: Ordering[A], combinator: Combinator[A]): 
        Ordering[List[A]] with {   
             override def compare(x: List[A], y: List[A]) =
                    val sumX = x.reduce(combinator.combine)
                    val sumY = y.reduce(combinator.combine)
                    simpleOrdering.compare(sumY, sumY)      
    }



object PIScala extends App:
    //  CO --> Just for concept purposes, not recommended to use in the wild

    val big = new java.math.BigInteger("12345")

    // type and value parameterization of an object
    val greetStrings: Array[String] = new Array[String](3)
    greetStrings(0) = "Hello"
    greetStrings(1) = ", "
    greetStrings(2) = "World!\n"
    // ^CO

    for i <- 0 to 2 do println(greetStrings(i))

    // operators are actually methods in scala
    println( 1 + 2 ); /* is actually */ println( 1.+(2) )

    // cant do: print greetStrings
    Console println greetStrings(0) //<-- can do

    // this concept of operators are actually methods
    // is why we access collection indices with () instead of []
    // like many languages, its actually calling a getter method

    // when we create a new instance object of a class,
    List(0, 1)
    // we are actually calling an apply method
    List.apply(0, 1)
    // However, the class needs an apply method for this to work
    // (Case classes come with built in apply methods and it's why
    // you don't need the "new" keyword to create an instance)

    greetStrings(0) = "Hello"; /* is actually*/ greetStrings.update(0, "Hello")

    class Person100(name: String)
    case class Person101(name: String)
    val p100 = new Person100("name") // wont work --> Person100("name")
    val p101 = Person101("name")

    for i <- 0.to(2) do println(greetStrings.apply(i))

    val numNames = Array("zero", "one", "two")
    val numNames2 = Array.apply("zero", "one", "two")

    // lists are immuatable in Scala
    val list10 = List(1, 2, 3)
    val list11 = List(4, 5, 6)

    // the '::' method is called Cons, it prepends an element
    // to an exiting list or another element
    val list12 =  4 :: List(1, 2, 3); println(list12)

    // To concatenate lists, use the ':::' method

    val list13 = list12 ::: List(1, 2, 3); println(list13)

    // when you use Cons with integers (not an already existing list)
    // you need to add :: Nil to the end
    // val list14 = 1 :: 2 // <-- won't compile
    val list14 = 1 :: 2 :: Nil; println(list14)

    // if the method name ends in a colon, it is actually operating
    // on the left operand 
    val list15 = Nil.::(2).::(1) ; println(list15)
    println(list14 == list15) //true

    // We typically only prepend "::" with Lists, becase appending
    // to a singly-linked list(class List) is a O(n) operation

    // So if you do need to append to a List object, you can 
    // prepend as many elements you need and then call the reverse 
    // method to mimic append operation

    val list16 = 101 :: 100 :: List[Int]()
    val list17 = list16.reverse; println(list16); println(list17)
    // List(101, 100)
    // List(100, 101)

    //  Some list methods and uses
    val emptyList = List.empty; println(emptyList)

    val thrill = "Will" :: "fill" :: "until" :: Nil
    println(thrill(2)) //until

    // drop method
    val drop2 = thrill.drop(2); println(drop2) //List(until)

    // dropRight method  //note: these are O(n)
    val dropRight2 = thrill.dropRight(2); println(dropRight2) //List(Will)

    // take method
    val take2 = thrill.take(2); println(take2) //List(Will, fill)

    // takeRight method //note: these are O(n)
    val takeRight2 = thrill.takeRight(2); println(takeRight2) //List(fill, until)

    // init method --> returns a list of all but the last element
    val initList = thrill.init; println(initList) //List(Will, fill)

    // filter method
    val filtered = thrill.filter(x => x.toUpperCase.startsWith("W"))
    println(filtered) //List(Will)

    // filterNot: return all all elements that return FALSE for a given predicate
    val filteredNot = thrill.filterNot(x => x.toUpperCase.startsWith("W"))
    println(filteredNot) //List(fill, until)

    // forall method: indicates whether ALL elements return true for a predicate
    val forAlllist = thrill.forall(_.contains("i")); println(forAlllist) //true

    // exists method: true if any elements return true for a given predicate
    val doesItExist = thrill.exists(_ == "Will") //true

    // last method: return the last element
    val lastThrill = thrill.last; println(lastThrill) //until
    // careful with this one
    //println(List.empty.last) // <-- won't compile

    val reversedThrill = thrill.reverse; println(reversedThrill) //List(until, fill, Will)

    // mkString method: makes a string out of a List (or other sequence)
    // can add beginners, separators and enders to the string
    val mkStringThrill = thrill.mkString("Beginner:{\n", ":Separator:", "\n}:Ender")
    println(mkStringThrill) //
    // Beginner:{
    // Will:Separator:fill:Separator:until
    // }:Ender

    // sortWith method: sorts a list with a predicate that compares two elements, one-by-one
    val sortedWith = thrill.sortWith((a, b) => a.toLowerCase.compareTo(b.toLowerCase) > 0)
    println(sortedWith) //List(Will, until, fill)

    // groupBy method: groups  elements in a list by either a predicate or a if expression
    def ifExpression(x: String): Int =
        if (x.toUpperCase.startsWith("W")) 1000
        else if (x.toUpperCase.startsWith("F")) 2000
        else 3000

    val groupedBy1 = thrill.groupBy(ifExpression)
    println(groupedBy1) // HashMap(2000 -> List(fill), 3000 -> List(until), 1000 -> List(Will))

    // groupBy with predicate:
    val groupedBy2 = thrill.groupBy(_.length == 4)
    println(groupedBy2) // HashMap(false -> List(until), true -> List(Will, fill))

    // can then grab these:
    println(groupedBy2(true)) // List(Will, fill)

    // splitAt method: splits the list(or Seq) into 2 at a given index (returns a tuple)
    println(thrill.splitAt(1)._2) //List(fill, until)

    // partition method: same as groupBy method, but only allows a predicate
    // however returns a tuple instead of a Map
    println(thrill.partition(_.length == 4)) //(List(Will, fill),List(until))
    println(thrill.partition(_.length == 4)._1) //grabs the "true" elements
    //List(Will, fill)

    //  The Tuple

    val tuple = new Tuple2[Int, String](1, "foo")
    println(tuple) //(1,foo)
    println(tuple._1) //1
    println(tuple._2) //foo

    // The Set

    val jetSet = scala.collection.immutable.Set("Boeing", "Airbus") //default Set is Immutable
    // don't need to do this
    val newSet = jetSet + "Lear"
    val query = jetSet.contains("cessna"); println(query) //false
    println(jetSet) //Set(Boeing, Airbus)
    println(newSet) //Set(Boeing, Airbus, Lear)

    val mutableSet = scala.collection.mutable.Set("Boeing", "Airbus")
    mutableSet += "Lear"
    println(mutableSet)

    import scala.collection.immutable.HashSet
    val hashSet = HashSet("tomatoes", "chilies")
    val ingrediaents = hashSet + "Coriander"
    println(ingrediaents)

    // Functional Style
    def printArgs(args: String*): Unit =
        for arg <- args do println(arg)

    printArgs(List("a", "b", "c", "d", "e", "f"):_*) // <-- the ':_*' allows to to pass
                                                    // a list of strings to the function
                                                    // that accepts varargs

    // a mor functional method that formats the arguments for printing
    def formatArgs(args: String*): String =
        args.mkString("\n")

    println(formatArgs(List("a", "b", "c", "d"):_*))

    // functional programming leads to easier testing:
    def formatArgsTest = 
        val res = formatArgs(List("zero", "one", "two"):_*)
        assert(res == "zero\none\ntwo")

    formatArgsTest

    val adjectives = List("One", "Two", "Red", "Blue")
    val mappedAdjectives = adjectives.map(_ + " Fish")
    println(mappedAdjectives)
    //List(One Fish, Two Fish, Red Fish, Blue Fish)

    // a for yield expression does the same thing as map
    val forExpr = for {
        a <- adjectives
    } yield a + " Fish"

    println(forExpr)
    //List(One Fish, Two Fish, Red Fish, Blue Fish)

    val mapped2 = forExpr.map(_.length)

    val mapped2For = 
        for x <- forExpr yield
            x.length

    println((mapped2, mapped2For))
    // (List(8, 8, 8, 9),List(8, 8, 8, 9))

    // find method
    val findW = thrill.find(_.toUpperCase.startsWith("W")) match
        case Some(x) => x
        case _ => ""

    println(findW) //Will

    val mappedChar = thrill.flatMap(x => x.map(y => y.toByte))
    println(mappedChar)
    // List(87, 105, 108, 108, 102, 105, 108, 108, 117, 110, 116, 105, 108)

    //  classes and objects:
    class ChecksumAccumulator:
        private var sum = 0

    import scala.collection.mutable
    // singleton object (in this case a "companion object")
    // companion object
    object ChecksumAccumulator:
        private val cache = mutable.Map.empty[String, Int]
        def calculate(s: String): String = s + " calculated.."

    // class instance
    val acc = new ChecksumAccumulator

    // case classes
    // Some things about case classes
    // 1. they have to take at least one parameter

    case class Person(name: String, age: Int)

    val p = Person("Name", 31) // we don't need the new keyword because
                                // they have an apply method under the hood

    // 2. also, the compiler will store all parameters as "fields", so you 
    // don't need to explicit define val name: String in the constructor,
    // therefore it already has getter and setter methods implemented so 
    // you can access case class fields outside the constructor

    println(p.name) //Name

    // 3. they already have a toString method, that returns a more useful
    // description of the case class instance.  Also, this toString method
    // is called when you simply write the name of the value for the instance
    // example:  println(p)  --> Person(Name, 31), rather than memory giberish
    println(p.toString) //Person(Name,31)
    println(p) //Person(Name,31)

    // 4. they also have an apply method implemented so you don't need the "new"
    // keyword to instantiate a new instance

    val person = Person("Henry", 29)

    // 5. ***very useful*** they already have an unapply method implemented
    // so you can use them in match expressions
    val henryTheName = person match
        case Person(n, a) => s"This is $n, he is $a years old"

    println(henryTheName) //This is Henry, he is 29 years old


    // 6. The compiler will provide an implementation of equals (==) and
    // hashCode methods so that case class instances can be compared
    // The comparison is based on the parameters passes to the constructor

    val p1 = Person("A", 10)
    val p2 = Person("A", 10)
    val p3 = Person("B", 15)
    val p4 = p1

    println(p1 == p2) //true
    println(p2 == p3) //false
    println(p1 == p3) //false

    val hcp1 = p1.hashCode()
    val hcp2 = p2.hashCode()
    val hcp3 = p3.hashCode()
    val hcp4 = p4.hashCode()

    println((hcp1, hcp2, hcp3, hcp4))
    // (1491002345,1491002345,-326578411,1491002345)
    // A hashCode is a unique integer value for an instance of an object

    println(hcp1 == hcp2) //true
    println(hcp1 == hcp4) //true
    println(hcp1 == hcp3) //false

    // 7. The compiler also create a copy method that is very helpful
    // for the functional programming style
    val henryTurned30 = person.copy(age = 30)
    println(henryTurned30) //Person(Henry,30)

    // Scala Entry Points: Ways to "run" a scala application or script
    // For Scala 3

    // 1. put the code you want to run in an object that extends App

    // object Application extends App:
    
    // 2. add the @main annotation to a method that has the code
    // you want to run as the application or script

    // @main
    // def application(): Unit =

    // Scala2 way:
    // 3. have a main method that takes an Array[String] and returns Unit
    // (within an object of whatever name)

    // object Application:
        // def main(args: Array[String]): Unit =
            // app code goes here

    val stringClass = classOf[String]
    println(stringClass)

    import scala.util.matching.Regex
    import java.util.regex.Pattern

    //val rgx = """(\p{L}|\p{M}|\p{Z}|\p{S}|\p{N}|\p{P})|\p{C})""".r

    println(System.getProperty("file.encoding")) //UTF-8

    def dateFormat(string: String): String = 
        val date = raw"(\d{4})-(\d{2})-(\d{2})".r
        string match
            case date(year, month, day) => s"$month/$day/$year"
            case _ => ""

    println(dateFormat("2022-12-31")) //12/31/2022

    val re = raw"\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{3}\b".r 
    val reu = re.unanchored
    val str = "The email address is this.email@gmail.com "
    println(reu.findFirstMatchIn(str)) //Some(this.email@gmail.com)
    println(reu.findAllIn(str).toList) //List(this.email@gmail.com)

    import scala.collection.JavaConverters._

    val sysEnv = System.getenv().asScala
    //for ((k, v) <- sysEnv) println(s"key: ${k}, value: ${v}")
    println("//////////////////////////////////////////")
    val properties = System.getProperties().asScala
    //for ((k,v) <- properties) println(s"key: $k, value: $v")


    // Interger literals
    // if it begins with 0x or 0X, it is hexidecimal (base 16)
        // -> it may contain 0 through 9 as well as upper and lower
        // case A through F

    // these are hexidecimal (base 16) integer literals
    val hex = 0x5; println(hex) //5
    val hex2 = 0x00FF; println(hex2) //255
    val magic = 0xcafe_babe; println(magic) //-889275714


    val billion = 1_000_000_000; println(billion)

    val sr = 0xAECE; println(sr) //44750

    // if a integer literal ends in an L or l, it's a Long
    // otherwise it's an Int

    val prog = 0XCAFEBABEL  //< -Int
    val tower = 35L //<- Long
    val of = 31l //<- Long

    // If an Int literal is assigned to a Short or Byte,
    // it is treated as a Short or Byte so long as it's 
    // within those types' respective ranges

    val little: Short = 367 // 367: Short
    val littler: Byte = 38 // 38: Byte

    // Floating point literals
    val big2 = 1.2345 //1.2345 Double
    val bigger = 1.2345e1 //12.345 Double
    val biggerStil = 123E45 //1.23E47: Double
    val trillion = 1_000_000_000e3 //1.0E12: Double
    val double1 = 134566D // Double

    // if a floating point literal ends in an F or f, its a Float
    // otherwise its a Double
    val float2 = 123E5f //Float

    // Char
    // Character literals are composed of any unicode character
    // between single quotes

    val a = 'A'
    println(a)

    // You can also identify a Char using its Unicode point
    val d = '\u0041' // Char = A
    val f = '\u0044' // Char = d

    val uni = '\u00DF'
    println(uni) // ß

    val backslash = '\\' // Char = \


    println(f"${Math.PI}%.5f") //3.14159

    // method overloading
    def plus(x: Int): Int = x + 1
    def plus(x: Long): Long = x + 1L
    println(plus(1))
    println(plus(1L))

    val s = "Hello, World!"
    println( s indexOf 'W') //7

    import scala.language.postfixOps
    println(s toLowerCase)

    case class Box(sides: Int):
        def unary_+ = println(s"Box has ${sides} sides")

    val box = Box(6)

    +box //Box has 6 sides

    // if you want to invoke the right hand side of && or ||
    // no matter what, use & and | instead

    

















    



    









    

    

































    

    














    

