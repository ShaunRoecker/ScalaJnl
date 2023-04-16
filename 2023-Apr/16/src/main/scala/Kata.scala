object Kata extends App {  

   println()
   def `._+s`(x: Int): Int = x + 1

   println(`._+s`(1)) // 2

   
   final case class Person(name: String, age: Int)
   val people = 
      List(
         Person("john", 24), 
         Person("sarah", 27), 
         Person("fred", 29), 
         Person("allison", 32)
      )

   def personAgeCompare(p: Person, people: List[Person]) = 
      val xs = p +: people
      val oldest = xs.maxBy(_.age)
      if (oldest == p) true else false

   println(personAgeCompare(Person("Henry", 25), people)) // false
   println(personAgeCompare(Person("Henry", 75), people)) // true


   val list = List(1, 2, 1, 3, 4, 3, 2, 1, 1)

   val gmr = list.groupMapReduce(identity)(_ => 1)(_ + _) // Map(1 -> 4, 2 -> 2, 3 -> 2, 4 -> 1)

   val res1 = gmr.values
   println(res1) // Iterable(4, 2, 2, 1)

   val res1sum = res1.sum

   assert(res1sum == list.length)

   // most consectutive characters in a String

   def consecutive(s: String): Option[(Char, Int)] =
      val list = s.toList
      if (list.isEmpty) None
      else {
         val res = list.tail.scanLeft(list.head -> 1) { case ((prevChar, count), char) =>
            val newCount = 
               if (prevChar == char) count + 1
               else 1
            (char, newCount)
         }
         Some(res.maxBy(_._2))
      } 

   

   










}
