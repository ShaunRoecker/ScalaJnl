object Script {

    val filesHere = (new java.io.File(".")).listFiles
    val scalaFiles = for 
                        file <- filesHere 
                     yield
                        file.getName.endsWith(".scala")
                        
                        
    val forexp = for 
                    file <- filesHere
                    if file.isFile
                    if file.getName.endsWith(".scala")
                yield
                    file
    

                    
    def fileLines(file: java.io.File) =
        scala.io.Source.fromFile(file).getLines().toArray
    def grep(pattern: String) =
        for
            file <- filesHere
            if file.getName.endsWith(".scala")
            line <- fileLines(file)
            trimmed = line.trim
            if line.trim.matches(pattern)
        do println(s"$file: ${line.trim}")
    
    grep(".*gcd.*")

    //throw new IllegalArgumentException

    // throw is an expression that has a result type
    def half(n: Int) = 
        if n % 2 == 0 then
            n / 2
        else 
            throw new RuntimeException("n must be even")
       
}

object Generics {
    println("Generics")

    class MyList[A]
        // use the type A

    val listOfIntegers = new MyList[Int]
    val listOfStrings = new MyList[String]

    // object MyList[+A]
    //     def add(element: A): MyList[A] = ???
    
    //val emptyListOfIntegers = MyList.empty[Int]


    class MyMap[Key, Value]

    val mapOfIntStrings = new MyMap[Int, String]

    // variance problem
    class Animal
    class Cat extends Animal
    class Dog extends Animal

    // yes - List[Cat] extends List[Animal] = Covariance
    class CovariantList[+A]
    val covAnimal: Animal = new Cat
    val covAnimalList: CovariantList[Animal] = new CovariantList[Cat]

    // animalList.add(new Dog) ??? 

    // no - List[Cat] != List[Animal] = Invariance
    class InvariantList[A]  // vvv has to be Animal, cant be an extended type
    val invAnimalList: InvariantList[Animal] = new InvariantList[Animal]

    // no, in fact the opposite, can use List[Animal] in the type place of List[Cat]
    // think of a class Trainer, and we need a Trainer[Cat].  Well, Trainer[Animal] works
    // here becuase Trainer[Animal] can train cats, as well as other animals
    class ContravariantList[-A]
    val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]


    // class Cage accepts types that are subtypes of Animal
    class Cage[A <: Animal](animal: A)
}

object CaseClasses {
    case class Person(name: String, age: Int)

    // 1.  class parameters are fields with case classes
    val jim = new Person("Jim", 34)
    println(jim.name) //Jim (wouldn't work with regular classes, need to add val or var f=to set params to fields)

    // 2. case classes come with a sensible toString method
    println(jim.toString) //Person(Jim,34)

    // 3. equals and hashCode implemented out of the box
    val jim2 = new Person("Jim", 34)
    println(jim == jim2) //true

    // 4. case classes have handy copy methods
    val jim3 = jim2.copy(age = 35)
    println(jim3) // this is how we change values with immutable types and functional programming
    // very niccce! (Kazakhstani accent)

    // 5. case classes have companion objects
    val thePerson = Person
    val mary = Person("Mary", 23)
    println(mary) //Person(Mary,23)

    // 6. case classes are serializable
    // - serializable meaning that all their information can be converted to a "String" 
    // to a string and "sent" accross a network, and used by the receiver after deserialization
    // That's pretty neat
    // Akka deals with this

    // 7. case classes have extractor patterns - case classes can be used in pattern matching,
    //   very powerful

    // 8. case classes can have case objects
    case object UnitedKingdom:
        def name: String = "The United Kingdom of Great Britain"






}

object Enums {
    println("Enums")

    enum Permissions:
        case READ, WRITE, EXECUTE, NONE
        // because it's a data type, you can add fields and methods
        def openDocument(): Unit =
            if (this == READ) println("opening document...")
            else println("reading not allowed")
    
    
    // enums create a sealed data type that cannot be extended
    // All the possible types of "Persmission" are defined in the Permissions enum

    val somePermissions: Permissions = Permissions.READ

    somePermissions.openDocument()

    // enums can take constructor arguments
    enum PermissionsWithBits(bits: Int):
        case READ extends PermissionsWithBits(4)
        case WRITE extends PermissionsWithBits(2)
        case EXECUTE extends PermissionsWithBits(1)
        case NONE extends PermissionsWithBits(0)


    object PermissionsWithBits:
        def fromBits(bits: Int): PermissionsWithBits =
            PermissionsWithBits.NONE

    // another example (from the Scala 3 Reference)
    // Enums can be parameterized
    enum Color(val rgb: Int):
        case Red   extends Color(0xFF0000)
        case Green extends Color(0x00FF00)
        case Blue  extends Color(0x0000FF)


    enum Planet(mass: Double, radius: Double):
        private final val G = 6.67300E-11
        def surfaceGravity = G * mass / (radius * radius)
        def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

        case Mercury extends Planet(3.303e+23, 2.4397e6)
        case Venus   extends Planet(4.869e+24, 6.0518e6)
        case Earth   extends Planet(5.976e+24, 6.37814e6)
        case Mars    extends Planet(6.421e+23, 3.3972e6)
        case Jupiter extends Planet(1.9e+27,   7.1492e7)
        case Saturn  extends Planet(5.688e+26, 6.0268e7)
        case Uranus  extends Planet(8.686e+25, 2.5559e7)
        case Neptune extends Planet(1.024e+26, 2.4746e7)
    end Planet

    println(Planet.Mercury.surfaceGravity)

    enum PizzaSize(diameter: Int):
        private val Pi = math.Pi
        def radius = diameter / 2
        def area = Pi * (radius * radius)
        def printArea = 
            println(f"This pizza is ${this.area}%.1f square inches")

        case SMALL extends PizzaSize(10)
        case MEDIUM extends PizzaSize(12)
        case LARGE extends PizzaSize(14)

    
    

    val largePizza = PizzaSize.LARGE
    println(largePizza.area)
    println(largePizza.printArea)
    val mediumPizza = PizzaSize.MEDIUM

    // can inspect the ordinal of enums
    val somePizzaSizeOrdinal = largePizza.ordinal
    println(somePizzaSizeOrdinal)

    
    




}

object Exceptions extends App {
    println("Exceptions")

    val str: String = null
    // println(str.length) //java.lang.NullPointerException

    import scala.util.{Try, Success, Failure}

    // throwing and catching exceptions
    // throw new NullPointerException

    // 2. How to throw exceptions
    def getInt(withExceptions: Boolean): Int =
        if (withExceptions) throw new RuntimeException("No int for you!")
        else 42
    
    try 
        getInt(true)
    catch 
        case e: RuntimeException => println("Caught One!")
    finally
        println("Cleaning up the mess you just made...")
    
    val try1 = Try(getInt(true))
    println(try1)
    


}


