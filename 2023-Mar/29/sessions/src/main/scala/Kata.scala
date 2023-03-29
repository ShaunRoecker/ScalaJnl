object Kata extends App {  
   println("kata")
   
   // For a given string s find the character c (or C) with longest 
   // consecutive repetition and return:
      // Some(c, l)
      // for empty string: None

   def longestRepetition(s: String): Option[(Char, Int)] = {
      val xs = s.toList
      if (xs.isEmpty) None
      else {
         val res = xs.tail.scanLeft((xs.head -> 1)){ case ((prevChar, count), nextChar) => 
            val nextAcc = 
               if (prevChar == nextChar) count + 1
               else 1
            (nextChar -> nextAcc)
         }
         Some(res.maxBy(_._2))
      }
   }

   println(longestRepetition("bannanagram")) //Some((n,2))

   // count all the occurring characters in a string. If you have a string like aba, 
   // then the result should be {'a': 2, 'b': 1}

   def count(string: String): Map[Char,Int] = {
      string.groupMapReduce(identity)(_ => 1)(_ + _)
   }
   println(count("bannanagram"))
   // HashMap(n -> 3, a -> 4, m -> 1, b -> 1, g -> 1, r -> 1)


   //If we list all the natural numbers below 10 that are multiples of 3 or 5, 
   // we get 3, 5, 6 and 9. The sum of these multiples is 23.
   def solution(number: Int): Long = 
      (1 until number).view.filter(n => n % 3 == 0 || n % 5 == 0).sum 
   
   println(solution(10)) // 23

   // convert the string to CamelCase
   def toCamelCase(str: String): String = {
      val xs = str.split("[-_]")
      xs.tail.foldLeft(xs.head){ case (a, b) => a + b.capitalize }
   }

   println(toCamelCase("the-camel-case"))

   def toCamelCase2(str: String): String = {
      val xs = str.split("[-_]").toList
      (xs.head.toLowerCase :: xs.tail.map(_.capitalize)).mkString
   }

   println(toCamelCase2("tTe-camel_case"))

   // Implement the function unique_in_order which takes as argument a sequence 
   // and returns a list of items without any elements with the same value next 
   // to each other and preserving the original order of elements.
   def uniqueInOrder[T](xs: Iterable[T]): Seq[T] = {
      def concat(list: List[T]): List[T] = {
         list match {
            case Nil => list 
            case x :: xs => x :: concat(xs.dropWhile(_ == x))
         }
      }
      concat(xs.toList)
   }

   def uniqueInOrder2[T](xs: Iterable[T]): Seq[T] = {
      if (xs.isEmpty) Nil 
      else xs.head +: uniqueInOrder(xs.tail.dropWhile(_ == xs.head))
   }

   println(uniqueInOrder("abbcbsdbbbdddee")) //List(a, b, c, b, s, d, b, d, e)
   println(uniqueInOrder2("abbcbsdbbbdddee")) //List(a, b, c, b, s, d, b, d, e)

   //Count the number of occurrences of each character and return it as a (list of tuples) 
   // in order of appearance. For empty output return (an empty list).

   def orderedCount(s: String): List[(Char, Int)] = {
      s.distinct.map(c => (c, s.count(_ == c))).toList
   }

   println(orderedCount("abracadabra"))
   // List((a,5), (b,2), (r,2), (c,1), (d,1))
   

}
