import scala.util.Failure
object Implicits1:
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Implicits and Type Classes".toUpperCase)
    println()

    case class Person(name: String):
        def greet = s"Hi, I'm ${name}"

    object Person:
        implicit val personOrderingAZ: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => a.name.compare(b.name) < 0)

    implicit def formStringToPerson(s: String): Person = Person(s)

    println("Bert".greet) //Hi, I'm Bert

    val persons = List(
        Person("A"),
        Person("B"),
        Person("C")
    )

    object PersonOrderZA:
        implicit val personOrderingZA: Ordering[Person] = 
            Ordering.fromLessThan((a, b) => a.name.compare(b.name) > 0)

    // Sorted Method Signature:
    // override def sorted[B >: Person](implicit ord: Ordering[B]): List[Person]

    println(persons.sorted) //List(Person(C), Person(B), Person(A))


    case class Purchase(nUnits: Int, unitPrice: Double):
        def totalPrice: Double = nUnits * unitPrice

    object Purchase:
        implicit val totalPriceOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.totalPrice.compare(b.totalPrice) > 0)

    
    val purchases = List(
        Purchase(10, 34.99),
        Purchase(5, 30.99),
        Purchase(8, 12.99)
    )

    println(purchases.sorted) //List(Purchase(10,34.99), Purchase(5,30.99), Purchase(8,12.99))

    object UnitPriceOrderingLH:
        implicit val unitPriceOrderingLH: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.unitPrice.compare(b.unitPrice) < 0)

    import UnitPriceOrderingLH._

    println(purchases.sorted)

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("type classes".toUpperCase)
    println()

    trait HTMLWritable:
        def toHTML: String

    //(((1))) One way to to overwrite the abstract method in the trait
    case class User(name: String, age: Int, email: String) extends HTMLWritable:
        override def toHTML: String = s"<div>$name:$age:$email</div>"

    println(User("John", 32, "john@example.com").toHTML)

    //(((2))) better way to do this
    trait HTMLSerializer[T]:
        def serialize(value: T): String

    implicit object UserSerializer extends HTMLSerializer[User]:
        def serialize(user: User): String = 
            s"<div>${user.name}:${user.age}:${user.email}</div>"

    val john = User("John", 32, "john@example.com")
    println(UserSerializer.serialize(john))

    // The second way allows us to define HTMLSerializers for other types
    // not just the User type

    // Demonstrate that here with a Java date
    import java.util.Date

    object DateSerializer extends HTMLSerializer[Date]:
        def serialize(date: Date): String = s"<h1>${date.toString}</h1>"

    val date = java.util.Date()
    println(DateSerializer.serialize(date))

    // This HTMLSerializer trait is called a type class
    // Implementer of a "type class" are called type class instances
    // (These would be UserSerializer and DateSerializer, in this case)
    // We use singleton objects to instantiate type classes because it
    // doesn't make sense to create multiple instances of them,
    // like a class does

    // General type class looks like this:
    // is always of some variable type ([T])
    trait MyTypeClassTemplate[T]:
        // contains action methods that act on that variable type
        def action1(value: T): String
        def action2(value: T): Int

    object MyTypeClassTemplate:
        def apply[T](implicit instance: MyTypeClassTemplate[T]) = instance

    // Any implementors of MyTypeClassTemplate(a Type Class) need
    // to reify these action methods
    // String instance of MyTypeClassTemplate
    implicit object StringInstanceOfMyTypeClassTemplate extends MyTypeClassTemplate[String]:
        def action1(s: String): String = s
        def action2(s: String): Int = 
            scala.util.Try(Integer.parseInt(s)) match
               case scala.util.Success(x) => x
               case scala.util.Failure(x) => 0


    println(StringInstanceOfMyTypeClassTemplate.action2("99")) //99
    println(MyTypeClassTemplate[String].action2("g")) //0


    // Int instance of MyTypeClassTemplate
    object IntInstanceOfMyTypeClassTemplate extends MyTypeClassTemplate[Int]:
        def action1(i: Int): String = i.toString
        def action2(i: Int): Int = i

    println(IntInstanceOfMyTypeClassTemplate.action1(99)) //99

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Equality Type Class".toUpperCase)
    println()

    trait Equality[T]:
        def hasEquality(a: T, b: T): Boolean

    object Equality:
        def apply[T](implicit instance: Equality[T]): Equality[T] = instance

    // case class User(name: String, age: Int, email: String) extends HTMLWritable:
    //     override def toHTML: String = s"<div>$name:$age:$email</div>"
    // (((1))) Create a User Equality Type Class instance:
    implicit object UserEquality extends Equality[User]:
        def hasEquality(user1: User, user2: User): Boolean = 
            if user1.email == user2.email then true else false

        def ageDiffOneYear(user1: User, user2: User): Boolean = 
            if (Math.abs(user1.age - user2.age) == 1) then true else false


    val userA = User("A", 1, "A@example.com")
    val userB = User("B", 2, "B@example.com")

    println(UserEquality.hasEquality(userA, userB)) //false
    // Is the age difference of two users off by one?
    println(UserEquality.ageDiffOneYear(userA, userB)) //true
    // This one doesn't have much relavance to equality, but
    // I'm just going with it

    println(Equality[User].hasEquality(userA, userB)) //false


    // trait HTMLSerializer[T]:
    //     def serialize(value: T): String

    // object UserSerializer extends HTMLSerializer[User]:
    //     def serialize(user: User): String = 
    //         s"<div>${user.name}:${user.age}:${user.email}</div>"

    // val john = User("John", 32, "john@example.com")
    // println(UserSerializer.serialize(john))

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Combining implicits with the type class concept".toUpperCase)
    println()

    object HTMLSerializer:
        def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
            serializer.serialize(value)
        // we can create a factory method
        def apply[T](implicit serializer: HTMLSerializer[T]) = serializer

    implicit object IntSerializer extends HTMLSerializer[Int]:
        override def serialize(value: Int): String = s"<div>$value</div>"

    println(HTMLSerializer.serialize(42)(IntSerializer)) //<div>42</div>
    // This works fine, but we can declare the IntSerializer object as implcit,
    // and with Int values, we don't have to worry about the second parameter:
    // HTMLSerializer[T]

    println(HTMLSerializer.serialize(43)) // <div>43</div>

    // We can create different serializers, that will be used by the compiler,
    // if the type that we are trying to serialize is of a type that we have 
    // created a type class instnace for.
    case class Book(title: String, pages: Int)
    val book = Book("El Sorborno", 402)

    implicit object BookSerializer extends HTMLSerializer[Book]:
        override def serialize(book: Book): String = 
            s"""<div>
               |<h1>${book.title}</h1>
               |<h2>Pages: ${book.pages}</h2>
               |</div>""".stripMargin
    
    println(HTMLSerializer.serialize(book))
    // <div>
    // <h1>El Sorborno</h1>
    // <h2>Pages: 402</h2>
    // </div>
    // Since we created an implicit object BookSerializer, the compiler
    // uses it for the serialize method, and we don't need the second parameter

    println(HTMLSerializer.serialize(john))//<div>John:32:john@example.com</div>

    // the apply method we added to the HTMLSerializer object, allows us to service
    // out the HTMLSerializer for a type, and allows us to do this instead
    println(HTMLSerializer[User].serialize(john))
    // This allows us to use any of the methods in the HTMLSerializer object

