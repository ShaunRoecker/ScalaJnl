object Kata extends App {  

   val list = List(1, 2, 3, 4, 5, 6, 7, 8)

   println(list.combinations(2).filter(_.sum == 5).toList) // List(List(1, 4), List(2, 3))

   val list1 = List(1, 2, 3, 4, 5, 6, 7, 8)
   val target = 7
   

   val fm1 = for {
      i <- list1
   } yield i + 2

   println(fm1) 

   val fm2 = for {
      elem <- list1
      if (elem == 1) 
   } yield elem

   println(fm2)

   def twoSum(list: List[Int], target: Int): List[Int] =
      val fm3 = for {
         i <- (0 until list.length)
         j <- ((i + 1) until list.length)
         if (list1(i) + list1(j) == target)
      } yield List(i, j)

      fm3.headOption match {
         case Some(x) => x
         case None => List(-1, -1)
      }


   println(twoSum(list1, 11))

   // Running sum
   def runningSum(nums: List[Int]): List[Int] = 
      if (nums.isEmpty) List()
      else nums.tail.scanLeft(nums.head)(_ + _)

   println(runningSum(List(1, 2, 3, 4)))
   import scala.annotation.tailrec

   def pivotIndex(nums: Array[Int]): Int = 
      @tailrec 
      def pivotAcc(idx: Int, leftSum: Int, rightSum: Int): Int = 
         if (idx == nums.length) -1
         else if (leftSum == rightSum - nums(idx)) idx
         else pivotAcc(idx + 1, leftSum + nums(idx), rightSum - nums(idx))

      pivotAcc(0, 0, nums.sum)

   
   println(pivotIndex(Array(0, 1, 0)))

   def pivotIndex2(nums: Array[Int]): Int =
      @tailrec 
      def pivotRec(i: Int, leftSum: Int, rightSum: Int): Int =
         if (i == nums.length) -1
         else if (leftSum == rightSum - nums(i)) i
         else pivotRec(i + 1, leftSum + nums(i), rightSum - nums(i))
      pivotRec(0, 0, nums.sum)

   // is Isomorphic
   def isIsomorphic(s: String, t: String): Boolean = 
        s.zip(t).groupBy(_._1).forall(_._2.toSet.size == 1) &&
        s.zip(t).groupBy(_._2).forall(_._2.toSet.size == 1)
    
   
   println(isIsomorphic("hollowhall", "hollowhall"))

   def isIsomorphic2(s: String, t: String) = 
        s.zip(t).groupBy(_._1)

   println(isIsomorphic2("hollow", "hollow"))

   def isIsomorphic3(s: String, t: String): Boolean = 
        val mapped = s.zip(t).toMap.map(_.swap)
        t.flatMap(mapped.get(_)).mkString == s

      
   println(isIsomorphic3("hollow", "hollow"))
   

   def isIsomorphic4(s: String, t: String) = 
        val mapped = s.zip(t).toMap.map(_.swap)
        t.flatMap(mapped.get(_)).mkString == s

      
    
   println(isIsomorphic4("hollow", "dummub"))

   println("aaaaa".zip("abcde").toMap) // Map(a -> e)
   println("aaaaa".zip("abcde").toMap.map(_.swap)) // Map(e -> a)
   println("hollow".zip("dummub").toMap.map(_.swap)) //Map(d -> h, u -> o, m -> l, b -> w)
        
   
   val myMap = Map(1 -> "a", 2 -> "b", 3 -> "c", 4 -> "d")
   // println(myMap.mapValues(x => "hi" + x)) // depreciated

   val res32 = myMap.map { case (k, v) => (k, "hi" + v)}
   println(res32)
   
   val res33 = myMap.map { case (k, v) => (k, v match { case v => "hi" + v })}
   println(res33)

   println("hollow".groupMapReduce(identity)(_ => 1)(_ + _))

   println(List("Ally", "Sarah").flatMap(_.toLowerCase)) //List(a, l, l, y, s, a, r, a, h)

   def grpB(i: Int) =
      i match 
         case a if a < 3 => "Something"
         case b if b >= 3 && b <= 7 => "Something else"
         case _ => "Something else entirely"

   
   val res56 = List (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
   val res57 = res56.groupBy(grpB)
   println(res57)
   //HashMap(Something else entirely -> List(8, 9, 10), Something -> List(1, 2), Something else -> List(3, 4, 5, 6, 7))

   def addMaps(map1: Map[String, Int], map2: Map[String, Int]): Map[String, Int] =
      map1.foldLeft(map2) { case (map, (k, v)) =>
         map.get(k) match
            case Some(newV) => map + (k -> (newV + v))
            case None => map + (k -> v)   
      }
        
   println(addMaps(Map("a" -> 2, "b" -> 3), Map("b" -> 4, "c" -> 5)))
   
   // lift "lifts" the value of the index passed into the method,
   // into the Option monad
   val xd = Seq(1, 2, 3).lift(0)

   println(xd) // Some(1)

   val xd1 = "abcd".lift(0)

   println(xd1) //Some(a)
   




   
   
}
