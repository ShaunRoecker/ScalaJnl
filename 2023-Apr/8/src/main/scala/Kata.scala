object Kata extends App {  

   object SolutionIsomorphic {
      //   def isIsomorphic(s: String, t: String) = {
      //       val mapSValues = s.zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap.values.toSet
      //       println(mapSValues) //Set(Vector(0), Vector(1, 2))
      //       val mapTValues = t.zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap.values.toSet
      //       println(mapTValues) //Set(Vector(0), Vector(1, 2))
      //       mapSValues.equals(mapTValues)
      //   } 

        def isIsomorphic2(s: String, t: String): Boolean = {
            s.lazyZip(t).groupBy(_._1).forall(_._2.toSet.size == 1) &&
            s.lazyZip(t).groupBy(_._2).forall(_._2.toSet.size == 1)
        }
        // explanation:
            // If any char at string S has mapped to more than one char at string T
            // or any two chars at string S has mapped to a same char at string T, 
            // the two strings are not isomorphic      
    }
   import SolutionIsomorphic._

   // println(isIsomorphic("egg", "add"))
   println(isIsomorphic2("egg", "add"))

   // println("egg".zipWithIndex) // Vector((e,0), (g,1), (g,2))
   // println("egg".zipWithIndex.groupBy(_._1)) // HashMap(e -> Vector((e,0)), g -> Vector((g,1), (g,2)))
   // println("egg".zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap) // Map(e -> Vector(0), g -> Vector(1, 2))
   // println("egg".zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap.values) //Iterable(Vector(0), Vector(1, 2))
   // println("egg".zipWithIndex.groupBy(_._1).mapValues(_.map(_._2)).toMap.values.toSet) // Set(Vector(0), Vector(1, 2))

   object SolutionIsSubsequence {
      def isSubsequence(s: String, t: String): Boolean = {
         if (s.isEmpty) true
         else if (t.isEmpty) false
         else if (s.head == t.head) isSubsequence(s.tail, t.tail)
         else isSubsequence(s, t.tail)
      }

      def isSubsequence2(s: String, t: String) = {
         s.toList
      }

   }
   import SolutionIsSubsequence._

   println(isSubsequence("abc", "ahbgdc"))
   println(isSubsequence2("abc", "ahbgdc"))

   

}
