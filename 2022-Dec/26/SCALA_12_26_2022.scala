object SCALA_12_26_2022 extends App:
    println("12.26.2022")


    // // FP Domain Modeling
    // enum Topping:
    //     case Cheese
    //     case Pepperoni
    //     case Sausage
    //     case Mushrooms
    //     case Onions

    // enum CrustSize:
    //     case Small
    //     case Medium
    //     case Large

    // enum CrustType:
    //     case Regular
    //     case Thin
    //     case Thick

    
    // case class Pizza(
    //     crustSize: CrustSize,
    //     crustType: CrustType,
    //     toppings: Seq[Topping]
    // )

    // case class Order(
    //     pizzas: Seq[Pizza],
    //     customer: Customer
    // )

    // case class Customer(
    //     name: String,
    //     phone: String,
    //     address: Address
    // )

    // case class Address(
    //     street1: String,
    //     street2: Option[String],
    //     city: String,
    //     state: String,
    //     zipCode: String
    // )

    // type Money = BigDecimal
    // // Step 1: Create a Pizza Service Interface
    // trait PizzaServiceInterface:

    //     def addTopping(p: Pizza, t: Topping): Pizza
    //     def removeTopping(p: Pizza, t: Topping): Pizza
    //     def removeAllToppings(p: Pizza): Pizza

    //     def updateCrustSize(p: Pizza, cs: CrustSize): Pizza
    //     def updateCrustType(p: Pizza, ct: CrustType): Pizza

    //     def calculatePizzaPrice(
    //         p: Pizza,
    //         toppingsPrices: Map[Topping, Money],
    //         crustSizePrices: Map[CrustSize, Money],
    //         crustTypePrices: Map[CrustType, Money]    
    //     ): Money

    // // Step 2: Create a Concrete Implementatin of that Interface
    // trait PizzaService extends PizzaServiceInterface:
    //     def addTopping(p: Pizza, t: Topping): Pizza = 
    //         val newToppings = p.toppings :+ t
    //         p.copy(toppings = newToppings)
    //     def removeTopping(p: Pizza, t: Topping): Pizza = ???
    //         // val newToppings = ListUtils.dropFirstMatch(p.toppings, t)
    //         // p.copy(toppings = newToppings)
    //     def removeAllToppings(p: Pizza): Pizza = 
    //         val newToppings = Seq[Topping]()
    //         p.copy(toppings = newToppings)

    //     def updateCrustSize(p: Pizza, cs: CrustSize): Pizza = 
    //         p.copy(crustSize = cs)
    //     def updateCrustType(p: Pizza, ct: CrustType): Pizza = 
    //         p.copy(crustType = ct)
    //     def calculatePizzaPrice(
    //         p: Pizza,
    //         toppingsPrices: Map[Topping, Money],
    //         crustSizePrices: Map[CrustSize, Money],
    //         crustTypePrices: Map[CrustType, Money]    
    //     ): Money = 
    //         // TODO implement real algorithm based on those sequences
    //         val base = BigDecimal(10)
    //         val numToppings = p.toppings.size
    //         val price = base + 1.00 * numToppings
    //         price

        
    // // This Data Access Object would be connected to a database
    // trait PizzaDAOInterface:
    //     def getToppingPrices(): Map[Topping, Money]
    //     def getCrustSizePrices(): Map[Topping, Money]
    //     def getCrustTypePrices(): Map[CrustType, Money]


    
    // trait OrderServiceInterface:
    //     // implementing classes should provide their own database
    //     // that is an instance of PizzaDAOInterface
    //     protected def database: PizzaDAOInterface

    //     def calculateOrderPrice(o: Order): Money

    // // The Implementation of OrderServiceInterface
    // trait AbstractOrderService extends OrderServiceInterface:
    //     // You can't call functions on a trait, so you need to create
    //     // a concrete instance of the trait before you do anything else
    //         // The technique is called "reifying"
    //     object PizzaService extends PizzaService
    //     import PizzaService.calculatePizzaPrice

    //     // all implementations will use these functions:
    //     private lazy val toppingPricesMap = database.getToppingPrices()
    //     private lazy val crustSizePricesMap = database.getCrustSizePrices()
    //     private lazy val crustTypePricesMap = database.getCrustTypePrices()

    //     // the publically available service
    //     def calculateOrderPrice(o: Order): Money =
    //         calculateOrderPriceInternal(
    //             o,
    //             toppingPricesMap,
    //             crustTypePricesMap
    //         )

    //     private def caculateOrderPriceInternal(
    //         o: Order,
    //         toppingsPrices: Map[Topping, Money],
    //         crustSizePrices: Map[CrustSize, Money],
    //         crustTypePrices: Map[CrustType, Money]
    //     ): Money =
    //         val pizzaPrices: Seq[Money] = for {
    //             pizza <- o.pizzas
    //         } yield {
    //             calculatePizzaPrice(
    //                 pizza,
    //                 toppingPrices,
    //                 crustSizePrices,
    //                 crustTypePrices
    //             )
    //         }
    //         pizzaPrices.sum

    
    // // example2
    // import java.awt.Color
    // // Modular Domain Modeling: Animal example
    // trait Animal

    // abstract class AnimalWithTail(tailColor: Color) extends Animal

    // trait DogTailServices:                  // self-type
    //     this: AnimalWithTail =>             // this code ensures that you can only use DogTailServices
    //     def wagTail = println("wagging tail")     // with classes that extend AnimalWithTail
    //     def lowerTail = println("lowering tail")
    //     def raiseTail = println("raising tail")

    // trait DogMouthServices:
    //     this: AnimalWithTail => 
    //     def bark = println("bark!")
    //     def lick = println("licking")

    
    
    // object IrishSetter
    //     extends AnimalWithTail(Color.red)
    //     with DogTailServices
    //     with DogMouthServices


    // IrishSetter.wagTail //wagging tail
          
    // REGEX
    import scala.util.matching.Regex

    val regex1 = raw"Jeff(?=rey)".r //Matches Jeff only if it's part of Jeffrey


    val names = "Sam, Jeff, Jefferson, Robert, Jeffrey"
    val jeffAnchored = regex1.findAllMatchIn(names)
    println(jeffAnchored.toList) //List(Jeff)

    val regex2 = regex1.unanchored

    val jeffUnanchored = regex2.findAllMatchIn(names)
    println(jeffUnanchored.toList) //List(Jeff)

    // Regex Non capuring paretheses (?: ...)
    // it groups the chars of a regex but doesn't create a "group"
    // variable, but can still make the chars within the parentheses
    // optional, such as raw"(?:Hello)?\s*World".r

    val r3 = raw"(?:Hello)?\s*World".r
    println(r3.matches("World")) //true
    // But note: we then can use the method group() to pull that variable

    // (?: ...)  --> group but dont capture
    // (?= ...)  --> look-ahead
    // (?<= ...)  --> look-behind

    // val r4 = raw"(?<= 4\.) Sam".r
    // println(r4.matches("Sam"))
    // println(r4.matches("4. Sam"))

    // Lazy Evaluation

    lazy val x = {
        println("Hello")
        42
    }
    println(x)
    println(x)

    // lazy vals are only evaluated when they are used
    // but if the lazy val is used again, it uses the same
    // cached evaluation from the first time

    def sideEffectCondition: Boolean = 
        println("Boo")
        true
    
    def simpleCondition: Boolean = false
    
    lazy val lazyCondition = sideEffectCondition

    println(if (simpleCondition && lazyCondition) then "yes" else "no") // no

    // in conjunction with call-by-name
    def byNameMethod(n: => Int): Int = n + n + n + 1


    def byValueMethod(n: Int): Int = n + n + n + 1

    // The below technique is called "CALL BY NEED"
    def byNameLazyVal(n: => Int): Int =
        lazy val t = n
        t + t + t + 1 

    def retrieveMagicValue: Int = 
        //Thread.sleep(1000)
        42
    

    println(byNameMethod(retrieveMagicValue))
    println(byValueMethod(retrieveMagicValue))

    // lazy vals with filtering
    def lessThan30(i: Int): Boolean = 
        println(s"$i is less than 30")
        i < 30

    def greaterThan20(i: Int): Boolean = 
        println(s"$i is greater than 20")
        i > 20

    val numbers = List(1, 25, 40, 5, 23)
    val lt30 = numbers.filter(lessThan30)
    val gt20 = lt30.filter(greaterThan20)
    println(gt20)
    println()

    val lt30lazy = numbers.withFilter(lessThan30)
    val gt20lazy = lt30lazy.withFilter(greaterThan20)
    println(gt20lazy)

    import scala.io.Source
    
    class FileReader(filename: String):
        lazy val text =
            val fileContents =
                try 
                    Source.fromFile(filename).getLines.mkString
                catch
                    case e: Exception => e.getMessage
            println(fileContents)
            fileContents
        
    // we use lazy val here, because since the "text" val is in the
    // class constructor, that code block will run on initialization,
    // and we probably dont want that, so use lazy vals when you don't
    // want a val to be evaluated until its used.
    // Note: lazy vals are only evaluated ONCE, that value is memoized
    // and provided regardless of how many times the value is called or
    // referenced.
    import scala.util.{Try, Success, Failure}

    def toInt(s: String): Try[Int] =
        Try(s.toInt)

    println(toInt("o"))
    println(toInt("4"))

    object Control {
        import reflect.Selectable.reflectiveSelectable
        def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
            try {
                f(resource)
            } finally {
                resource.close()
            }
    }

    type IO[A] = Try[A]

    trait Vehicle:
        type T
        def vehiclemodel(vm: T): T
        val color : T	
    

    class Mercedes extends Vehicle:
        type T = String 
        def vehiclemodel(vm: String) = vm
        val color = "grey"

    trait Buffer:
        type T
        def element: T

    abstract class SeqBuffer extends Buffer:
        type U
        type T <: Seq[U]
        def length = element.length

    // FP Lenses

    case class User(
        id: Int,
        name: Name,
        billingInfo: BillingInfo,
        phone: String,
        email: String
    )

    case class Name(
        firstName: String,
        lastName: String
    )

    case class Address(
        street1: String,
        street2: String,
        city: String,
        state: String,
        zip: String
    )

    case class CreditCard(
        name: Name,
        number: String,
        month: Int,
        year: Int,
        cvv: String
    )

    case class BillingInfo(
        creditCards: Seq[CreditCard]
    )

    val user = User(
        id = 1,
        name = Name(
            firstName = "Al",
            lastName = "Alexander"
        ),
        billingInfo = BillingInfo(
            creditCards = Seq(
                CreditCard(
                    name = Name("John", "Doe"),
                    number = "1111111111111111",
                    month = 3,
                    year = 2020,
                    cvv = ""
                )
            )
        ),
        phone = "907-555-1212",
        email = "al@al.com"
        )

    
    
    
    
    
                    















    
    