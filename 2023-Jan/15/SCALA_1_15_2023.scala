object SCALA_1_15_2023 extends App:
    import java.util.{Date, Calendar}
    type Amount = BigDecimal
    // monoids
    enum TransactionType:
        case CR, DR

    enum Currency:
        case USD, EUR, JPY, AUD

    case class Money(m: Map[Currency, Amount]):
        def toBaseCurrency: Amount = ???

    // In reality, we would have types for all the parameters
    // in Transaction
    case class Transaction(
        txid: String, 
        accountNo: String, 
        date: Date,
        amount: Money,
        txnType: TransactionType,
        status: Boolean
    )

    case class Balance(b: Money)

    // Now lets add a module for functionality
    trait Analytics[Transaction, Balance, Money]:
        def maxDebitOnDay(txns: List[Transaction]): Money
        def sumBalances(bs: List[Balance]): Money

    object Analytics extends Analytics[Transaction, Balance, Money]:
        def maxDebitOnDay(txns: List[Transaction]): Money = ???
        def sumBalances(bs: List[Balance]): Money = ???
    

    trait Monoid[T]:
        def zero: T
        def op(x: T, y: T): T

    // object Monoids:

    //     given MapMonoid: Monoid[Map[K, V]] with
    //         def zero = Map.empty[K, V]
    //         def op(m1: Map[K, V], m2: Map[K, V]) = m2.foldLeft(m1) { (a, e) =>
    //             val (key, value) = e
    //             a.get(key).map(v => 
    //                 a + ((key, implicitly[Monoid[V]].op(v, value)))).getOrElse(a + ((key, value))
    //             )
    //         }

    //     final val zeroMoney: Money = Money(Monoid[Map[Currency, Amount]].zero)
    //     given moneyAdditionMonoid: Monoid[Money] with
    //         val m = implicitly[Monoid[Map[Currency, Amount]]]
    //         def zero = zeroMoney
    //         def op(m1: Money, m2: Money): Money = Money(m.op(m1.m, m2.m))

   
          
    
    import scala.util.Sorting
    import scala.math.PartialOrdering._
    val pairs = Array(("a", 5, 2), ("c", 3, 1), ("b", 1, 3))

    // sort by 2nd element
    Sorting.quickSort(pairs)(Ordering.by[(String, Int, Int), Int](_._2))

    // sort by the 3rd element, then 1st
    Sorting.quickSort(pairs)(Ordering[(Int, String)].on(x => (x._3, x._1)))
    
    import scala.annotation.tailrec
    def sum(list: List[Int]): Int =
        @tailrec
        def sumrec(list: List[Int], acc: Int): Int = list match 
            case Nil => acc
            case x :: xs => sumrec(xs, acc + x)
        sumrec(list, 0)

    val list = List(1, 2, 3, 4)
    println(sum(list)) 

    def max(list: List[Int]): Int =
        @tailrec
        def maxrec(list: List[Int], max: Int): Int = list match 
            case Nil => max
            case x :: xs => {
                val newMax = if (x > max) x else max
                maxrec(xs, newMax)
            }
        maxrec(list, 0)

    println(max(list))

    case class Person(name: String, age: Int)
    val people = List(
        Person("John", 34),
        Person("Sarah", 23),
        Person("Frank", 45)
    )

    object PersonOrderings:
        given personNameOrdering[A <: Person]: Ordering[A] with
            def compare(p1: A, p2: A) = 
                if (p1.name < p2.name) then -1
                else if (p1.name > p2.name) then 1 else 0

        given personAgeOrdering: Ordering[Person] with
            def compare(p1: Person, p2: Person) = 
                if (p1.age > p2.age) then -1
                else if (p1.age < p2.age) then 1 else 0

        


    import PersonOrderings.given

    println(people.sorted(personNameOrdering))

    



    



    
    