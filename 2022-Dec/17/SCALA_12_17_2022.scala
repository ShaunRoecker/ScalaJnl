object SCALA_12_17_2022 extends App {
  println("12/17/2022")

  // DAILY CTCI 1.3 && 1.4
    /////////////////////////////////////////////////////////
    // 1.3 (pg.)  replace all spaces in a string with '%20'
    def replaceRGX(s: String): String = ???
        // wait to finish the regex book, that's going to easily solve this problem


    // 1.4 (pg.)
    /////////////////////////////////////////////////////////
    import scala.annotation.tailrec
    def isPalindromePermutation(str: String): Boolean =
        @tailrec
        def parities(s: String, current: Map[Char, Boolean] = Map()): Map[Char, Boolean] =
            if (s.isEmpty) then current
            else parities(s.tail, current + (s.head -> !current.getOrElse(s.head, false)))
        
        @tailrec
        def countParities(m: Map[Char, Boolean], current: Map[Boolean, Int] = Map()): Map[Boolean, Int] =
            if (m.isEmpty) then current
            else countParities(m.tail, current + (m.head._2 -> (1 + current.getOrElse(m.head._2, 0))))

        countParities(parities(str)).getOrElse(true, 1) == 1
    
    println(isPalindromePermutation("racecar"))


    




}
