object Kata extends App {  
   import scala.annotation.tailrec

   
   def sum(list: List[Int]): Int = 
      @tailrec
      def rec(acc: Int, xs: List[Int]): Int =
         if (xs.isEmpty) acc 
         else rec(acc + xs.head, xs.tail)
      rec(0, list)

   println(sum(List(1, 2, 3)))   

   def longestConsec(strings: List[String], k: Int): String =
      if (strings.isEmpty || k > strings.length || k <= 0) ""
      else strings.sliding(k).map(_.mkString).maxBy(_.length)
      
   val list = List(1, 2, 3, 4, 5)
   println(list.sliding(2).toList)
   // List(List(1, 2), List(2, 3), List(3, 4), List(4, 5))
   println(list.sliding(2, 2).toList)
   // List(List(1, 2), List(3, 4), List(5))

   def multiplicationTable(n: Int): List[List[Int]] =
      List.tabulate(n, n)((a, b) => (a + 1) * (b + 1))

   println(multiplicationTable(3))
   // List(List(1, 2, 3), List(2, 4, 6), List(3, 6, 9))

   // highest scoring word
   def high(s: String): String = 
      s.split(" ").maxBy(_.map(_.toInt - 96).sum)

   println(high("which is the highest scoring word"))
      // scoring

   def inList(list1: List[String], list2: List[String]): List[String] = 
      list1.filter(s => list2.exists(_.contains(s))).distinct.sorted
   

   println("abc" :+ "d")
   // Vector(a, b, c, d)

   println("abc" >= "def") // false
   println("def" >= "abc") // true

   println("abc" + "def") // abcdef

   println("abcd".apply(1)) // b
   println("abcd".apply(2)) // c

   println("abcd".charAt(1)) // b
   println("abcd".charAt(2)) // c

   println("abc" compare "abcd") // -1
   println("abc" compare "abc") // 0
   println("abc" compare "ab") // 1
   println("abc" compare "abcdef") // -3
   println("abc" compare "a") // 2
   println("abc" compare "q") // -16

   println("this is a string".collect{ case x if x.toInt > 100 => x}) // "thisisstring"

   def makeMyFuncThePFunc(s: String): String =
      s.split(" ").collect {
         case a if a == "funk" => "P-Funk"
         case _ => "_"
      }.mkString

   println(makeMyFuncThePFunc("how much funk does it take to get to the center of funk"))
   // __P-Funk_________P-Funk

   println("abc" diff "ab") // c
   println("abc" diff "bac") // 

   println("count" contains "oun") // true

   println("This is a string".distinct) // This atrng

   println(("how many words?").indexWhere(_ == 'w')) // 2

   val indicesOfEvens = List(1, 2, 3, 4, 5, 6).zipWithIndex.filter(x => x._1 % 2 == 0).map(_._2)
   println(indicesOfEvens) // List(1, 3, 5)

   def evenIndex(list: List[Int]): List[Int] =
      list.zipWithIndex.filter(_._1 % 2 == 0).map(_._2)

   println(evenIndex(List(2, 4, 3, 6, 7, 8, 10, 1, 2, 3))) // List(0, 1, 3, 5, 6, 8)

   println(List(1,2,3,4,5,6,7,8,9,10).zipWithIndex.collect{ case(a,b) if a % 2 == 0 => b})
   // List(1, 3, 5, 7, 9)

   
   

   

}
