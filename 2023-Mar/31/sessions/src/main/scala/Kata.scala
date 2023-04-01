object Kata extends App {  
   import scala.annotation.tailrec

   println("HashCode".hashCode())

   def digitalRoot(n: Int): Int = 

      n.toString.map(_.asDigit).sum 

   println(digitalRoot(16)) //6

   def digitalRoot2(n: Int): Int =
      val x = n.toString.map(_.asDigit).sum
      if (x < 10) x
      else digitalRoot2(x)
      
   println(digitalRoot2(132189)) //6

   def digitalRootMostConcise(n: Int): Int =
      (n - 1) % 9 + 1

   //Write a function that accepts an array of 10 integers (between 0 and 9), 
   // that returns a string of those numbers in the form of a phone number.

   def createPhoneNumber(numbers: Seq[Int]): String = {
      List(
         numbers.take(3).mkString("(", "", ")"), " ",
         numbers.slice(3, 6).mkString, "-",
         numbers.drop(6).mkString
      ).mkString
   }

   println(createPhoneNumber(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)))

   def createPhoneNumberBetter(numbers: Seq[Int]) = {
    "(%d%d%d) %d%d%d-%d%d%d%d".format(numbers:_*)
  }

//   Create a list of Ints from a string representation of a 
//   phone number, with/without dashes/parenthesis
         // "(727) 889-0872" or "727-333-3333" or "9999999999"

   def phoneString(str: String): List[Int] =
      str.toList.filter(_.isDigit).map(_.toInt - 48)

   println(phoneString("(923) 667-3345")) // List(9, 2, 3, 6, 6, 7, 3, 3, 4, 5)
   println(phoneString("974-334-9856")) // List(9, 7, 4, 3, 3, 4, 9, 8, 5, 6)

   val nemo = Vector("nemo")

   val x3 = for {
      i <- nemo
      if (i == "nemo")
   } do println("found Nemo")

   def findNemo(vect: Vector[String]): Unit = 
      for {
         i <- nemo
         if (i == "nemo")
      } do println("found Nemo")

   findNemo(nemo)

   // Given an array of integers, return the indices of the 
   // two numbers that add up to a given target
   
   import scala.collection.immutable.HashMap

   def twoSum(nums: Array[Int], target: Int): Array[Int] = {
      val indexByValue = HashMap(nums.zipWithIndex: _*)
      val result = nums.indices.collectFirst(Function.unlift { i =>
         indexByValue.get(target - nums(i)).filter(_ != i).map(Array(i, _))
      })
      result.getOrElse(Array(-1, -1))
   }

   println(twoSum(Array(1, 2, 3, 4, 5, 6, 7), 7).toList)

   def twoSum2(nums: Array[Int], target: Int): Array[Int] = {
      nums.indices.iterator
         .scanLeft(HashMap.empty[Int, Int]) { (map, i) =>
            map + (nums(i) -> i)
         }
         .zipWithIndex
         .collectFirst(Function.unlift { case (indexByValue, i) =>
            indexByValue.get(target - nums(i)).filter(_ != i).map(Array(i, _))
         })
         .getOrElse(Array(-1, -1))
   }
   def twoSum3(nums: Array[Int], target: Int) =
      nums.indices

   println(twoSum2(Array(1, 2, 3, 4, 5, 6, 7), 4).toList)
   
   println("hh".getClass()) //class java.lang.String

   println("779".reverse.padTo(10, "0").reverse) //Vector(0, 0, 0, 0, 0, 0, 0, 7, 7, 9)
   // If you use a String as the pad value, you get back a Vector by default

   println("1234".reverse.padTo(7, '0').reverse) // 0001234
   // If you use a Char as the pad value, you get back a Vector by default

   // You have to use the double reverse to get the pad values to the left.

   println("aloha".padTo(10,'a')) //  alohaaaaaa

   println(List(1, 2, 3).padTo(0, 10)) // doesnt work intuitively - List(1, 2, 3)

   // To get this functionality
   println{
      val arr = List(1, 2, 3)
      arr ++ List.fill(10 - arr.length)(0)
   }
   List(1, 2, 3, 0, 0, 0, 0, 0, 0, 0)

   

   def twoSum4(nums: Array[Int], target: Int): Option[(Int, Int)] = {
      Iterator.range(0, nums.length).map { i =>
            Iterator.range((i + 1), nums.length).collectFirst {
               case j if ((nums(i) + nums(j)) == target) => (i, j)
            }
         }.collectFirst {
            case Some(tuple) => tuple
         }
   }

   println(twoSum4(Array(1, 2, 3, 4, 5, 6, 7), 9))


   def twoSum5(nums: List[Int], target: Int) = 
      Iterator.range(0, nums.length).map { i =>
         Iterator.range(i + 1, nums.length).collectFirst {
            case j if ((nums(i) + nums(j)) == target) => (i, j)
         }
      }  // out at this point: List(None, Some((1,6)), Some((2,5)), Some((3,4)), None, None, None)
      .collectFirst {
         case Some(x) => x
      } // out at this point: Some((1,6))
         

   println(twoSum5(List(1, 2, 3, 4, 5, 6, 7), 9))

    

   def twoSum6(nums: List[Int], target: Int) =
      Iterator.range(0, nums.length).map { i =>
         Iterator.range(i + 1, nums.length).collectFirst {
            case j if ((nums(i) + nums(j)) == target) => (i, j)
         }
      }
   
   println(twoSum6(List(1, 2, 3, 4, 5, 6, 7), 9).toList)

   def twoSum7(nums: List[Int], target: Int) =
      Iterator.range(0, nums.length).map { i =>
         Iterator.range(i + 1, nums.length).collectFirst {
            case j if ((nums(i) + nums(j)) == target) => (i, j)
         }}
      // }.collectFirst{
      //    case Some(tuple) => tuple
      // }
   
   println(twoSum7(List(1, 2, 3, 4, 5, 6, 7), 9).toList)

   



}  