object Kata extends App {  
   import scala.annotation.tailrec

   println("HashCode".hashCode())

   def digitalRoot(n: Int): Int = 

      n.toString.map(_.asDigit).sum 

   println(digitalRoot(16)) //6

   def digitalRoot2(n: Int): Int =
      val x = n.toString.map(_.asDigit).sum
      if (x < 10) x
      else digitalRoot2(x)
      
   println(digitalRoot2(132189)) //6

   def digitalRootMostConcise(n: Int): Int =
      (n - 1) % 9 + 1

   //Write a function that accepts an array of 10 integers (between 0 and 9), 
   // that returns a string of those numbers in the form of a phone number.

   def createPhoneNumber(numbers: Seq[Int]): String = {
      List(
         numbers.take(3).mkString("(", "", ")"), " ",
         numbers.slice(3, 6).mkString, "-",
         numbers.drop(6).mkString
      ).mkString
   }

   println(createPhoneNumber(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)))

   def createPhoneNumberBetter(numbers: Seq[Int]) = {
    "(%d%d%d) %d%d%d-%d%d%d%d".format(numbers:_*)
  }

//   Create a list of Ints from a string representation of a 
//   phone number, with/without dashes/parenthesis
         // "(727) 889-0872" or "727-333-3333" or "9999999999"

   def phoneString(str: String): List[Int] =
      str.toList.filter(_.isDigit).map(_.toInt - 48)

   println(phoneString("(923) 667-3345")) // List(9, 2, 3, 6, 6, 7, 3, 3, 4, 5)
   println(phoneString("974-334-9856")) // List(9, 7, 4, 3, 3, 4, 9, 8, 5, 6)

   

   

   

}
