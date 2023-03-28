object Kata extends App {
   // return the highest scoring word in a given string:    
   def high(s: String): String = s.split(" ").maxBy(_.map(_.toInt - 96).sum)

   println(high("which word scores the highest, maybe zzzisssone?"))

   // return an ordered count of the characters in a given string
   // as a list of tuples with the char and the total:
   def orderedCount(chars: String): List[(Char, Int)] = {
    chars.distinct.map(c => (c, chars.count(_ == c))).toList
   }

   println(orderedCount("how many charactersg"))
   // List((h,2), (o,1), (w,1), ( ,2), (m,1), (a,3), (n,1), (y,1), (c,2), (r,2), (t,1), (e,1), (s,1), (g,1))

//    reverse the word order but not the letters in the words
   def reverseWords(str: String): String = {
    str.split(" ").foldLeft(List[String]())((acc, x) => x +: acc).mkString(" ")
//  str.split(" ").reverse.mkString(" ")
   }

   println(reverseWords("The greatest victory is that which requires no battle"))
   // battle no requires which that is victory greatest Th


//    def likes(names: Array[String]): String = {
//         names match {
//             case Array()          =>  "no one likes this"
//             case Array(x)         => s"$x likes this"
//             case Array(x, y)      => s"$x and $y like this"
//             case Array(x, y, z)   => s"$x, $y and $z like this"
//             case Array(x, y, zs*) => s"$x, $y and ${zs.size} others like this"
//         }
//     }

    // Parse a linked list from a string:





}