// Pimping a Library
object Implicits2:
    import scala.annotation.tailrec

    implicit class RichInt(val value: Int) extends AnyVal: //extends AnyVal for 
        def isEven: Boolean = value % 2 == 0           // optimation purposes
        def sqrt: Double = Math.sqrt(value)

        def times(f: () => Unit): Unit =       
            def timesRec(n: Int): Unit =
                if (n <= 0) ()
                else f(); timesRec(n - 1)
            timesRec(value)

        def *[T](list: List[T]): List[T] =
            def concatenate(n: Int): List[T] =
                if (n <= 0) List()
                else concatenate(n - 1) ++ list
            concatenate(value)

       
    println(42.isEven) // 42
    //10.times(() => println("Hello")) 
    // With just a couple lines of code, we are able to enrich
    // the Int class, and implicitly use RichInt methods with
    // an Int value

    implicit class SuperString(value: String):
        def meanIt: String = value + "!"
        def asInt: Int = 
            scala.util.Try(Integer.parseInt(value)) match
                case scala.util.Success(x) => x
                case scala.util.Failure(_) => 0

        def encrypt(distance: Int): String =
            value.map(c => (c + distance).asInstanceOf[Char])
        def decrypt(distance: Int): String =
            value.map(c => (c - distance).asInstanceOf[Char])

     

    println("OK".meanIt) // OK!
    println("45".asInt)  // 45
    println("45Tz".asInt) // 0

    // can't do an implicit conversion of a implicit conversion
    val time1 = java.util.Date().getSeconds().toInt
    //Thread.sleep(3000)
    val time2 = java.util.Date().getSeconds().toInt
    println((time1, time2))

    val encrypt1 = "Hello, World!".encrypt(time1)
    println(encrypt1)
    val decrypt1 = encrypt1.decrypt(time1)
    println(decrypt1)

    val encrypt2 = "Hello, World!".encrypt(time2)
    println(encrypt2)
    val decrypt2 = encrypt1.decrypt(time2)
    println(decrypt2)

    println(3 * List(1, 2, 3)) //List(1, 2, 3, 1, 2, 3, 1, 2, 3)

