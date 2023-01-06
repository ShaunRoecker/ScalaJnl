object SCALA_1_4_2023:
    println()


    class Animal
    class Dog extends Animal
    class Cat extends Animal
    class Cage
    // variance

    // covariance: +T, this is saying that a Container[Super] will accept a Container[Child]

    // invariance: T, this is saying that a Container[Super] will ONLY accept a Container[Super]

    // contravariance: -T, this is saying that a Container[Child] will ONLY accept a Container[Super]

    // So if a Cat inherits from Animal, does a Container[Cat] ALSO inherit from Container[Animal]??
    // 3 Possible Answers:

    ///////////////////////////////////////////////////////////////////////////////////
    // Answer 1: Yes, this is OK with COVARIANCE [+T]
    class CovCage[+T]
    val ccage: CovCage[Animal] = new CovCage[Cat] // this works with COVARIANCE [+T]

    ///////////////////////////////////////////////////////////////////////////////////
    // Answer 2: No, you can't do this with INVARIANCE [T], 
            // A Cage[Animal] must be a Cage[Animal], or a Cage[Cat] must be a Cage[Cat]

    class InvCage[T]
    // val invCage: InvCage[Animal] = new InvCage[Cat]  <-- Neither of these are acceptable
    // val invCage: InvCage[Cat] = new InvCage[Animal]        with INVARIANCE

    ///////////////////////////////////////////////////////////////////////////////////
    class ContraCage[-T]
    val contraCage: ContraCage[Cat] = new ContraCage[Animal]


    // Think of Inheritance as on the X-axis: Animal --> Cat --> Kitten
                // And the [+T] means you can go forward
                // the [T] is Zero, you can't move
                // And the [-T] means you can go Backward

    

    class InvariantCage[T](val animal: T) //invariant position

    class CovariantCage[+T](val animal: T) //covariant position

    // class ContravariantCage[-T](val animal: T) // <- wont compile
    //    error: contravariant type T occurs in covariant position in type T of value animal
    // if that compiled... 
    // val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)


    // same logic applies here:
    // class CovariantVariableCage[+T](var animal: T) // <- wont compile
        // error: ovariant type T occurs in contravariant position in type T of parameter animal_=
    // if that compiled...
    //val ccage: CCage[Animal] = new CCage[Cat](new Cat) <- would be fine
    // ccage.animal = new Dog  // <- this would be a problem


    // ex
    class AnotherContravariantCage[-T]:
        def addAnimal(animal: T) = true// Contravariant position

    val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
    acc.addAnimal(new Cat)

    class Kitty extends Cat
    acc.addAnimal(new Kitty)

    class MyList[+A]:
        def add[B >: A](element: B): MyList[B] = new MyList[B] //widening the type

    // With this signature^^, the compile will widen or expand the type
    // so that all the elements are of a common parent type
    val emptyList/*: MyList[Kitty]*/ = new MyList[Kitty]
    val animals/*: MyList[Kitty]*/ = emptyList.add(new Kitty)
    val moreAnimals/*: MyList[Cat]*/ = animals.add(new Cat)
    val evenMoreAnimals/*: MyList[Animal]*/ = moreAnimals.add(new Dog)

    // The goal is to make it to where all of the elements of MyList have
    // a common type

    // NOTE: method arguments are in contravariant position


    class PetShop[-T]:  // S == Subtype of T
        def get[S <: T](isItAPuppy: Boolean, defaultAnimal: S): S = 
            defaultAnimal // narrowing the type
    
    val shop: PetShop[Dog] = new PetShop[Animal]
    // val evilCat = shop.get(true, new Cat)
    class TerraNova extends Dog
    val bigFurry = shop.get(true, new TerraNova)

    // Big Rule: 
        // - METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION
        // -RETURN TYPES ARE IN COVARIANT POSITION

    // trick to test Inheritance
    println(implicitly[TerraNova <:< Dog]) //generalized constraint
    //implicitly[PetShop[TerraNova] <:< PetShop[Dog]] //error

    // ex
    class Vehicle
    class Bike extends Vehicle
    class Car extends Vehicle
    class IList[T]

    case class InvariantParking[T](vehicles: List[T]):
        def park(vehicle: T): InvariantParking[T] = 
            val newList = vehicles.prepended(vehicle)
            this.copy(vehicles = newList)

        def impound(vehicle: List[T]): InvariantParking[T] = ??? 
        def checkVehicles(conditions: String): List[T] = ???


    class CovariantParking[+T](vehicles: List[T]):
        def park[S >: T](vehicle: S): CovariantParking[S] = ???
        def impound[S >: T](vehicles: List[S]): CovariantParking[T] = ???
        def checkVehicles(conditions: String): List[T] = ???


    class ContravariantParking[-T](vehicles: List[T]):
        def park(vehicle: T): ContravariantParking[T] = ???
        def impound(vehicles: List[T]): ContravariantParking[T] = ???
        def checkVehicles[S <: T](conditions: String): List[S] = ???



    val iPark = new InvariantParking[String](List("v1", "v2"))
    val iPark2 = iPark.park("v3")
    println(iPark2)


object TypeMembers extends App:

    class Animal
    class Dog extends Animal
    class Cat extends Animal

    class AnimalCollection:
        type TypeAnimal  // abstract type member
        type BoundedAnimal <: Animal 
        type SuperBoundedAnimal >: Dog <: Animal

    val ac = new AnimalCollection
    val dog: ac.AnimalType = ???
    val cat: ac.BoundedAnimal = new Cat
    




    

    





    

    
