object Kata extends App {

    // multiplication table
    def multiTable(n: Int): List[List[Int]] = {
        List.tabulate(n, n)((a, b) => (a + 1) * (b + 1))
    }
    println(multiTable(4))

    // For a given string s find the character c (or C) with longest consecutive repetition and return:
        // Some(c, l)
    // where l (or L) is the length of the repetition. If there are two or more characters with the 
    // same l return the first in order of appearance.

    // For empty string return: None

    def longestRepetition(s: String) /*Option[(String, Int)]*/ = {
        val ls = s.toList
        if (ls.isEmpty) None
        else {
            val res = ls.tail.scanLeft(ls.head -> 1){ case ((prev, c), a) =>
                val nextAcc = 
                    if (a == prev) c + 1
                    else 1
                a -> nextAcc
            }
            println(res)
            Some(res.maxBy(_._2))
        }
        
    }
    //List((a,1), (n,1), (s,1), (d,1), (a,1), (a,2), (a,3), (s,1), (y,1), (y,2))
    println(longestRepetition("ansdaaasyy"))  //Some((a,3))


    def countCharInString(s: String): Map[Char, Int] = {
        s.groupMapReduce(identity)(_ => 1)(_ + _)
    }

    println(countCharInString("smmmmmssmmddddmyy"))


    def removeVowelsFromString(s: String) = s.replaceAll("[aeiouAEIOU]", "") 

    println(removeVowelsFromString("hello Moto"))


    def reverseWords(text: String): String = {
        text.split(" ").map(_.reverse).mkString(" ")
    }

    println(reverseWords("hello from the planet Mars"))

    def descendingOrderInt(num: Int): Int = {
        num.toString.sortWith(_.asDigit > _.asDigit).toInt
    }

    println(descendingOrderInt(15))


    def orderedCount(chars: String): List[(Char, Int)] = 
        chars.distinct.map(c => (c,chars.count(_ == c))).toList

    

    println(orderedCount("abracadabra"))
    
    





}
