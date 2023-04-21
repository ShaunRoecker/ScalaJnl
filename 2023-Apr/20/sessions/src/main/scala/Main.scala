import scala.annotation.tailrec
object Main1:
    
    def multiTable(n: Int): List[List[Int]] =
        List.tabulate(n, n){ (x, y) => (x + 1) * (y + 1) }

    println(multiTable(3))


    class Coffee:
        val price: Double = 1.00d

    class CreditCard:
        def charge(cf: Double): Unit = 
            println(cf)
            
    
    class Cafe:
        def buyCoffee(cc: CreditCard): Coffee = {
            val cup = new Coffee()
            cc.charge(cup.price)
            cup
        }
        
    class Self { self =>
        def printSelf = println(self)
    }
    val self = new Self
    self.printSelf

    class Payments:
        def charge(card: CreditCard, cf: Double): Unit = 
            println(cf)
    
    class Cafe2 {
        def buyCoffee(cc: CreditCard, p: Payments): Coffee =
            val cup = new Coffee()
            p.charge(cc, cup.price)
            cup

    }

    case class Charge(cc: CreditCard, amount: Double) {	self =>

        def combine(other: Charge): Charge =
            if (cc == other.cc)	
                Charge(cc, self.amount + other.amount)	
            else
                throw new Exception("Can't combine charges to different cards")	

    }

    // class Cafe {
    //     def buyCoffee(cc: CreditCard): (Coffee, Charge) = ???

    //     def buyCoffees(cc: CreditCard, n: Int): (List[Coffee], Charge) = {	
    //         val purchases: List[(Coffee, Charge)] = List.fill(n)(buyCoffee(cc))	
    //         val (coffees, charges) = purchases.unzip	
    //         (coffees, charges.reduce((c1,c2) => c1.combine(c2)))	
    //     }
    // }

//     def coalesce(charges: List[Charge]): List[Charge] =
//       charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList

    println(List((1, "a"), (2, "b"), (3, "c")).unzip)
    // (List(1, 2, 3),List(a, b, c))


    println(List.fill(5)("a")) // List(a, a, a, a, a)


    val list = List(1, 2, 3, 4, 5, 6)

    def numToAlpha(x: Int): String =
        x match
            case 1 => "A"
            case 2 => "B"
            case _ => "C"

    val stringList = list.map(numToAlpha)
    println(stringList) // List(A, B, C, C, C, C)

    
    def sum(list: List[Int]): Int =
        @tailrec
        def loop(xs: List[Int], acc: Int): Int =
            if (xs.isEmpty) acc
            else loop(xs.tail, acc + xs.head)
        loop(list, 0)

    println(sum(List(1, 2, 3))) // 6

    def notEmptyWithA(list: List[String]): Boolean =
        !list.isEmpty &&
        list.map(_.toUpperCase).contains("A")

    println(notEmptyWithA(List("b", "a", "c"))) // true
    println(notEmptyWithA(List()))              // false
    println(notEmptyWithA(List("b", "c")))      // false


object Main2 {

    def abs(n: Int): Int =
        if (n < 0) -n
        else n

    private def formatAbs(x: Int): String = {
        val msg = "The absolute value of %d is %d"
        msg.format(x, abs(x))
    }

    private def formatFactorial(x: Int): String = {
        val msg = "The factorial of %d is %d"
        msg.format(x, factorial(x))
    }

    private def factorial(n: Int): Int =
        @tailrec
        def loop(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else loop(n - 1, n * acc)
        loop(n, 1)

    private def findFirst(ss: Array[String], key: String): Int = {
        @tailrec
        def loop(n: Int): Int =
            if (n >= ss.length) -1
            else if (ss(n) == key) n
            else loop(n + 1)
        loop(0)
    }

    private def findFirstPolymorphic[A](as: Array[A], p: A => Boolean): Int = {
        @tailrec
        def loop(n: Int): Int =
            if (n >= as.length) -1
            else if (p(as(n))) n
            else loop(n + 1)
        loop(0)
    }

    private def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
        @tailrec
        def loop(n: Int): Boolean =
            if (n == as.length - 1) ordered(as(n - 1), as(n))
            else if (!ordered(as(n - 1), as(n))) false
            else loop(n + 1)
        if (as.size == 0 || as.size == 1) true
        else loop(1)
    }
    
    private val equal = (x: Int, y: Int) => x == y

    private val lessThan = new Function2[Int, Int, Boolean] {
        def apply(x: Int, y: Int): Boolean = x < y
    }

    val lessThanApply = lessThan.apply(10, 20)

    def partial1[A, B, C](a: A, f: (A, B) => C): B => C = 
        (b: B) => f(a, b)

    val partial = ()

    def main(args: Array[String]): Unit =
        println(formatAbs(-42)) // The absolute value of -42 is 42
        println(formatFactorial(3)) // The factorial of 3 is 6
        println(findFirst(Array("a", "b", "c", "b"), "b")) // 1
        println(findFirstPolymorphic(Array("a", "b", "c", "b"), _ == "b")) // 1


}



        
    



