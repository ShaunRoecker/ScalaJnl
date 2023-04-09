object Kata extends App {  

   // Merge two list of tuples by common elements
   val l1 = List(("Dan", "b"), ("Dan","a"), ("Bart", "c"))
   val l2 = List(("a", "1"), ("c", "1"), ("b", "3"), ("a", "2"))


   def join2[A, B, C](l1: List[(A, B)], l2: List[(B, C)]): List[(A, C)] = {
      for {
         (key1, subkey1) <- l1
         (key2, subkey2) <- l2
         if subkey1 == key2
      } yield (key1 -> subkey2)  
   }

   println(join2(l1, l2)) // List((Dan,3), (Dan,1), (Dan,2), (Bart,1))

   def longestPalindrome(s: String): Int = {
      s match {
         case "" => 0
         case s if s.length == 1 => 1
         case _ => {
               // val lenMap = s.groupMapReduce(identity)(_ => 1)(_ + _)
               2
         }
      }
   }

   println(longestPalindrome("abccccdd"))

   println("abccccdd".groupMapReduce(identity)(_ => 1)(_ + _))
   val a = "abccccdd".groupMapReduce(identity)(_ => 1)(_ + _)
   val (odd, even) = "abccccdd"
                        .groupMapReduce(identity)(_ => 1)(_ + _)
                           .partitionMap{
                              case l if l._2 % 2 != 0 => Left(l)
                              case r => Right(r)
                           }
   
   println("partitionMap:")
   println(odd) //List((a,1), (b,1))
   // println(odd.maxByOption(_._2)._2) 
   println(odd.foldLeft(0){ case (acc, (char, count)) => acc + count}) //2
   println(even)
   
   println(" here:")
   println(Map("a" -> 1, "b" -> 2, "c" -> 3).partition {
      case a if a._1 == "a" => true
      case _ => false
   })
   // (Map(a -> 1),Map(b -> 2, c -> 3))

   def longestPalindrome3(s: String): Int = {
      val (odd, even) = 
         s.groupMapReduce(identity)(_ => 1)(_ + _)
            .partitionMap{
               case l if l._2 % 2 != 0 => Left(l)
               case r => Right(r)
            }

      val maxOdd = 
         odd.maxByOption(_._2) match {
            case Some(tuple) => tuple._2
            case None => 0
         }

      val sumEven = 
         even.foldLeft(0){ 
            case (acc, (char, count)) => acc + count 
         }

      sumEven + maxOdd
   }
   println(longestPalindrome3("abccccdd")) //7

   def longestPalindrome2(s: String): Int = {
        val freq = s.groupMapReduce(identity)(k => 1)(_ + _).values  
        val (ans, odd) = freq.foldLeft(0, false) {
            case ((sum, odd), n) => 
                if (n % 2 == 0)
                   (sum + n, odd)
                else (sum + n - 1, true)
        }   
        if (odd) ans + 1 else ans   
   }
   println("abccccdd".groupMapReduce(identity)(k => 1)(_ + _).values ) // Iterable(1, 1, 4, 2)



}
