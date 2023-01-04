object SCALA_1_3_2023: 
    println("Regex Daily")
    //^((19|20)\d\d)[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$ 
    // matches a date in yyyy-mm-dd format from 1900-01-01 through 2099-12-31, 
    // with a choice of four separators.

    val regexYYYYmmdd = """^(?<YYYY>(19|20)\d\d)[- /.](?<MM>0[1-9]|1[012])[- /.](?<DD>0[1-9]|[12][0-9]|3[01])$""".r

    val regexmmddYYYY = 
        """^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.]((19|20)\d\d)$""".r

    val dateString = "1967/02/20"
    val date = dateString match
        case regexYYYYmmdd(year, _, month, day) => s"$month/$day/$year"
        // case regexmmddYYYY(month, day, year, _) => s"$month/$day/$year"
        case _ => "blank"

    println(date)


    val regex2 = """\b(\d{4})(0[1-9]|1[0-2])(0[1-9]|[12]\d|30|31)\b""".r


    val dateString2 = "3/12/2000"
    val rDateDDMMYYYY = 
        """(?<DD>[0-3]?[0-9])[-/.](?<MM>[01]?[0-9])[-/.](?<YYYY>[0-2][0-9][0-9][0-9])""".r

    val date2 = dateString2 match
        case rDateDDMMYYYY(day, month, year) => s"$dateString2 => $day-$month-$year"
        case _ => s"Not Found"

    println(date2)
    
    def dateMatcher(date: String, format: String): String = 
        ???

    val partialFunction = new PartialFunction[Double, Double] {
        def roundAt(p: Int)(n: Double): Double = { 
            val s = math pow (10, p) 
            (math round n * s) / s 
        }
        def truncateAt(n: Double, p: Int): Double = {
            val s = math pow (10, p)
            (math floor n * s) / s 
        }
        def apply(x: Double): Double = 
            truncateAt((43 / x), 2)
        def isDefinedAt(x: Double): Boolean = x != 0
    }
     
    val aListWithZero: List[Double] = List(1, 0, 7, 3) 
    println(aListWithZero.collect(partialFunction))

    // Givens and the Using Clause
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Givens and Using".toUpperCase)
    println()

    val aList = List(2,4,3,1)

    object Implicits:
        implicit val descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)

    // import Implicits._

    val anOrderedList = aList.sorted
    println(anOrderedList)

    // Scala 3 style
    // as long as the given is in the same scope as the method that is searching
    // for it, it doesn't matter in what order the two are placed (the given can
    // be after the method)
    object Givens:
        given descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)

    // Instantiating an Anonymous Class
    object GivenAnonymousClassNaive:
        given descendingOrderingV2: Ordering[Int] = new Ordering[Int] {
            override def compare(x: Int, y: Int) = y - x // descending order
        }

    object GivenWith:
        given descendingOrderingV3: Ordering[Int] with {
                override def compare(x: Int, y: Int) = y - x 
        }

    // import GivenWith._ // in Scala 3, this import does not import givens as well
    
    // import GivenWith.given // in Scala 3, this imports all the givens in that object
    import GivenWith.descendingOrderingV3  // import a specific given

    // implicit arguments are now using clauses in Scala 3
    // method to return the largest and the smallest values from a List
    def extremes[A](list: List[A])(using ordering: Ordering[A]): (A, A) = 
        val sortedList = list.sorted
        (sortedList.head, sortedList.last)

    
    // implicit def (synthesize new implicit values)
    // Here we are creating a given (would be implicit def) to generate an ordering that 
    // allows us to order a container of a certain type, using the ordering for the given type
    // ex: we are taking an Ordering for Int and creating a new ordering for List[Int]
    trait Combinator[A]:
        def combine(x: A, y: A): A

    object IntCombinator extends Combinator[Int]:
        def combine(x: Int, y: Int): Int = x + y

    given listOrdering[A](using simpleOrdering: Ordering[A], combinator: Combinator[A]): 
        Ordering[List[A]] with {
            override def compare(x: List[A], y: List[A]) = {
                val sumX = x.reduce(combinator.combine)
                val sumY = y.reduce(combinator.combine)
                simpleOrdering.compare(sumX, sumY)
            }
    }

    

    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)

    val listOfList = List(list2, list1)

    println(listOfList.sorted)

    case class Person(name: String):
        def greet(): String = s"Hi, I'm ${name}."

    //implicit conversions in Scala 3
    import scala.language.implicitConversions
    given string2PersonConversion: Conversion[String, Person] with {
        override def apply(x: String) = Person(x)
    }

    // Extension Methods
    extension(string: String){
        def greetAsPerson(): String = Person(string).greet()
    }

    val violetsGreet = "Violet".greetAsPerson()
    println(violetsGreet)

    // Extension methods are equivalent to implicit classes in Scala 2

    // generic extensions
    extension[T](list: List[T]) {
        def ends: (T, T) = (list.head, list.last)
        // def extremes(using ordering: Ordering[T]): (T, T) =
        //     list.sorted.ends

    }

