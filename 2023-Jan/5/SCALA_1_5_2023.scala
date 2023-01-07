object SCALA_1_5_2023 extends App:
    // Regex Daily
    import scala.util.matching.Regex
    // A very powerful regex for date:
    val dateRegex =  """^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)(?:0?2|(?:Feb))\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$""".r

    def YYYYMMDDConverter(d: String): String =
        val yyyymmdd = """([12][90]\d\d)(\d{2})(\d{2})""".r
        d match
            case yyyymmdd(year, month, day) => s"$month/$day/$year"
            case _ => d

    println(YYYYMMDDConverter("20230105")) //01/05/2023
    

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Scala's type system".toUpperCase)
    println()
     
    class Animal
    class Dog extends Animal:
        def speak() = println("woof")
    class Cat extends Animal:
        def speak() = println("meow")

    // 
    class AnimalCollection:
        type AnimalType
                // BoundedAnimal is a Subtype of Animal
        type BoundedAnimal <: Animal // abstract type member upper bounded with Animal
                // SuperAnimal is a Supertype of Animal
        type SuperAnimal >: Animal // abstract type member lower bounded with Animal

        type SuperBoundedAnimal >: Dog <: Animal

        type AnimalC = Cat
        // You can create type aliases for complex types for concise code
        type Cats = List[Vector[Cat]]
        def useCats(cats: Cats): Cats = cats
        // vs. def useCats(cats: List[Vector[Cat]]): List[Vector[Cat]] = cats

     
    val ac = new AnimalCollection
    val dog: ac.SuperBoundedAnimal =  new Dog
     

    // API Design: Below is an alternative to Generics
    trait MyList:
        type T
        def add(element: T): MyList

    class NonEmptyList(value: Int) extends MyList:
        override type T = Int
        def add(element: Int): MyList = ???

    
    // Another way to Type Alias:
    // type CatsType = cat.type
    // val newCat: CatsType = cat // can do this
    // new CatsType // but cant create a new type instance this way

    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("inner types and path dependent types".toUpperCase)
    println()

    class Outer:
        class Inner
        object InnerObject
        type InnerType

    // can define classes almost anywhere, including inside methods
    // def aMethod: Int = 
    //     class HelperClass
    //     type HelperType = String // even type aliases, however you do have to define them
    

    sealed trait CrewMember
    sealed trait StarFleetTrained
    class Officer extends CrewMember with StarFleetTrained
    trait Captain


    // class Crew[A <: CrewMember]
    class Crew[A <: CrewMember & StarFleetTrained]

    val kirk = new Officer with Captain

    val officers = Crew[Officer & StarFleetTrained]()

    // Using Duck Typing (Structural Types)
    // This is how you require that a method uses an object that has
    // certain other method

    import reflect.Selectable.reflectiveSelectable

    def callSpeak[A <: {def speak(): Unit}](obj: A): Unit = obj.speak()

    callSpeak(Dog()) //woof
    callSpeak(Cat()) //meow

    // Opaque Types (For Domain-Driven Design): Giving type names to
    // simple types like String and Int to make your code safer

    object DomainObjects:
        opaque type CustomerId = Int
        object CustomerId:
            def apply(i: Int): CustomerId = i
        given CanEqual[CustomerId, CustomerId] = CanEqual.derived

        opaque type ProductId = Int
        object ProductId:
            def apply(i: Int): ProductId = i
        given CanEqual[ProductId, ProductId] = CanEqual.derived

    // This lets you write code like this:
    import DomainObjects.*

    val customerId = CustomerId(101)
    val productId = ProductId(101)

    def addToCart(customerId: CustomerId, productId: ProductId) = ???

    // Using Term Inference with Given and Using

    import scala.util.{Try, Success, Failure}

    trait Adder[T]:
        def add(a: T, b: T): T
    
    object AdderGivens:
        given intAdder: Adder[Int] with
            def add(a: Int, b: Int): Int = a + b

        given stringAdder: Adder[String] with
            def add(a: String, b: String): String = 
                Try(a.toInt + b.toInt) match 
                    case Success(x) => x.toString
                    case Failure(e) => "0"


    import AdderGivens.given
    // make sure to put GivenObject.given in order to import the givens
    // GivenObject.* won't import the givens

    // to import individual givens:
    import AdderGivens.{intAdder, stringAdder}

    def genericAdder[T](x: T, y: T)(using adder: Adder[T]): T = adder.add(x, y)

    println(genericAdder(1, 2))   //3
    println(genericAdder("1", "2"))   //3
    println(genericAdder("1", "two"))   //0

    // the way that using clauses are used like implicit values in Scala 2
    // is known as a context parameter in the broader programming industry

    // Also make sure that all the givens you are using are in current
    // scope

    // Anonymous Givens and Unnamed Parameters
    given Adder[Double] with
        def truncateAt(n: Double, p: Int): Double = 
            val s = math.pow(10, p)
            (math.floor(n * s)) / s 
        def add(a: Double, b: Double): Double = truncateAt((a + b), 2)

    println(genericAdder(1.0045, 2.0001)) //3.0

    // Extension Methods
    trait Math[T]:
        def add(a: T, b: T): T
        def subtract(a: T, b: T): T
        // extension methods: create your own api
        extension (a: T)
            def + (b: T) = add(a, b)
            def - (b: T) = subtract(a, b)

        
    given intMath: Math[Int] with
        def add(a: Int, b: Int): Int = a + b
        def subtract(a: Int, b: Int): Int = a - b

    
    given stringMath: Math[String] with
        def add(a: String, b: String): String =   
            Try(a.toInt + b.toInt) match 
                case Success(x) => x.toString
                case Failure(e) => "0"

        def subtract(a: String, b: String): String = 
            Try(a.toInt - b.toInt) match 
                case Success(x) => x.toString
                case Failure(e) => "0"
    

    // Then you can create genericAdd and genericSubtract functions:
    
    // `+` and `-` here refer to the extension methods
    def genericAdd[T](x: T, y: T)(using Math: Math[T]): T = x + y
    def genericSubtract[T](x: T, y: T)(using Math: Math[T]): T = x - y

    println("add ints:        " + genericAdd(1, 1))
    println("subtract ints:   " + genericSubtract(1, 1))

    // add ints:        2
    // subtract ints:   0

    // Alias Givens
    // "an alias can be used to define a given instance that is equal
    // to some expression"

    enum Context:
        case Food, Life

    import Context.*

    def search(s: String)(using ctx: Context): String = ctx match
        case Food =>
            s.toUpperCase match
                case "DATE" => "like a big raisin"
                case "FOIL" => "wrap food in foil before baking"
                case _      => "something else"
        case Life => 
            s.toUpperCase match 
                case "DATE" => "like going out to dinner"
                case "FOIL" => "argh, foiled again!" 
                case _ => "something else"

    given foodContext: Context = Food

    val date = search("date")
    // val date = search("date")(using Food)
    println(date)

    // import Adder.{given Adder[_]}

    // given import docs: https://oreil.ly/hnsz6

    



    

    












