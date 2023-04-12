object Hello:
  def main(args: Array[String]): Unit =
    // return the character with the longest consecutive
    // repitition...

    def longestRepetition(s: String): Option[(Char, Int)] =
      val xs = s.toList
      if (xs.isEmpty) None
      else 
        val res = xs.tail.scanLeft(xs.head -> 1) { case ((prevChar, count), char) =>
          val nextAcc = 
            if (char == prevChar) 
              count + 1 
            else 1
          char -> nextAcc
        }
        Some(res.maxBy(_._2))


    println(longestRepetition("aabbbabbbccddbbbb"))

    