// Type classes part 3
object Implicits3:
    trait HTMLWritable:
        def toHTML: String

    case class User(name: String, age: Int, email: String) extends HTMLWritable:
        override def toHTML: String = s"<div>$name:$age:$email</div>"

    trait HTMLSerializer[T]:
        def serialize(value: T): String

    implicit object UserSerializer extends HTMLSerializer[User]:
        def serialize(user: User): String = 
            s"<div>${user.name}:${user.age}:${user.email}</div>"

    val john = User("John", 32, "john@example.com")
    //println(UserSerializer.serialize(john))

    // The second way allows us to define HTMLSerializers for other types
    // not just the User type

    // Demonstrate that here with a Java date
    import java.util.Date

    object DateSerializer extends HTMLSerializer[Date]:
        def serialize(date: Date): String = s"<h1>${date.toString}</h1>"

    val date = java.util.Date()
    //println(DateSerializer.serialize(date))

    // This HTMLSerializer trait is called a type class
    // Implementer of a "type class" are called type class instances
    // (These would be UserSerializer and DateSerializer, in this case)
    // We use singleton objects to instantiate type classes because it
    // doesn't make sense to create multiple instances of them,
    // like a class does
    object HTMLSerializer:
        def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
            serializer.serialize(value)
        // we can create a factory method
        def apply[T](implicit serializer: HTMLSerializer[T]) = serializer

    implicit object IntSerializer extends HTMLSerializer[Int]:
        override def serialize(value: Int): String = s"<div>$value</div>"

    //println(HTMLSerializer.serialize(42)(IntSerializer)) //<div>42</div>
    // This works fine, but we can declare the IntSerializer object as implcit,
    // and with Int values, we don't have to worry about the second parameter:
    // HTMLSerializer[T]

    //println(HTMLSerializer.serialize(43)) // <div>43</div>

    // We can create different serializers, that will be used by the compiler,
    // if the type that we are trying to serialize is of a type that we have 
    // created a type class instnace for.
    case class Book(title: String, pages: Int)
    val book = Book("El Sorborno", 402)

    implicit object BookSerializer extends HTMLSerializer[Book]:
        override def serialize(book: Book): String = 
            s"""<div>
               |<h1>${book.title}</h1>
               |<h2>Pages: ${book.pages}</h2>
               |</div>""".stripMargin
    
    println(HTMLSerializer[Book].serialize(book))
    // <div>
    // <h1>El Sorborno</h1>
    // <h2>Pages: 402</h2>
    // </div>
    // Since we created an implicit object BookSerializer, the compiler
    // uses it for the serialize method, and we don't need the second parameter

    //println(HTMLSerializer.serialize(john))//<div>John:32:john@example.com</div>

    // the apply method we added to the HTMLSerializer object, allows us to service
    // out the HTMLSerializer for a type, and allows us to do this instead
    //println(HTMLSerializer[User].serialize(john))
    // This allows us to use any of the methods in the HTMLSerializer object
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Type class Enrichment".toUpperCase)
    println()

    implicit class HTMLEnrichment[T](value: T):
        def toHTML(implicit serializer: HTMLSerializer[T]): String = 
            serializer.serialize(value)

    println(john.toHTML)

    /*
        What we have here:
        - type class itself HTMLSerializer[T]{ ... } (it's the trait)
        - type class instances (some of which are implicit)
            - UserSerializer, BookSerializer, IntSerializer, StringSerializer, etc...
        - conversion with implicit classes (HTMLEnrichment)
    */

    trait Equality[T]:
        def hasEquality(a: T, b: T): Boolean

    object Equality:
        def apply[T](implicit instance: Equality[T]): Equality[T] = instance

    // case class User(name: String, age: Int, email: String) extends HTMLWritable:
    //     override def toHTML: String = s"<div>$name:$age:$email</div>"
    // (((1))) Create a User Equality Type Class instance:
    implicit object UserEquality extends Equality[User]:
        def hasEquality(user1: User, user2: User): Boolean = 
            if user1.email == user2.email then true else false

        def ageDiffOneYear(user1: User, user2: User): Boolean = 
            if (Math.abs(user1.age - user2.age) == 1) then true else false


    val userA = User("A", 1, "A@example.com")
    val userB = User("B", 2, "B@example.com")

    //println(UserEquality.hasEquality(userA, userB)) //false
    // Is the age difference of two users off by one?
    //println(UserEquality.ageDiffOneYear(userA, userB)) //true
    // This one doesn't have much relavance to equality, but
    // I'm just going with it

    //println(Equality[User].hasEquality(userA, userB)) //false

    implicit object StringEquality extends Equality[String]:
        def hasEquality(a: String, b: String): Boolean =
            if (a == b) true else false

    implicit class TypeLifter[T](value: T):
        def ===(other: T)(implicit instance: Equality[T]): Boolean =
            instance.hasEquality(value, other)

        



    println("Is this string" === "Is this string") //true
    println("Is this string" === "the same as this string") //false

    def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
        s"<html><body>${content.toHTML(serializer)}</body></html>"

    def htmlSugar[T : HTMLSerializer](content: T): Stirng = 
        s"<html><body>$content</body></html>"

    // implicitly
    case class Permissions(mask: String)
    implicit val defaultPermissions = Permissions("0744")



    
object Functional extends App:
    

    



        


