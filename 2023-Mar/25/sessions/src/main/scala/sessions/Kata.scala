object Kata extends App {
    //Count the number of occurrences of each character and return it as a (list of tuples) 
    // in order of appearance. For empty output return (an empty list).

    def orderedCount(chars: String): List[(Char, Int)] = 
        chars.distinct.map(c=>(c, chars.count(_ == c))).toList

    println(orderedCount("abracadabra"))

    def squareSum(xs: List[Int]): Int = 
        xs.map(n=>n*n).sum
    
    println(squareSum(List(1, 2, 3)))

    def charWithLongestRep(s: String) /*Option[Char, Int]*/ = {
        val ls = s.toList
        if (ls.isEmpty) None
        else {
            val res = ls.tail.scanLeft(ls.head -> 1){ case ((prevChar, count), currChar) =>
                val nextAcc = 
                    if (currChar == prevChar) count + 1
                    else 1
                currChar -> nextAcc
            }
            println(res)
            Some(res.maxBy(_._2))
        }
    }

    val res1 = charWithLongestRep("fjjjddskkdddf") 
    //List((f,1), (j,1), (j,2), (j,3), (d,1), (d,2), (s,1), (k,1), (k,2), (d,1), (d,2), (d,3), (f,1))
    println(res1)// Some((j,3))

    def fizzBuzz(n: Int): List[String] = {
        val xs = for (i <- Range.inclusive(1, n)) yield {
            (i % 3, i % 5) match {
                case (0, 0) => "FizzBuzz"
                case (0, _) => "Fizz"
                case (_, 0) => "Buzz"
                case _ => i.toString
            } 
        }  
        xs.toList 
    } 

    println(fizzBuzz(15))

    def isDivisible(n: Int, x: Int, y: Int): Boolean = {
        (n % x == 0, n % y == 0) match {
            case (true, true) => true
            case _ => false
        }
    }

    println(isDivisible(3, 3, 4)) //false 
    println(isDivisible(12, 3, 4)) //true


    def moveZeroes(lst: List[Int]): List[Int] = {
        val zeroList = lst.partition(_ != 0)
        zeroList._1 ::: zeroList._2
    }

    println(moveZeroes(List(1, 2, 0, 1, 0, 1, 0, 3, 0, 1)))
    // List(1, 2, 1, 1, 3, 1, 0, 0, 0, 0)

    def moveZeroes2(lst: List[Int]): List[Int] = 
        lst.sortBy(_ == 0)

    println(moveZeroes2(List(1, 2, 0, 1, 0, 1, 0, 3, 0, 1)))
    // List(1, 2, 1, 1, 3, 1, 0, 0, 0, 0)

    def upper(strings: String*): String = 
        strings.map(_.toUpperCase).mkString("{{", "::", "}}")

    
    val scalaLs = List("a", "scala", "list")
    println(upper(scalaLs:_*)) // <-- ':_*' allows you to pass a list of strings to a
                                        // mathod that takes String*
                                        

}
