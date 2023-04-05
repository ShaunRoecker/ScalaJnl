object Kata extends App {  
   def maxProfit(prices: List[Int]) =
      prices.foldLeft((Int.MaxValue, 0)){ (acc, n) => 
         val x = (acc._1 min n, acc._2 max (n - acc._1))
         println(x)
         x
      }._2
   // 7, 0
   
   println(maxProfit(List(7,1,5,3,6,4)))
   println(maxProfit(List(5, 5, 5, 5, 5, 5)))

   def findMaximumProfit(stockPrices: Array[Int]) = {
      def getPotentialProfit(buyPrice: Int, sellPrice: Int): Option[Int] = {
         if (sellPrice > buyPrice) Some(sellPrice - buyPrice) else None
      }

      val maximumSellPricesFromIonward = stockPrices.view
         .scanRight(0)({ case (maximumPriceSoFar, dayPrice) =>
            Math.max(maximumPriceSoFar, dayPrice)
         }).toArray
      println(maximumSellPricesFromIonward.toList)

      val maximumSellPricesAfterI = maximumSellPricesFromIonward.drop(1)
      if (stockPrices.length < 2) None
      else
         stockPrices
            .zip(maximumSellPricesAfterI)
            .map({ case (buyPrice, sellPrice) =>
               getPotentialProfit(buyPrice = buyPrice, sellPrice = sellPrice)
            }).max  
   }


   println(findMaximumProfit(Array(7,1,5,3,6,4)))

   // longest palindrome:
   def longestPalindrome(s: String): Int = 
      s.size min ( 1 + s.groupBy(x => x)
                     .map({ case (k, v) => (v.size / 2) * 2 })
                        .filter(_ > 1)
                        .sum
                  )

  
   println(longestPalindrome("ababdnsnnaa"))
    
   def grpMapReduce(s: String) = 
     println(s.groupMapReduce(k => k)(k => 1)(_ + _).values) //Iterable(3, 2, 1, 1)
     println(s.groupMapReduce(k => k)(k => 1)(_ + _))  // Map(a -> 3, b -> 2, d -> 1, n -> 1)

   grpMapReduce("ababdna")

   def multiplyTwo(xs: List[Int]): List[Int] =
      xs.map(n => n * 2)

   def addTen(xs: List[Int]): List[Int] =
      xs.map(n => n + 10)

   val multiplyTwoAndAddTen = multiplyTwo _ andThen addTen _

   val x64 = multiplyTwoAndAddTen(List(1, 2, 3, 4, 5, 6, 7, 8))
   println(x64) // List(12, 14, 16, 18, 20, 22, 24, 26)

   import scala.sys.process._
   val dirContents = "ls".!!

   println(s"DirContents: $dirContents")

   // println(multi_lifted())

   val res45 = List(1, 2, 3).applyOrElse(0, x => x - 1) //1
   println(res45)

   // Remember that a Sequence IS a function, so when we use apply
   // it returns that value at the index of the argument
   println(List(1, 2, 3).apply(2)) // 3

   println(List(1, 2, 3).canEqual(false))

   println(List(1, 2, 3, 4).contains(3)) // true
   println("abcde".contains('c')) // true

   println(List(1, 2, 3, 4).containsSlice(List(2, 3))) // true
   println("abcde".containsSlice("cd")) // true

   println("Cookie cookie cookie".startsWith("C")) // true

   println(3.4.floor) // 3.0

   println(List(1, 2, 3, 4).empty)

   println(Some(10))
   val some = Some(Map("a" -> 1, "b" -> 2))
   
   println(List(1, 2, 3, 2).findLast(_ > 1))


}