object AdvancedTypes:
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("advanced types".toUpperCase)
    println()

    // convenience
    trait Writer[T]:
        def write(value: T): Unit

    trait Closeable:
        def close(status: Int): Unit

    trait GenericStream[T]:
        def foreach(f: T => Unit): Unit

    def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit =
        stream.foreach(println)
        stream.close(status = 0)

    // diamond problem
    trait Animal:
        def name: String

    trait Lion extends Animal:
        override def name: String = "lion"

    trait Tiger extends Animal:
        override def name: String = "tiger"

    class Mutant extends Lion with Tiger

    val mutant = new Mutant
    println(mutant.name)

    // the super problem + type linearization
    trait Cold:
        def print(): Unit = println("cold")

    trait Green extends Cold:
        override def print(): Unit = 
            println("green")
            super.print()

    trait Blue extends Cold:
        override def print(): Unit = 
            println("blue")
            super.print()

    trait Red extends Cold:
        override def print(): Unit = println("red")

    class White extends Red with Green with Blue:
        override def print(): Unit = 
            println("white")
            super.print()

    val white = new White
    // white.print()
    // white
    // blue
    // green
    // red


object Variance extends App: 

    trait Animal
    class Dog extends Animal
    class Cat extends Animal
    class Crocodile extends Animal

    class Cage[T]

    // covariance
    class CovCage[+T]
    val covCage: CovCage[Animal] = new CovCage[Cat]

    // invariance
    class InvCage[T]
    val invCage: InvCage[Animal] = new InvCage[Animal] 
    // ^^ can't be anything other than InvCage[Animal] ^^

    // contravariance
    class ContraCage[-T]
    val contraCage: ContraCage[Cat] = new ContraCage[Animal]


    class InvariantCage[T](animal: T) // invariant

    // covariant positions

    class CovariantCage[+T](val animal: T) // covariant position

    // contravariant positions

    // class ContravariantCage[-T](val animal: T) //

    // trait AnotherCovariantCage[+T]:
    //     def addAnimal(animal: T) // Contravariant position

    class AnotherContravariantCage[-T]:
        def addAnimal(animal: T) = true

    val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
    acc.addAnimal(new Cat)

    class Kitty extends Cat
    acc.addAnimal(new Kitty)

    class MyList[+A]:
        // B is a SuperType of A
        def add[B >: A](element: B): MyList[B] = new MyList[B] //widening the type

    class MyList2[+Cat]:
        // B is a SuperType of A
        def add[Animal >: Cat](element: Animal): MyList[Animal] = new MyList[Animal]

    class MyList3[+Kitty]:
        // B is a SuperType of A
        def add[Cat >: Kitty](element: Cat): MyList[Cat] = new MyList[Cat]

    val emptyList = new MyList[Kitty]
    val animals = emptyList.add(new Kitty)
    val moreAnimals = animals.add(new Cat) // becomes a List[Cat]
    val evenMoreAnimals = moreAnimals.add(new Dog)
    // Since our list is now a List[Cat], and we widened with [B >: A] ([Animal >: Cat]),
    // we can now add a Dog, because a Dog is an Animal

    // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION

















    
