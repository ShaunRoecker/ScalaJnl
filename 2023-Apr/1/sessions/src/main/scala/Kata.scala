object Kata extends App {  

   def twoSum(nums: List[Int], target: Int) = 
      Iterator.range(0, nums.size).map { i =>
         Iterator.range(i + 1, nums.size).collectFirst{
            case j if ((nums(i) + nums(j)) == target) => (i, j)
         }
      }.collectFirst{ case Some(tuple) => tuple } match
         case None => (-1, -1)
         case Some(value) => value
      
   println(twoSum(List(1, 2, 3, 4, 5, 6, 7), target = 12))


   def twoSum2(nums: List[Int], target: Int) = 
      (0 until nums.size).map { i =>
         (i + 1 until nums.size).collectFirst{
            case j if ((nums(i) + nums(j)) == target) => (i, j)
         }
      }.collectFirst{ case Some(tuple) => tuple } match
         case None => (-1, -1)
         case Some(value) => value
   
   println(twoSum2(List(1, 2, 3, 4, 5, 6, 7), target = 12))


   def twoSumTailRec(nums: Array[Int], target: Int): Array[Int] = {
      import scala.annotation.tailrec
      @tailrec
      def check(i: Int, miMap: Map[Int, Int]): Array[Int] =  {
         val diff: Int = target - nums(i)
         if (miMap.contains(diff)) Array(miMap(diff), i)
         else check(i+1, miMap + (nums(i) -> i))
      }
      check(0, Map.empty)
   }

   println(twoSumTailRec(Array(1, 2, 3, 4, 5, 6, 7), target = 12).toList)


   println(List(0, 0, 2, 3, 1, 0).collectFirst { case i if i != 0 => i}) // Some(2)

   val l = List(1,2,3,4,5)

   def f(l:List[Int]):List[Int] = {l.map(x=>x-1)}

   def g(l:List[Int]):List[Int] = {l.map(x=>x+1)}

   val p = f _ andThen g _

   val output = p(l)
   println(output) // List(1, 2, 3, 4, 5)

   val q = f _ andThen g _ andThen f _ 

   val output2 = q(l)
   println(output2) // List(0, 1, 2, 3, 4)

   // andThen allows us to compose functions

   // we can create a function that gets an element from a list and
   // then multiplies that element by 2

   // Its important to know that List is a function

   val xs1 = List(1, 2, 3, 4, 5)
   
   val elementFromIndex = xs1(3); println(elementFromIndex) // 4
   // You see we called xs1 as a function, because it is one.

   // so we can compose additional functions onto our List function
   val multi2ListWhenCalled = xs1.andThen(_ * 2)
   println(multi2ListWhenCalled(1)) // 4
   println(multi2ListWhenCalled(0)) // 2

   // With the solution l.map(x => x + 1).map(x => x - 1) you are traversing the list twice.
   // When composing 2 functions using the andThen combinator and then applying it 
   // to the list, you only traverse the list once.

   






   
   
}
