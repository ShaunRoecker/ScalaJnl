import java.{util => ju}
object Implicits extends App:
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("IMPLICITS".toUpperCase)
    println()

    val pair = "Daniel" -> "555"
    // How does this work since there isn't a "->" method in the String class?
    // This is an example of implicit conversion

    case class Person(name: String):
        def greet = s"Hi, I'm $name"

    implicit def fromStringToPerson(s: String): Person = Person(s)

    println("Peter".greet) //Hi, I'm Peter
    // the compiler looks for any implicit declarations that it can use to 
    // execute this code, since the "fromStringToPerson" method is implicit,
    // and accepts a String, it will be used to turn the String into a Person,
    // and then run the method greet (a Person method)

    // "Peter".greet => fromStringToPerson("Peter").greet

    // This is an implicit conversion, as above:

    implicit def fromPersonToString(p: Person): String = p.toString

    // With an implicit conversion here, we can call string methods on a Person

    val person = Person("Guy")
    println(person) // Person(Guy)  == 11 chars
    println(person.length) // 11

    // The above would not work without the implicit conversion, as the Person
    // class does not have a length method

    // those are implicit methods, but for implicit values, always add
    // the explicit type to the implicit val

    def increment(x: Int)(implicit amount: Int): Int = x + amount
    implicit val defaultAmount: Int = 10

    println(increment(10)) //20 
    // Not the same as Default args for a method

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Organizing Implicits".toUpperCase)
    println()
    // How and where to store implicits?

    println(List(1, 5, 6, 8, 3, 9).sorted) //List(1, 3, 5, 6, 8, 9)
    // sorted method:
        // override def sorted[B >: Int](implicit ord: Ordering[B]): List[Int]

    // Can see that the sorted method takes an implicit Ordering type value
    // We can create these Ordering type, to do custom sorting on more complicated 
    // objects

    implicit val reverseOrdering: Ordering[Int] = 
        Ordering.fromLessThan((a, b) => a > b)

    println(List(1, 5, 6, 8, 3, 9).sorted) //List(9, 8, 6, 5, 3, 1)

    // Since we added this implicit val of type Ordering, the sorted method
    // will use it instead of the implicit Ordering defined in the PreDef class
    // Also, this only works because the implicit Ordering we defined is of the
    // same type [Int] as is contained in the list

    // Create an implicit Ordering for the Dog class, so it can be used to sort
    // a list of Dogs by name
    case class Dog(name: String, age: Int)

    // implicit val dogNameAZ: Ordering[Dog] =
    //     Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)

    // If we but this implicit in any old object it won't work because
    // the compiler won't look there (only looks in local scope, imported scope,
    // and companion objects for the types involved in the method)

    object LaDeeDa:
        implicit val dogNameAZ: Ordering[Dog] =
        Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
    
    // If this ^^^ was the only implicit, this wouldn't run, however it will work
    // if we put it in a companion object of any type involved, in this case:
        // List, Ordering, or Dog

    // Lets put it in a Dog Companion object
    // object Dog:
    //     implicit val dogNameAZ: Ordering[Dog] =
    //     Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)

    val dogs = List(
        Dog("Spirit", 13),
        Dog("Violet", 7),
        Dog("Hurc", 14)
    )

    //println(dogs.sorted)
    // List(Dog(Hurc,14), Dog(Spirit,13), Dog(Violet,7))

    // hierarchy of where the compiler looks for these implicits when it needs to
    // 1. Local Scope
    // 2. Imported Scope
    // 3. Companion objects of all types involved in the method signature
        // For this case those would be:
            // 1. List Companion Object
            // 2. Ordering Companion Object
            // 3. all types involed = A or any supertype

    object DogNameOrdering:
        implicit val dogNameAZ: Ordering[Dog] =
            Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)

    object DogAgeOrdering:
        implicit val dogNameAZ: Ordering[Dog] =
            Ordering.fromLessThan((a, b) => a.age.compareTo(b.age) < 0)

    
    // If we want to use one of these orderings, import it into scope, and it will
    // be the Ordering that is used inplicitly when you call the sorted method on
    // a list of Dogs

    // import DogAgeOrdering._
    // println(dogs.sorted) //List(Dog(Violet,7), Dog(Spirit,13), Dog(Hurc,14))

    import DogNameOrdering._
    println(dogs.sorted) //List(Dog(Hurc,14), Dog(Spirit,13), Dog(Violet,7))

    val vector = Vector(1, 6, 4, 5, 3)
    println(vector.sorted) //Vector(6, 5, 4, 3, 1)

    val s3: String = "s3"

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Purchases Example".toUpperCase)
    println()

    case class Purchase(nUnits: Int, unitPrice: Double):
        val totalPrice: Double = nUnits * unitPrice
    
    object Purchase:
        implicit val totalPriceOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.totalPrice.compare(b.totalPrice) > 0)

    // This will set the default sorting to total price since it is in the companion object

    // create 3 Orderings:
        // 1. By total price
        // 2. By unit price
        // 3. By unit price

    object TotalPriceOrdering:
        implicit val totalPriceOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.totalPrice.compare(b.totalPrice) > 0)
    
    object NumberUnitsOrdering:
        implicit val numberUnitsOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.nUnits.compare(b.nUnits) > 0)

    object UnitPriceOrdering:
        implicit val unitPriceOrdering: Ordering[Purchase] =
            Ordering.fromLessThan((a, b) => a.unitPrice.compare(b.unitPrice) > 0)

    type Purchases = List[Purchase]

    val purchases: Purchases = List(
        Purchase(12, 12.99),
        Purchase(2, 110.99),
        Purchase(50, 1.99),
        Purchase(20, 19.99)
    )

    // import TotalPriceOrdering._
    // // total price sorting
    // println(purchases.sorted)
    // // List(Purchase(20,19.99), Purchase(2,110.99), Purchase(12,12.99), Purchase(50,1.99))

    // import NumberUnitsOrdering._
    // // number of units sorting
    // println(purchases.sorted)
    // // List(Purchase(50,1.99), Purchase(20,19.99), Purchase(12,12.99), Purchase(2,110.99))

    // import UnitPriceOrdering._
    // // unit price sorting
    // println(purchases.sorted)
    // List(Purchase(2,110.99), Purchase(20,19.99), Purchase(12,12.99), Purchase(50,1.9))

    println(purchases.sorted)
    //List(Purchase(20,19.99), Purchase(2,110.99), Purchase(12,12.99), Purchase(50,1.99))


    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Type Classes".toUpperCase)
    println()

    trait HTMLWritable:
        def toHtml: String

    case class User(name: String, age: Int, email: String) extends HTMLWritable:
        override def toHtml: String = s"<div>$name ($age) <a href=$email/></div>"

    trait HtmlSerializer[T]:
        def serialize(value: T): String

    implicit object UserSerializer extends HtmlSerializer[User]:
        override def serialize(user: User): String = 
            s"""User: ${user}, 
                name: ${user.name}, 
                age: ${user.age}, 
                email: ${user.email}""".stripMargin

    println(UserSerializer.serialize(User("Name", 34, "name@gmail.com")))

    // User: User(Name,34,name@gmail.com), 
    //             name: Name, 
    //             age: 34, 
    //             email: name@gmail.com

    implicit object IntSerializer extends HtmlSerializer[Int]:
        override def serialize(i: Int): String = s"Integer Number: ${i}"

    println(IntSerializer.serialize(123443)) //Integer Number: 123443


    import java.util.Date
    object DateSerializer extends HtmlSerializer[Date]:
        override def serialize(date: Date): String = 
            val d = date.getDate 
            s"<h1>$d</h1>"

    
    println(DateSerializer.serialize(new Date))

    object PartialUserSerializer extends HtmlSerializer[User]:
        override def serialize(user: User): String = 
            s"<h1>${user.name.toUpperCase}</h1>"

    println(PartialUserSerializer.serialize(User("Name", 34, "name@gmail.com")))
    // <h1>NAME</h1>



    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Equality type class".toUpperCase)
    println()
    // An equality type class
    trait Equality[T]:
        def equals(v1: T, v2: T): Boolean

    // object Equality:
    //     def apply[T](a: T, b: T)(implicit equilizer: Equality[T]) = 
    //         equilizer.apply(a, b)

    
    object UserNameEquality extends Equality[User]:
        override def equals(user1: User, user2: User): Boolean = 
            if (user1.name == user2.name) true else false

    val userOne = User("John", 32, "john1@gmail.com")
    val userTwo = User("John", 35, "john2@gmail.com")

    val usernameEquals = UserNameEquality.equals(userOne, userTwo)
    println(usernameEquals) //true

    object UserAgeEquality extends Equality[User]:
        override def equals(user1: User, user2: User): Boolean = 
            if (user1.age == user2.age) true else false

    val userAgeEquals = UserAgeEquality.equals(userOne, userTwo)
    println(userAgeEquals) //false

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Type classes/implicits part 2".toUpperCase)
    println()

    object HTMLSerializer:
        def serialize[T](value: T)(implicit serializer: HtmlSerializer[T]): String =
            serializer.serialize(value)

        def apply[T](implicit serializer: HtmlSerializer[T]) =
            serializer

    // implicit object IntSerializer extends HTMLSerializer[Int]:
    //     override def serialize(value: Int): String = 
    //         s"<div>$value</div>"

    println(HTMLSerializer.serialize(42))
    println(HTMLSerializer.serialize(userOne))

    // by adding an apply method and using HTMLSerializer[User]
    // we then have access to the entire type class interface
    // instead of just one method ->

    println(HTMLSerializer[User].serialize(userOne))

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Example 2".toUpperCase)
    println()

    // type class
    trait MyTypeClassTemplate[T]:
        def action(value: T): String

    // companion object
    // within the companion object we create an apply method that creates an instance
    // of the TypeClassTemplate with a given type.  This is so we can use the 
    // entire type class interface with different types
    object MyTypeClassTemplate:
        def apply[T](implicit instance: MyTypeClassTemplate[T]) = instance

    

    trait Example2[T]:
        def action(value: T): String

    object Example2:
        def apply[T](implicit instance: Example2[T]) = instance

    







    







    




    


    











    


