
object SCALA_12_28_2022 extends App:

    case class User(name: String, age: Int, email: String)

    val john = User("John", 32, "john@gmail.com")
    println(john)

    val anotherJohn = User("John", 36, "john2@gmail.com")
    println(john)

    // Type Classes:
    trait Equal[T]:
        def apply(a: User, b: User): Boolean

    // object Equal:
    //     def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
    //         equalizer.apply(a, b)
    
    
    object NameEquality extends Equal[User]:
        override def apply(a: User, b: User): Boolean = a.name == b.name

    object FullEquality extends Equal[User]:
        override def apply(a: User, b: User): Boolean =
            a.name == b.name && a.email == b.email


    
    
    trait HTMLSerializer[T]:
        def serialize(value: T): String

    object HTMLSerializer:
        def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = 
            serializer.serialize(value)

        def apply[T](implicit serializer: HTMLSerializer[T]) = 
            serializer

    implicit object IntSerializer extends HTMLSerializer[Int]:
        override def serialize(value: Int): String = 
            s"<div class='ml3 p3'>$value</div>"

    println(HTMLSerializer.serialize(value = 42))

    

    //println(HTMLSerializer[User].serialize(john))

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Type Enrichment".toUpperCase)
    println()

    // implicit class that wraps an Int
    implicit class RichInt(val value: Int) extends AnyVal:
        def isEven: Boolean = value % 2 == 0
        def sqrt: Double = Math.sqrt(value)

        // def times(f:() => Unit): Unit =
        //     def timesAux(n: Int): Unit =
        //         if (n <= 0) ()
        //         else {
        //             f()
        //             timesAux(n - 1)
        //         }
        //     timesAux(value)

        def *[T](list: List[T]): List[T] =
            def concatenate(n: Int): List[T] =
                if (n <= 0) List()
                else concatenate(n - 1) ++ list
            concatenate(value)
        

    new RichInt(value = 42).sqrt

    // The below expression is interpreted as an error at first, but it doesn't 
    // give up, it searches for all the implicit classes within scope to see
    // if the 42 can be implicitly converted to that type and therefore
    // use the method that was called

    42.isEven //compiler rewrites to new RichInt(42).isEven

    // this is known as implicit conversion and it allows us to convert to
    // the RichInt type and use the RichInt methods, given a regular Int
    // So with Type Enrichment and implicit conversion, we can use RichInt
    // methods on the Int type

    // The compiler does not do multiple implicit searches
    // implicit class RicherInt(richInt: RichInt):
    //     def isOdd: Boolean = richInt.value % 2 != 0

    //11.isOdd // won't work because the compiler wont do multiple implicit searches
    // type enrichment

    1 to 10

    import scala.concurrent.duration._
    3.seconds


    import scala.util.{Try, Success, Failure}
    // enrich the String Class
    implicit class RichString(string: String):
        def asInt: Int = Try(Integer.valueOf(string)) match 
                case Success(x) => x
                case Failure(_) => 0
        
        def encrypt(cypherDistance: Int): String = 
            string.map(c => (c + cypherDistance).asInstanceOf[Char])

        def decrypt(cypherDistance: Int): String =
            string.map(c => (c - cypherDistance).asInstanceOf[Char])

        def enrich(string: String): RichString = new RichString(string)

        

        

    // println("12".asInt)
    // println("Jeff".asInt)
    // println("violet".encrypt(7)) // }pvsl{
    // println("}pvsl{".decrypt(7)) // violet

    // // 2.times(() => println("Violet Chunks!"))
    // println(4 * List(1, 2)) //List(1, 2, 1, 2, 1, 2, 1, 2)

    // val js = 3 + "4".asInt
    // println(js) //7

    // case class RichAltInt(value: Int)
    // implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

    // implicit def IntToBoolean(i: Int): Boolean = i == 1

    // //println(2.enrich)
    // // val enrichedS = "enriched string".enrich
    // // println(enrichedS.asInt)

    // Note: Keep type enrichment to implicit classes and type classes, not methods

        println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Regex Daily".toUpperCase)
    println()

    import java.util.regex._
    import scala.util.matching.Regex

    

    def addCommasToNum(s: String): String = 
        val r1 = """(?<=\d)(?=(?:\d\d\d)+$)""".r
        r1.replaceAllIn(s, ",")

    println(addCommasToNum("1000000")) //1,000,000

    val s1 = "The population of 299999999 is growing"

    // Positive lookbehind: (?<=...) "successful if 'can' match to the left"
    // Negative lookbehind: (?<!...) "successful if 'cannot' match to the left"
    // Positive lookahead: (?=...) "successful if 'can' match to the right"
    // Negative lookahead: (?!...) "successful if 'cannot' match to the right"

    // start of word boundary: """(?<!\w)(?=\w)""".r
    // end of word boundary: """(?<=\w)(?=\w)""".r

    // To allow or method to work with s1 as well
    def addCommasToNumInSentense(s: String): String = 
        val r1 = """(?<=\d)(?=(?:\d\d\d)+(?!\d))""".r
        r1.replaceAllIn(s, ",")

    println(addCommasToNumInSentense(s1)) //The population of 299,999,999 is growing

    def addApostropheToJeff(s: String): String =
        val r = """(?<=Jeff)(?=s\s*)""".r
        r.replaceAllIn(s, "'")

    println(addApostropheToJeff("Jeffs cat had another one of Jeffs things"))
    //Jeff's cat had another one of Jeff's things
    println("\n\n")

    val string1 = "Jeffs cat had another one of Jeffs things" 

    val newString1 = for {
        a <- addApostropheToJeff(string1)

    } yield a //+ b

    println(newString1)
    









































































