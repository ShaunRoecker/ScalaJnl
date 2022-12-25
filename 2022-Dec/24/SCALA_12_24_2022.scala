object SCALA_12_24_2022 extends App:
    println("12/24/2022")
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 2.4 && 2.5
    /////////////////////////////////////////////////////////
    // 2.4 (pg.209)  


    /////////////////////////////////////////////////////////
    // 2.5 (pg.209)

    // REGEX Daily
    import java.util.regex.Pattern
    import scala.util.matching.Regex

    val date = raw"(\d{4})-(\d{2})-(\d{2})".r
    val date2 = """(\d{4})-(\d{2})-(\d{2})""".r

    val issueDate = "2017-01-23"

    val modIssueDate = issueDate match
        case date(year, month, day) => s"${month}/${day}/${year}"

    println(modIssueDate)

    val month = date.findAllIn(issueDate).group(2)
    println(month) //01

    val r1 = """(a)(b)?(c)?(d)?(e)?""".r
    println(r1.findAllIn("a").group(1))



    //println(r1.findAllIn(str1).start) //0
    //println(r1.findAllIn(str1).hasNext) //true

    val r = "(ab+c)".r
    val s = "xxxabcyyyabbczzz"
    r.findAllIn(s).start    // 3 -> start is the position of the first match
    val mi = r.findAllIn(s)
    mi.hasNext              // true
    mi.start                // 3
    mi.next()               // "abc"
    mi.start                // 3
    mi.hasNext              // true
    mi.start                // 9
    mi.next()               // "abbc"

    
    val dates2 = "Important dates in history: 2004-01-20, 1958-09-05, 2010-10-06, 2011-07-15"
    val yearsAndMonths = for {
        m <- date.findAllMatchIn(dates2)
    } yield m.group(2)

    println(yearsAndMonths.toList) //List(01, 09, 10, 07)

    val r2 = """[ab ]+""".r
    println(r2.matches("a    b   b"))

    val temp = """^([-+]?[0-9]+(\.[0-9]*)?)\s*([CcFf])$""".r
    println(temp.matches("+100.05           c")) //true
    println(temp.matches("-100F")) //true

    val multiLine = """Multi line string 1
                      |multi line string 2
                      |multi line string 3""".stripMargin
    
    val r3 = """string 3\z""".r
    println(r3.findAllIn(multiLine).toList)

    // the "type" keyword in Scala
    trait Base {
        type T
        def method: T
    }

    class Implementation extends Base:
        type T = Int
        def method: T = 42
    
    // type can be used as an alias for more complex types to create more concise code
    type Stack = List[String]
    def addToStack(nameStack: Stack, name: String): Stack =
        val newStack = name :: nameStack
        newStack
    
    val stack = List("NameOne")
    println(addToStack(stack, "newName")) //List(newName, NameOne)

    val list = List("a", "b", "c", "d", "e")
    val list2 = for {
        s <- list
    } yield s.toUpperCase

    val list3 = list.map(_.toUpperCase)

    println((list2, list3)) // (List(A, B, C, D, E),List(A, B, C, D, E))

    // Pattern Matching
    import scala.util.Random

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("pattern matching".toUpperCase)
    println()

    val random = new Random
    val x = random.nextInt(10)

    val description = x match 
        case 1 => "the ONE"
        case 2 => "the SECOND"
        case 3 => "the THIRD"
        case _ => "the OTHER"
    
    println(description)

    case class Person2(name: Name, age: Int)

    case class Name(firstName: String, middleName: String = "", lastName: String)

    val person = Person2(
        Name(firstName = "Chris", lastName = "Black"),
        28)

    println(person)

    person.name match
        case Name(fn, mn, ln) => println(s"$fn : $mn : $ln")

    val firstn = person.name.firstName match
        case x if x.capitalize == "Chris" =>  s"$x is a Chris"
        case _ => "Don't know that guy"
    

    println(firstn)

    sealed class Animal
    case class Dog(breed: String) extends Animal
    case class Parrot(greeting: String) extends Animal

    val animal = Dog("Labrador")
    animal match
        case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")

    // x is Random nextInt, remember?
    val isEven = x match 
        case n if n % 2 == 0 => true
        case _ => false
    
    // Why?
    val isEvenCond = if (x % 2 == 0) true else false
    val isEvenNormal = x % 2 == 0

    trait Expr
    case class Number(n: Int) extends Expr
    case class Sum(e1: Expr, e2: Expr) extends Expr
    case class Prod(e1: Expr, e2: Expr) extends Expr

    def show(e: Expr): String = e match 
        case Number(n) => s"$n"
        case Sum(e1, e2) => show(e1) + " + " + show(e2)
        case Prod(e1, e2) => {
            def maybeShowParentheses(exp: Expr) = exp match 
                case Prod(_, _) => show(exp)
                case Number(_) => show(exp)
                case _ => "(" + show(exp) + ")"

            maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
        }
    
    println(show(Sum(Prod(Number(2), Number(6)), Prod(Number(3), Number(5))))) //2 * 6 * 3 * 5

    val x1: Any = "Scala"
    val constants = x1 match 
        case 1 => "a number"
        case "Scala" => "the SCALA"
        case true => "Boolean"
        case SCALA_12_24_2022 => "a singleton object"
    
    println(constants)

    val matchAnything = x1 match
        case _ => "default"
    
    println(matchAnything)
    
    val matchVariable = x1 match
        case something => s"I've found ${something}"

    println(matchVariable)

    val aTuple = (1, 2)

    val matchTuple = aTuple match
        case (1, 1) => s"I've found ${aTuple}"
        case (something, 2) => s"I've found ${something}"
    
    println(matchTuple)

    val nestedTuple = (1, "t") match
        case _: Tuple2[Int, String] => "default tuple(Int, String)"
        

    println(nestedTuple)

    val nestedTuple2 = (1, (2, 3)) match
        case (_, (3, 4)) => "Tuple 1"
        case (_, _) => "Tuple 2"
    
    println(nestedTuple2)

    import scala.annotation.tailrec
    def recursion(xs: List[Int], acc: Int): Int = xs match
        case Nil => acc
        case x :: xs => recursion(xs, acc + x)
    
    // // list patterns
    // val aStandardList = List(1, 2, 3, 42)
    // val standardListMatching = aStandardList match
    //     case List(1, _, _, _) => //extractor
    //     case List(1, _*) => //vararg (list of arbitrary length with 1 at the head)
    //     case 1 :: List(_) => //infix pattern
    //     case List(1, 2, 3) :+ 42 => // infix pattern

    // // type specifiers
    // val unknown: Any = 2
    // val unknownMatch = unknown match
    //     case list: List[Int] => // explicit type specifier
    //     case _ =>

    // // name binding
    // val nameBindingMatch = aStandardList match
    //     case nameBinding @ List(_) => // use the name later in the match expression


    def regexPatterns(toMatch: String): String = {
        val numeric = """([0-9]+)""".r
        val alphabetic = """([a-zA-Z]+)""".r
        val alphanumeric = """([a-zA-Z0-9]+)""".r

        toMatch match {
            case numeric(value) => s"I'm a numeric with value $value"
            case alphabetic(value) => s"I'm an alphabetic with value $value"
            case alphanumeric(value) => s"I'm an alphanumeric with value $value"
            case _ => s"I contain other characters than alphanumerics. My value $toMatch"
        }
    } 
    println(regexPatterns("aa4"))

    case class Person(name: String, age: Int = 1)
    val mary = Person("Mary", 32)
    val joe = Person("Joe", 45)

    def binderPatternWithPartMatch(person: Person): String =
        person match
            case person @ Person("Mary", _) => s"This is ${person.name}, she is ${person.age}"
            case _ => "Not Mary"
    
    println(binderPatternWithPartMatch(joe))
    println(binderPatternWithPartMatch(mary))

    val x5 = "hat" match
        case hat @ "hat" => s"this is ${hat}"
        
    println(x5)

    def regexMatch(h: String): String = 
        val rgx = """h""".r
        rgx.matches(h) match
            case t @ true => s"this is a $h (matches $rgx: $t)"
            case f @false => s"this is a $h (matches $rgx: $f)"
        

    println(regexMatch("h")) //this is a h (matches h: true)
    println(regexMatch("ha")) //this is a ha (matches h: false)

    val numbers = List(1, 2, 3)
    val numbersMatch = numbers match
        case listOfString: List[String] => "a list of strings"
        case listOfNumbers: List[Int] => "a list of strings"
        case _ => ""

    println(numbersMatch) //a list of strings //note(generics don't work in this situation)
    // this is called "type erasure"

    val listx = List(1, 2, 3, 4)
    val evenOnes = for
        x <- listx if x % 2 == 0
    yield x * 10

    println(evenOnes) //List(20, 40)

    val tuples = List((1, 2), (3, 4))
    val filterTuples = for
        (first, second) <- tuples
    yield first * second

    println(filterTuples)

    val tuple = (1, 2, 3)
    val (a, b, c) = tuple
    println(a) //a

    //val list = List(a, b, c, d, e)
    val head :: tail = list
    println(head) // a
    println(tail) // List(b, c, d, e)

    // big idea - NEW (partial function)
    val mappedList = listx.map {
       case v if v % 2 == 0 => v + " is even"
       case 1 => "the one"
       case _ => "something else"
    }

    println(mappedList) //List(the one, 2 is even, something else, 4 is even)

    @tailrec 
    def factorial(n: Int, acc: Int): Int =
        if (n <= 0) acc
        else factorial(n - 1, n * acc)
    
    





    


    




            



            
            

            

            



            

            
        
    
    
    








    









