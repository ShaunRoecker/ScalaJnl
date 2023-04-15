object Kata extends App {  

   val adder = (x: Int, y: Int) => x + y
   println(adder(10, 5))

   case class Person(name: String)

   def p[A](a: A) = println{ Console.CYAN + a + Console.RESET }

   p[String]("printString")

   p[Person](Person("John"))

   def multiplier(x: Int, y: Int) = x * y

   val multiply2 = multiplier(2, _: Int)
   p[Int](multiply2(2))  // 4

   def multiply3val(a: Int, b: Int, c: Int) = a * b * c

   val multi2And = multiply3val(2, _, _)
   p[Int](multi2And(2, 2)) // 8

   val multiply3valCurried = (multiply3val _).curried
   p[Int](multiply3valCurried(10)(10)(10)) // 1000

   p[Int => Int]((x: Int) => x + 10) // Kata$$$Lambda$10/0x00000008000f2840@687080dc

   def exM(a: Int)(b: Int)(c: Int) = a * b * c

   p[Int](exM(10)(10)(10)) // 1000

   val exM2 = exM(2)(10) _
   p[Int](exM2(10)) // 200

   def capitalAll(strs: String*) = 
      strs.map(_.capitalize)

   // can use the ':_*' syntax after a Seq, to imput the String* values as a Seq
   // method will return the same functor you use
   println(capitalAll(List("a", "b", "c", "d", "e", "f"):_*)) // List(A, B, C, D, E, F)

   // otherwise have to do this...
   println(capitalAll("a", "b", "c", "d", "e", "f")) // ArraySeq(A, B, C, D, E, F)

   class Calculator(val brand: String):
      val color: String = 
         if (brand == "TI") "blue"
         else if (brand == "HP") "black"
         else "white"

      def add(m: Int, n: Int): Int = m + n
   
   // regular classes need an unapply method to be used in pattern matching
   object Calculator:
      def unapply(calc: Calculator): Option[String] =
         Some(calc.brand)

   // case classes come with an unapply method out-of-the-box
   case class Computer(cpu: Int)

   def calcComMatcher(device: Calculator | Computer) = 
      device match 
         case com: Computer => println(s"It's a computer with ${com.cpu} cores")
         case calc: Calculator => println(s"It's a ${calc.brand} calculator")
      
   val calculator = new Calculator("Texas Instruments") // It's a Texas Instruments calculator
   val computer = Computer(8) // It's a computer with 8 cores

   calcComMatcher(calculator)
   calcComMatcher(computer)

   // inheritance
   class ScientificCalculator(brand: String) extends Calculator(brand) {
      def log(m: Double, base: Double) = math.log(m) / math.log(base)
      // method overloading..
      def log(m: Int): Double = log(m, math.exp(1))
      // however default method parameters are more concise with the same result...
      def logDefault(m: Double, base: Double = 2): Double = math.log(m) / math.log(base)
   }


   val sciCalc = new ScientificCalculator("TI")
   println(sciCalc.log(10, 2)) // 3.3219280948873626
   println(sciCalc.logDefault(10, 2)) //  3.3219280948873626
   println(sciCalc.logDefault(10)) //  3.3219280948873626

   // Classes can also extend Function and those instances can be called with ().
   class AddOne extends Function1[Int, Int] {
      def apply(m: Int): Int = m + 1
   }

   val plusOne = new AddOne()
   println(plusOne(1)) //2

   class CantDivZero extends PartialFunction[Int, Int]:
      def apply(x: Int): Int = 42 / x
      def isDefinedAt(x: Int): Boolean = x != 0

   val zeroPartial = new CantDivZero()

   println(zeroPartial(2)) // 21

   println(List(1, 2, 0, 1, 2).collect(zeroPartial)) // List(42, 21, 42, 21)
   
   import scala.annotation.tailrec
   def product(list: List[Int]): Int =
      @tailrec
      def productRec(xs: List[Int], acc: Int): Int =
         xs match
            case Nil => acc
            case x :: xs => productRec(xs, acc * x)
      productRec(list, 1)

   println(product(List(1, 2, 3, 4))) // 25

   




}
