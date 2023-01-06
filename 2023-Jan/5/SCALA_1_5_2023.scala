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
    class Dog extends Animal
    class Cat extends Animal

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

    





