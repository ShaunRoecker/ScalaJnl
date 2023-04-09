object Kata extends App {  

   val l = List("Tom", "Jerry", "Panda", "Scala", "Linux")
   val n = l.groupBy(x => x)
   println(n)
   // HashMap(Scala -> List(Scala), Panda -> List(Panda), Tom -> List(Tom), Jerry -> List(Jerry), Linux -> List(Linux))
   val i = l.groupBy(identity)
   println(i)
   // HashMap(Scala -> List(Scala), Panda -> List(Panda), Tom -> List(Tom), Jerry -> List(Jerry), Linux -> List(Linux))

   // identity == A => A

   val k = l.groupBy(_.containsSlice("Linux"))
   println(k)
   // HashMap(false -> List(Tom, Jerry, Panda, Scala), true -> List(Linux))

   val tuples = List((1, 20), (2, 20), (1, 30), (2, 40), (2, 60))
   val avgs = tuples.groupMap(_._1)(_._2)
   println(avgs) //Map(1 -> List(20, 30), 2 -> List(20, 40, 60))

   // so the first parentheses is the value to groupBy, and the second is the value to group. 
   // I think, still learning this one.

   case class Person(name: String, age: Int)
   val people = List(
      Person("a", 10),
      Person("b", 20),
      Person("c", 30),
      Person("a", 30),
      Person("b", 30)
   )

   val grpPeepsBy = people.groupMap(_.name)(_.age)
   println(grpPeepsBy) // Map(a -> List(10, 30), b -> List(20, 30), c -> List(30))

   // then to get the average value of each group...
   val grpPAvg = people.groupMap(_.name)(_.age).map((k, v) => k -> (v.sum / v.length))
   println(grpPAvg) // Map(a -> 20, b -> 25, c -> 30)

   sealed trait StateScala2
   case object FL extends StateScala2
   case object TX extends StateScala2


   enum State:
      case FL, TX

   case class Street(value: String)
   case class City(value: String)
   case class Zip(value: Int)
   case class Address(street: Street, city: City, state: State | StateScala2, zip: Zip)
   case class Name(firstName: String, lastName: String)
   case class CashAmount(amount: Double)

   case class Account(cashAmount: CashAmount, name: Name, address: Address)

   val accounts: List[Account] = List(
      Account(CashAmount(10.23), Name("John", "Doe"), Address(Street("123"), City("Placeville"), State.FL, Zip(12545))),
      Account(CashAmount(15540.23), Name("Susan", "Doe"), Address(Street("432"), City("Placeville"), State.FL, Zip(12345))),
      Account(CashAmount(120.23), Name("John", "Doe"), Address(Street("123"), City("Otherville"), State.TX, Zip(12345))),
      Account(CashAmount(14435.23), Name("Jane", "Dom"), Address(Street("123"), City("Placeville"), State.FL, Zip(12545))),
      Account(CashAmount(777.23), Name("Joe", "Doe"), Address(Street("156"), City("Otherville"), State.TX, Zip(12345))),
      Account(CashAmount(1678.23), Name("Jon", "Doe"), Address(Street("123"), City("Placeville"), State.FL, Zip(12245)),
   ))

   val ax1 = accounts.groupMap(_.address.state)(_.cashAmount)
   println(ax1)
   //Map(
      // TX -> List(CashAmount(120.23), CashAmount(777.23)), 
      // FL -> List(CashAmount(10.23), CashAmount(15540.23), CashAmount(14435.23), CashAmount(1678.23))
   // )

   // Find the average cash amount by state...
   val avgByState = accounts.groupMap(_.address.state)(_.cashAmount).map{ case (k, v) => 
      val sum = v.foldLeft(CashAmount(0D)){ case (acc, v) => 
         val amount = acc.amount + v.amount
         CashAmount(amount)
      }
      k -> (sum.amount / v.length)   
   }
   println(avgByState) //Map(TX -> 448.73, FL -> 7915.98)  

   println(avgByState.toList) // List((TX,448.73), (FL,7915.98)) 


   // Seq.groupMapReduce(what to groupBy)(where to start count)(function to combine)

   println("aaabbbababcccddd".groupMapReduce(identity)(_ => 1)(_ + _))
   // Map(a -> 5, b -> 5, c -> 3, d -> 3)

   println("aaabbbababcccddd".groupMapReduce(_.toUpper)(_ => 100)(_ * _))
   // Map(A -> 1410065408, B -> 1410065408, C -> 1000000, D -> 1000000)

   val listToGroup = List(1,2,3,4,5,6,7,8)
   // sliding(3, 3) means they are lists of 3, and it takes 3 steps each list
   println(listToGroup.sliding(3,3).toList)
   // List(List(1, 2, 3), List(4, 5, 6), List(7, 8))

   // grouped(3) means they are just groups of 3
   println(listToGroup.grouped(3).toList)
   // List(List(1, 2, 3), List(4, 5, 6), List(7, 8))

   println(listToGroup.sliding(2,3).toList)
   // List(List(1, 2), List(4, 5), List(7, 8))

   case class Foo(a: Int, b: Int)
   val data: List[Foo] = Foo(1,10) :: Foo(2, 20) :: Foo(3,30) :: Nil

   // I know that in my data, there will be no instances of Foo with the same value of field a -
   //  and I want to transform it to Map[Int, Foo] (I don't want Map[Int, List[Foo]])

   // val m: Map[Int,Foo] = data.map(e => e.a -> e)(breakOut)
   // println(m)

   val matches/*: List[Tuple2[String, Int]] */ = List("a" -> 1, "a" -> 2, "b" -> 3, "c" -> 4, "c" -> 5)
   val m/*: Seq[(String, Seq[Int])]*/ = 
      matches
         .groupBy(_._1) // HashMap(a -> List((a,1), (a,2)), b -> List((b,3)), c -> List((c,4), (c,5)))
         .map { case (k, vs) => k -> vs.map(_._2) }  // Drop the String part of the value
                                             //HashMap(a -> List(1, 2), b -> List(3), c -> List(4, 5))
         .toVector // Vector((a,List(1, 2)), (b,List(3)), (c,List(4, 5)))
         .sortBy(_._2.size) // Vector((b,List(3)), (a,List(1, 2)), (c,List(4, 5)))

   println(m) //Vector((b,List(3)), (a,List(1, 2)), (c,List(4, 5)))

   val matches2 = List("a" -> 1, "a" -> 2, "b" -> 3, "c" -> 4, "c" -> 5)
   println(matches2.groupBy(_._1)) //HashMap(a -> List((a,1), (a,2)), b -> List((b,3)), c -> List((c,4), (c,5)))

   val people2 = List(
      Person("a", 10),
      Person("b", 20),
      Person("c", 30),
      Person("a", 30),
      Person("b", 30)
   )

   println(people2.groupBy(_._1))
   // HashMap(a -> List(Person(a,10), Person(a,30)), b -> List(Person(b,20), Person(b,30)), c -> List(Person(c,30)))
   val r4 = people2.groupBy(_.name)
                     .map { case (key, valList) => 
                              key -> valList.map { case p @ Person(n, a) => a }}
                     .toVector // Vector((a,List(10, 30)), (b,List(20, 30)), (c,List(30)))
                     .sortWith(_._2.sum > _._2.sum) // Vector((b,List(20, 30)), (a,List(10, 30)), (c,List(30)))
                     .map { case (k, v) => (k, v.sum)} // Vector((b,50), (a,40), (c,30))
         
                     
   println(r4)


   // Merge two list of tuples by common elements
   val l1 = List(("Dan", "b"), ("Dan","a"), ("Bart", "c"))
   val l2 = List(("a", "1"), ("c", "1"), ("b", "3"), ("a", "2"))

   def join[A, B, C](l1: List[(A, B)], l2: List[(B, C)]): List[(A, C)] = {
      for {
         (key, subkey) <- l1
         value <- l2.collect { case (`subkey`, value) => value }
      } yield key -> value  
   }

   println(join(l1, l2)) //List((Dan,3), (Dan,1), (Dan,2), (Bart,1))

   def join2[A, B, C](l1: List[(A, B)], l2: List[(B, C)]): List[(A, C)] = {
      for {
         (key1, subkey1) <- l1
         (key2, subkey2) <- l2
         if subkey1 == key2
      } yield (key1 -> subkey2)  
   }

   println(join2(l1, l2)) // List((Dan,3), (Dan,1), (Dan,2), (Bart,1))

   def join3(l1: List[(String, String)], l2: List[(String, String)]) = {
      val joined = for {
         (key1, subkey1) <- l1
         (key2, subkey2) <- l2
         if subkey1 == key2
      } yield (key1 -> subkey2)  
      joined.groupBy(_._1) // HashMap(Bart -> List((Bart,1)), Dan -> List((Dan,3), (Dan,1), (Dan,2)))
         .map{ case (k, v) => (k + "man") -> (v.map(_._2)) } // HashMap(Bartman -> List(1), Danman -> List(3, 1, 2))
         
   }

   println(join3(l1, l2)) // List((Dan,3), (Dan,1), (Dan,2), (Bart,1))



   // Using backticks, you can more or less give any name to a field identifier. In fact, you can even say
   val ` ` = 1
   println(` `) // 1



}
