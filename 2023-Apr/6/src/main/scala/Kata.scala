package kata

object Kata extends App {  
   def howFastView(xs: List[Int]): List[Int] =
      xs.view.map(x => x * 2).map(x => x * 2).map(x => x * 2).toList

   def howFastNoView(xs: List[Int]): List[Int] =
      xs.map(x => x * 2).map(x => x * 2).map(x => x * 2)

   val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
   val idxOf = list.indexOf(100)
   println(idxOf)

   val idxSlc = List(5, 4, 0, 3, 4, 2).indexOfSlice(List(0, 3, 4))
   println(idxSlc)

   println(List(1, 2, 3, 4).indexWhere(_ > 2))

   for {
      i <- List(1, 2, 3, 4, 5).indices
   } do println(i)

   println(List(1, 2, 3, 4).inits.toList) //the toList is only to see the result, Iterators are shy
   //List(List(1, 2, 3, 4), List(1, 2, 3), List(1, 2), List(1), List())

   println(List(1, 2, 3).lift) 

   val interXsect = List(1, 2, 3, 4).intersect(List(1, 2, 4, 5))
   println(interXsect) // List(1, 2, 4)

   println(List(1, 2, 3).isTraversableAgain) //true
   println(Iterator.range(0, 1).isTraversableAgain) //false

   println(List(1, 2, 3).iterableFactory)

   println(List(1, 2, 3, 4).knownSize)

   println(Math.min(0, 4))
   println(math.min(0, 4)) // doesnt even matter bro

   val lz = List(1, 2, 3).lazyZip(List(4, 5, 6))
   println(lz.toList) // List((1,4), (2,5), (3,6))

   println(List(1, 2, 3).lengthCompare(List(1, 2))) // 1
   println(List(1, 2, 3).lengthCompare(List(1, 2, 3, 4, 5, 6, 7))) // -1
   println(List(1).lengthCompare(List(1, 2, 3, 4, 5, 6, 7))) // -1
   println(List(1, 2, 3, 4, 5, 6, 7).lengthCompare(List(1, 2))) // 1
   println(List(1, 2).lengthCompare(List(1, 2))) // 0

   val pf: PartialFunction[Int, Boolean] = { case i if i > 0 => i % 2 == 0} 
   // You can "lift" a PartialFunction[A, B] into a Function[A, Option[B]]. 
   // That is, a function defined over the whole of A but whose values are of type Option[B]
   val nf: Int => Option[Boolean] = pf.lift

   println(nf(-1)) // None
   println(nf(1)) // Some(false)


   case class Person(name: String, age: Int)

   implicit val personAgeOrdering: Ordering[Person] = 
        Ordering.fromLessThan((a, b) => a.age > b.age)

   val people = List(
      Person("A", 10),
      Person("B", 20),
      Person("C", 30),
   )

   println(people.max) // Person(A,10)
   println(people.min) // Person(A,10)



}
