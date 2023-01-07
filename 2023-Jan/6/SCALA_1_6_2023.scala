object SCALA_1_6_2023 extends App:

    // Simulating Dynamic Typing with Union Types

    // UNION TYPES
    def aFunction(): Int | String =
        val x = scala.util.Random.nextInt(100)
        if (x < 50) then x else s"string: $x"

    val x: Int | String = aFunction()

    def isTruthy(a: Boolean | Int | String): Boolean = ???
    //def dogCatOrWhatever(): Dog | Plant | Car | Sun = ???


    // Combining union types with literal types
    type Bool = "True" | "False"

    def handle(b: Bool): Unit = b match 
        case "True" => println("true")
        case "False" => println("False")

    handle("True")
    handle("False")

    val t: Bool = "True"
    val f: Bool = "False"


    // INTERSECTION TYPES (Scala 3 only) --------------------------------------------------
    // Declaring that a value is a combination of types
    trait A:
        def a = "a"
    trait B:
        def b = "b"
    trait C:
        def c = "c"

    // you can define a method that requires its parameter's type to be
    // a combination of those types:
    def handleABC(x: A & B & C): Unit =
        println(x.a)
        println(x.b)
        println(x.c)

    // Now you can create a variable that matches that type and pass it 
    // into handleABC:
    class BCA extends B, C, A
    class CAB extends C, A, B

    handleABC(new BCA)
    handleABC(new CAB)

    trait Resettable:
        def reset(): Unit

    trait Growable[A]:
        def add(a: A): Unit

    def f(x: Resettable & Growable[String]): Unit =
        x.reset()
        x.add("first")
    // So with the method f, we have limited the types that can use the method
    // to only be that which extends Resettable and Growable[String].



    // Difference between intersection types and union types
    trait HasLegs:
        def run(): Unit
    trait HasWings:
        def flapWings(): Unit

    class Pterodactyl extends HasLegs, HasWings:
        def flapWings() = println("Flapping my wings")
        def run() = println("Im trying to run")
        override def toString = "Pterodactyl"

    class Dog extends HasLegs:
        def run() = println("I'm running")
        override def toString = "Dog"

    // returns a union type
    def getThingWithLegsOrWings(i: Int): HasLegs | HasWings =
        if i == 1 then Pterodactyl() else Dog()

    // returns an intersection type
    def getThingWithLegsOrWings(): HasLegs & HasWings =
        Pterodactyl()

    // MULTIVERSAL EQUALITY
    // multiversal equality is a way to make universal equality, which is
    // the "==" and "!=" methods, safer.  While with universal equality, 
    // which in Scala was derived from Java, Int == String will always return
    // false, but it will still compile, which can cause problems at runtime.
    // Multiversal equality makes it so this Int == String comparison won't even
    // compile

    case class Cat(name: String)
    case class Doggy(name: String)
    val d = Doggy("Fido")
    val c = Cat("Morris")

    // d == c  // false, but it compiles

    import scala.language.strictEquality // import when you want strict equality

    val rover = Doggy("Rover")
    val fido = Doggy("Fido")
    //println(rover == fido)   // compiler error

    //Enabling comparisons
    // Option 1
    case class Dog1(name: String) derives CanEqual

    // Option 2
    case class Dog2(name: String)
    given CanEqual[Dog2, Dog2] = CanEqual.derived

    val dog1 = Dog2("Violet")
    val dog2 = Dog2("Spirit")

    println(dog1 == dog2) //Dog2 class can now be compared despite scala.language.strictEquality

    // next example assumes scala.language.strictEquality is enabled
    // [2] create your class hierarchy
    trait Book:
        def author: String
        def title: String
        def year: Int

    case class PrintedBook(
        author: String,
        title: String,
        year: Int,
        pages: Int
    ) extends Book

    case class AudioBook(
        author: String,
        title: String,
        year: Int,
        lengthInMinutes: Int
    ) extends Book:
        // override to allow AudioBook to be compared to PrintedBook
        override def equals(that: Any): Boolean = that match
            case a: AudioBook =>
                if this.author == a.author
                && this.title == a.title
                && this.year == a.year
                && this.lengthInMinutes == a.lengthInMinutes
                    then true else false
            case p: PrintedBook =>
                if this.author == p.author && this.title == p.title
                    then true else false
            case _ =>
                false

    //Finally, use CanEqual to define which comparisons you want to allow:
    given CanEqual[PrintedBook, PrintedBook] = CanEqual.derived
    given CanEqual[AudioBook, AudioBook] = CanEqual.derived

    val p1 = PrintedBook("1984", "George Orwell", 1961, 328)
    val p2 = PrintedBook("1984", "George Orwell", 1961, 328)
    println(p1 == p2) //true

    val pBook = PrintedBook("1984", "George Orwell", 1961, 328)
    val aBook = AudioBook("1984", "George Orwell", 2006, 682)
    // println(pBook == aBook)   // compiler error

    //Enabling “PrintedBook == AudioBook”
    given CanEqual[PrintedBook, AudioBook] = CanEqual.derived
    given CanEqual[AudioBook, PrintedBook] = CanEqual.derived

    //Implement “equals” to make them really work
    // While these comparisons are now allowed, 
    // they will always be false because their equals methods 
    // don’t know how to make these comparisons. 

    println(aBook == pBook)   // true (works because of `equals` in `AudioBook`)
    println(pBook == aBook)   // false (haven't implemented `equals` in `PrintedBook`)

    // To compare Customers and Employees:

    import scala.language.strictEquality

    case class Customer(name: String):
        def canEqual(a: Any): Boolean = 
            a.isInstanceOf[Customer] || a.isInstanceOf[Employee]
        override def equals(that: Any): Boolean = 
            if !canEqual(that) then return false
            that match 
                case c: Customer => this.name == c.name
                case e: Employee => e.name == e.name
                case _ => false

    case class Employee(name: String):
        def canEqual(a: Any): Boolean = 
            a.isInstanceOf[Employee] || a.isInstanceOf[Customer]
        override def equals(that: Any): Boolean = 
            if !canEqual(that) then return false
            that match 
                case c: Customer => this.name == c.name
                case e: Employee => e.name == e.name
                case _ => false
            

    object CanEqualGivens:
        given CanEqual[Customer, Customer] = CanEqual.derived
        given CanEqual[Employee, Customer] = CanEqual.derived
        given CanEqual[Customer, Employee] = CanEqual.derived
        given CanEqual[Employee, Employee] = CanEqual.derived

    import CanEqualGivens.given

    val customer = Customer("Barb S.")
    val employee = Employee("Barb S.")

    println(customer == employee)

    // here A is in the contravariant position (in)
    class Stack1[A]:
        def push[A](x: A): Unit = ??? 

    // here A is in the covariant position (out)
    class Stack2[A]:
        def push[A](x: Int): A = ??? 

    // If we define A to be covariant[+A], then we have to do this
    // to ensure that whatever type we use extends A,
    // this is thought of as widening
    // (So long as we want to use A in the contravariant position)
    class StackCovariant1[+A]:
        def push[B >: A](x: B): List[B] = ???  
    // we can still have A in the covariant position
    class StackCovariant2[+A]:
        def push[A](x: Int): A = ???

    // Similarly if we want to define A to be contravariant, then we 
    // have to do this to ensure that whatever type we use it is a Super 
    // type of A,
    class StackContraVariant1[-A]:
        def push[A <: B](x: Int): B = ???  
    // we can still have A in the covariant position
    class StackContraVariant2[-A]:
        def push[A](x: A): Unit = ???


    // Existential Types (https://twitter.github.io/scala_school/type-basics.html)
    // Sometimes you do not care to be able to name a type variable, for example:
    def count[A](l: List[A]) = l.size
    // Instead you can use “wildcards”:
    def countEx(l: List[_]) = l.size

    // This is shorthand for:
    // def countWithForSome(l: List[T forSome { type T }]) = l.size

    // You may also apply bounds to wildcard type variables:
    def hashcodes(l: Seq[_ <: AnyRef]) = l.map(_.hashCode)



    


    
    

            





            






    











  

