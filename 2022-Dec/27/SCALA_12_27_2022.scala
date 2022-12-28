object SCALA_12_27_2022 extends App:
    println("12/27/2022")


    import java.util.regex._
    import scala.util.matching.Regex

    val r1 = raw"Jeff(?=rey)".r
    val r2 = raw"(Jeff|Jeff(?=rey))".r
    val r3 = r2.unanchored

    val s1 = "This is Jeff, he is here"
    val s2 = "This is Jeff"
    val s3 = "This is Jeffrey, he is here"

    println(r1.findFirstIn(s1)) //None
    println(r1.findFirstIn(s2)) //None
    println(r1.findFirstIn(s3)) //Some(Jeff)

    val m1 = s1 match
        case r3(x) => x
        
    println(m1)

    // (?= ...) look-ahead
    // (?<= ...) look-behind
    //(?: ...) group-but-dont-capture

    val r4 = raw"(?<=\bJeff)(?=s\b)"
    // This reads "find a spot where we can look behind to find "Jeff",
    // and also look ahead to find "s"

    // Note: this regex doesn't actually match any text, rather it matches
    // at a position where we want to put an apostrophe

    // val s4 = "Jeffs"


    // val m2 = r4.replaceAll(s4, "'")
    // println(m2)
    
    // val r5 = """(?<=\d)(?=(\d\d\d+$)/,""".r
    // val s5 = "The US population"

    import scala.annotation.tailrec

    
    def sum(list: List[Int]): Int =
        @tailrec
        def sumRec(list: List[Int], acc: Int): Int = list match
            case Nil => acc
            case x :: xs => sumRec(xs, x + acc)
        sumRec(list, 0)

    println(sum(List(1, 2, 3, 4))) //10

    // def ALGORITHM(xs: List[Int]): Int = xs match 
        // case [TERMINATING CONDITION]
        // case HEAD :: TAIL => HEAD [OPERATOR] ALGORITHM(TAIL)

    // for simple algorithms like this, probably just need reduce
    // or foldLeft - which is reduce but you can set the accumulator
    // value

    // the function that goes into the reduce/foldLeft method just
    // needs to return the same type that is contained in the
    // container class, in this case List

    def algorithm(x: Int, y: Int): Int = 
        if ( x > y )  x + y - 2
        else if ( x < y ) x * y + 10
        else 0

    val x10 = List(1, 2, 3, 4, 5).reduce(algorithm)
    println(x10)

    // This algorithm method is nonsense, but it demonstrates that
    // you can get as complicated as you like, so long as it takes
    // 2 parameters of the same type that is contained in the List
    // (or other Sequence subclass, or custom class that has the 
    // reduce method implemented) and returns that same type.

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Implicits".toUpperCase)
    println()

    // Implicits
    val pair = "Dave" -> "556"
    val intPair = 1 -> 12

    case class Person(name: String):
        def greet = s"Hi, my name is $name!"

    implicit def fromStringToPerson(s: String): Person = 
        Person(s)

    println("Peter".greet) //this works because of the implicit method
    // the above code is turned to this: Person("Peter").greet
    // because we declared or fromStringToPerson method as implicit (or implied)

    // implicit parameters
    def increment(x: Int)(implicit amount: Int): Int = x + amount
    implicit val defaultAmount: Int = 10 

    increment(2) //this compiles without the amount parameter
    // be careful, the implicit val defaultAmount needs an explicit
    // (declared, not implied) type for this to work


    // The trait Ordering takes implicit parameters
    println(List(1,5,4,7,8,6).sorted) //List(1, 4, 5, 6, 7, 8)
    // this works because there is already an implicit Ordering for Int

    implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
    // implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
    println(List(1,5,4,7,8,6).sorted) 

    // Things that can be used as implicit parameters:
        // 1. val/var
        // 2. objects
        // accessor methods (defs with no parentheses)

    case class Person2(name: String, age: Int)

    val persons = List(
        Person2("John", 45),
        Person2("Amy", 23),
        Person2("Greg", 15)
    )

    implicit val alphabeticOrdering: Ordering[Person2] = 
        Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
    
    println(persons.sorted)
    println(persons.sorted(alphabeticOrdering)) // doesnt have to be an implicit val
    // can also add the Ordering to the sorted method

    //List(Person2(Amy,23), Person2(Greg,15), Person2(John,45))

    // The scope hierarchy for implicits
    //  -normal scope = LOCAL SCOPE
    //  -imported scope
    //  -companion objects of all types involved in the method signature


    // Ordering Purchases:
    // order by total price (used by 50% of backend code)
    // order by unit count (used by 25% of backend code)
    // order by unit price (used by 25% of backend code)

    case class Purchase(nUnits: Int, unitPrice: Double):
        def totalPrice: Double = nUnits * unitPrice

    object Purchase:
        implicit val totalPriceOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.totalPrice < b.totalPrice)

    object UnitCountOrdering:
        implicit val unitCountOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.nUnits < b.nUnits)

    object UnitPriceOrdering:
        implicit val unitPriceOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.unitPrice < b.unitPrice)

    val purchases = List(
        Purchase(10, 12.59),
        Purchase(5, 9.99),
        Purchase(20, 159.99)
    )

    println(purchases.sorted)
    //List(Purchase(5,9.99), Purchase(10,12.59), Purchase(20,159.99))

    

    
    

    





    












