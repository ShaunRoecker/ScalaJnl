

object Kata {

    def reverseString(s: String): String = 
        s.foldLeft("")((acc, x) => x +: acc)

    println(reverseString("hello world"))

    def reverseList(xs: List[Int]): List[Int] = 
        xs.foldLeft(List[Int]())((acc, x) => x +: acc)

    println(reverseList(List(1, 2, 3, 4, 5)))

    def last(s: String): List[String] = s.split(" ").sortBy(_.last).toList

    println(last("aaa aab ccd ddc ddf ggg"))

    def highOrig(s: String): String = s.split(" ").maxBy(_.map(_.toInt - 96).sum)

    def highBreakout(s: String): String = {
        s.split(" ") // split the string into a list of strings
        .maxBy(      // getting the "max" of the strings, with some logic
            _.map(   // map here is mapping over each char in the string
                _.toInt - 96 // convert char to int, and subtract 96 to get a value for char (1 - 26)
            ).sum    // take the sum of all the char values in the single word (string)
        )    // with maxBy, the word in the list with the highest summed value is returned
    }
    println('a'.toInt) // 97
    println(('a'.toInt - 96)) // 1
    println('z'.toInt) // 122
    println(('z'.toInt - 96)) // 26

    final case class Person(name: String, age: Int)
    val people = List(Person("john", 24), Person("sarah", 27), Person("fred", 29), Person("allison", 32))

    // You use maxBy() if you wanted the maximum as measured by some other parameter.
    println(people.maxBy(_.age)) // Person(allison,32)
    println('a'.toInt) 

    // get the person in the list people, whose name sums to the highest value
    def highestValueName(people: List[Person]): Option[Person] = {
        people match {
            case Nil => None
            case _ => Some(people.maxBy(_.name.map(_.toInt - 96).sum))
        }
    }

    println(highestValueName(people)) //Some(Person(allison,32))
    println(highestValueName(List[Person]())) // None

    def fizzBuzz(n: Int): List[String] = {
        val fb = for (i <- Range.inclusive(1, n)) yield {
            (i % 3, i % 5) match {
                case (0, 0) => "FizzBuzz"
                case (_, 0) => "Fizz"
                case (0, _) => "Buzz"
                case _ => i.toString
            }
        }
        fb.toList
    }

    println(fizzBuzz(16))

    println("word".map(_.toInt - 96)) //ArraySeq(23, 15, 18, 4)
    println("word".slice(1, 3)) // or

}

object Example extends App {
    
    val a = Set(1, 2)
    val b = Set(1, 3, 4)

    val set1And2Union = a.union(b);  println(set1And2Union) // Set(1, 2, 3, 4)
    val set1And2Intersect = a.intersect(b);  println(set1And2Intersect) // Set(1)

    // give me every element that is in a but is not in b
    val aAndNotB = a.diff(b); println(aAndNotB) //Set(2)
    
    // give me every element that is in b but is not in a
    val bAndNotA = b.diff(a); println(bAndNotA) //Set(2)

    val rand = scala.util.Random.alphanumeric.take(5).mkString
    println(rand) //UBkJH

    val initial = List("doodle", "Cons", "bible", "Army")
    val sortedList = initial.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)
    println(sortedList)

    val withoutFlatmap = initial.map(_.map(_.toInt - 96)).flatten
    println(withoutFlatmap) //List(4, 15, 15, 4, 12, 5, -29, 15, 14, 19, 2, 9, 2, 12, 5, -31, 18, 13, 25)

    val withFlatmap = initial.flatMap(s => s.map(_.toInt - 96))
    println(withFlatmap) //List(4, 15, 15, 4, 12, 5, -29, 15, 14, 19, 2, 9, 2, 12, 5, -31, 18, 13, 25)

    val zwi = initial.zipWithIndex.map {
        case (v, index) => (v.map(_.toInt - 96).sum, index)
    }
    println(zwi)

    val mapWithCase = initial.map{ case item => item.toLowerCase }
    println(mapWithCase) //List(doodle, cons, bible, army)

    val xs = Seq(4, 5, 2, -1, -3, 4)

    val xsd = xs.collect {
        case i if i > 0 => 1
        case n if n <= 0 => 0
    }
    println(xsd) //List(1, 1, 1, 0, 0, 1)

    def combineMaps(map1: Map[String, Int], map2: Map[String, Int]): Map[String, Int] =
        map1.foldLeft(map2) { case (map, (key, value)) =>
            map.get(key) match {
                case Some(newValue) => map + (key -> (value + newValue))
                case None => map + (key -> value)    
            }
        }

    println(combineMaps(Map("a" -> 1, "b" -> 2), Map("a" -> 2, "c" -> 3)))
    // Map(a -> 3, c -> 3, b -> 2)
    
    




}