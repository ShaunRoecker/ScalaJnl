
object SCALA_1_2_2023 extends App:

    import scala.util.matching.Regex

    val newRegex = Regex(
        "([0-9]{2}):([0-9]{2}):([0-9]{2}).([0-9]{3})", 
        "Hours", "Minutes", "Seconds" // optional group names
    )

    val timestamp = """([0-9]{2}):([0-9]{2}):([0-9]{2}).([0-9]{3})""".r
    val hoursAndMinutes = timestamp.replaceAllIn("11:34:01.311", _ match {
        case timestamp(hours, minutes, seconds, _) => s"$hours-$minutes"
        })

    println(hoursAndMinutes)


    val regex1 = """([0-9])\1+""".r
    val str1 = "833337"
    val findFirst1 = regex1.findFirstMatchIn(str1)
    println(findFirst1) //Some(3333)
    //If you want to repeat the matched character, rather than the class, 
    // you need to use backreferences. ([0-9])\1+ matches 222 but not 837. 
    // When applied to the string 833337, it matches 3333 in the middle 
    // of this string. If you do not want that, you need to use lookaround.

    val regex2 = """([0-9])\1+""".r
    val str2 = "82233337"
    val findFirst2 = regex2.findFirstMatchIn(str2)
    println(findFirst2) //Some(22)
    // The match in this case is the 2 because it's the first character
    // that is repeated (more than one consecutive), and it returns all of
    // the 2s, if there were 100 consecutive 2s it would return all of them
    // the \1 is called a backreference, more on those later.

    // without the backreference: will match all of them
    val regex3 = """[0-9]+""".r
    val str3 = "82233337"
    val findFirst3 = regex3.findFirstMatchIn(str3)
    println(findFirst3) //Some(82233337)

    // Character Class Intersection
    // It makes it easy to match any single character that must 
    // be present in two sets of characters

    // This matches any consonant
    // The order doesn't matter, [^aeiuo&&[a-z]] is the same
    val regex4 = """[a-z&&[^aeiuo]]""".r
    val str4 = "aim high"
    val findFirst4 = regex4.findFirstMatchIn(str4)
    println(findFirst4) //Some(m)

    //You can intersect the same class more than once. 
    // [0-9&&[0-6&&[4-9]]] is the same as [4-6] 

    //[^1234&&[3456]] is both negated and intersected.
    //Java and PowerGREP reads this regex as “(not 1234) and 3456”
    // So its the same as [56]

    //[1234&&[^3456]] as “1234 and (not 3456)”. 
    // Thus this regex is the same as [12].

    // Character class Shorthands:
    // \d is short for [0-9]
    // \w stands for “word character”. It always matches the ASCII characters [A-Za-z0-9_]
    // \s stands for “whitespace character” it includes [ \t\r\n\f]
    // \D is the same as [^\d] (not a digit)
    // \W is short for [^\w] (not in [A-Za-z0-9_])
    // \S is the equivalent of [^\s]

    // Shorthand character classes can be used both inside and outside 
    // the square brackets. \s\d matches a whitespace character followed by a digit.

    //The dot matches a single character, 
    // without caring what that character is. 
    // The only exception are line break characters!!!

    //  \n is line break character

    // Windows text files normally break lines with a \r\n pair

    //When running on Windows, \r\n pairs are automatically converted
    //  into \n when a file is read, and \n is automatically written 
    // to file as \r\n.

    // If you read a Windows text file as a whole into a string, 
    // it will contain carriage returns

    //If you use the regex abc.* on that string, without setting RegexOptions.SingleLine, 
    // then it will match abc plus all characters that follow on the same line, 
    // plus the carriage return at the end of the line, but without the newline after that.

    //RegexOptions.SingleLine

    //  \N which matches any single character that is not a line break, 
    // just like the dot does.

    //"""^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$""".r 
    // matches a date in yyyy-mm-dd format from 1900-01-01 through 2099-12-31, 
    // with a choice of four separators.


    //  final and sealed keywords

    sealed class People //<- sealed means all subclasses must be defined in same file

    class Person extends People

    class Leg extends Person:
        final def addLeg = println("leg added") // final means can't be overwritten in case 
        def addAnotherLeg = println("another leg added") // of a method, and can't be 
                                                        // extended period in case of a class
    class Toe extends Leg:
        // override def addLeg = println("2 leg added") // <- cant be extended
        override def addAnotherLeg = println("3 leg added")

    val toe = new Toe
    toe.addLeg
    toe.addAnotherLeg

    final class CantBeExtendedClass
    // class SubClass extends CantBeExtendedClass  <-- wont compile

    // Chapter 12: Traits

    // traits have the default superclass of AnyRef

    trait Philosophical:
        def philosophize = "I consume memory, therefore I am!"

    trait HasLegs:
        def printLeg() = println("leg method")

    class Animal:
        override def toString: String = "SuperAnimal"

    class Frog extends Animal, Philosophical, HasLegs:
        override def toString: String = "green"
        override def philosophize: String = s"It ain't easy being $this!"
        def callSuperToString = 
            s"this toString: ${this.toString}, super toString: ${super.toString}"

    val frog = new Frog
    println(frog.philosophize)

    // a trait also defines a type
    val phil: Philosophical = frog
    // thus, variable phil could have been initialized with any 
    // object whose class mixes in Philosophical
    println(phil.philosophize)
    println(frog.callSuperToString) //this toString: green, super toString: SuperAnimal

    trait HasTail

    trait HasNose:
        def doSomethingWithNose = "doSomethingWithNose"

    class Pet(val name: String)

    // Mixin traits during variable construction
    val zeus = new Pet("Zeus") with HasTail with HasNose
    println(zeus.doSomethingWithNose)


    trait CanRun:
        this: HasLegs =>

    class Dog1 extends HasLegs, CanRun

    val dog = new Dog1
    dog.printLeg() //leg method

    //1.  Insuring a trait can only be extended to a Type that also
    // extends another trait:

        // trait ATrait:
        //     this: ReqTrait1 & ReqTrait2 & ReqTrait3 =>

    //2.  Insuring a trait can only be extended to a Type that has
    //  a specific method:
    
        // trait ATrait:
        //     this: { def ejectWarpCore(password: String): Boolean } =>

    // 3. Limiting which classes can use a trait by Inheritance

        // trait Employee
        // class CorporateEmployee extends Employee
        // class StoreEmployee extends Employee

        // trait DeliversFood extends StoreEmployee //<- all Types that extend DeliversFood
        //                                         // must also extend StoreEmployee

    trait Stringify[A]:
        def string(a: A): String

    object StringifyInt extends Stringify[Int]:
        def string(a: Int): String = s"value: ${a.toString}"

    import StringifyInt.*

    Console println string(42)  //value: 42

    trait Pair[A, B]:
        def getKey: A
        def getValue: B

    
    sealed trait Dog
    class LittleDog extends Dog
    class BigDog extends Dog

    // Type D must extend Dog
    trait Barker[D <: Dog]:
        def bark(d: D): Unit

    object LittleBarker extends Barker[LittleDog]:
        def bark(d: LittleDog): Unit = println("wuf")

    object BigBarker extends Barker[BigDog]:
        def bark(d: BigDog): Unit = println("WUF")

    val terrier = LittleDog()
    val husky = BigDog()

    LittleBarker.bark(terrier)
    BigBarker.bark(husky)

    // BigBarker.bark(terrier)   <- doesn't compile

    // In Scala 3 a trait can have parameters, just like a class

    trait Polygon(sides: Int)

    // enums in Scala3
    enum Position:
        case Top, Bottom, Left, Right

    // enums can have fields
    enum HttpResponse(val code: Int):
        case Ok extends HttpResponse(200)
        case MovedPermanently extends HttpResponse(301)
        case InternalServerError extends HttpResponse(500)

    import HttpResponse.*

    println(Ok.code)
    println(MovedPermanently.code)
    println(InternalServerError.code)

    // enums can also have methods:
    enum Planet(mass: Double, radius: Double):
        private final val G = 6.67300E-11
        def surfaceGravity = G * mass / (radius * radius)
        def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

        case Mercury extends Planet(3.303e+23, 2.4397e6)
        // More planets here...

    import Planet.*
    println(Mercury.surfaceWeight(150))

    // enums in Scala2
    sealed class CrustSize(val inches: Int)
    object CrustSize:
        case object Small extends CrustSize(10)
        case object Medium extends CrustSize(12)
        case object Large extends CrustSize(14)

    import CrustSize._

    println(Small.inches)
    
    








    







    





    