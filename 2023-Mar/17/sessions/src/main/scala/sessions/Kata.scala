

object Kata extends scala.App {

    object Helpers {
        implicit class StrUberOps(s: String) {
            def rickRoll = s + " Never gonna give you up!"
        }

        implicit class StringExtensions(s: String) {
            def toCamelCase1 = 
                s.trim.split(" ").map(_.capitalize).mkString

            def toCamelCase2 = {
                val lst = s.trim.split(" ")
                lst.foldLeft("")((a, b) => a + b.capitalize).mkString
            }

            def toRealCamelCase3 = {
                val xs = s.trim.split(" ")
                (xs.head.toLowerCase +: xs.tail.map(_.capitalize)).mkString
            }
        }
    }
    import Helpers._

    println("Hello, world".rickRoll)
    println("  camel case word ".toCamelCase1) //CamelCaseWord
    println("  camel case word ".toCamelCase2) //CamelCaseWord
    println("  camel case word ".toRealCamelCase3) //camelCaseWord

    def createFizzBuzzList(n: Int): List[String] = {
        val xs = for (i <- Range.inclusive(1, n)) yield {
            (i % 3, i % 5) match {
                case (0, 0) => "FizzBuzz"
                case (0, _) => "Buzz"
                case (_, 0) => "Fizz"
                case (_, _) => i.toString
            }
        }
        xs.toList
    }
    
    println(createFizzBuzzList(15))

    def multiplicationTable(n: Int) = {
        List.tabulate(n, n)((a, b) => (a + 1) * (b + 1))
    }
    println(multiplicationTable(4))
    // List(List(1, 2, 3, 4), 
    //      List(2, 4, 6, 8), 
    //      List(3, 6, 9, 12), 
    //      List(4, 8, 12, 16)
    // )

    def additionTable(n: Int) = {
        List.tabulate(n, n)((a, b) => (a + 1) + (b + 1))
    }
    println(additionTable(4))
    // List(List(2, 3, 4, 5), 
    //      List(3, 4, 5, 6), 
    //      List(4, 5, 6, 7), 
    //      List(5, 6, 7, 8)
    // )

    ////////////////////////////////////////////////////////////////
    // Reverse String
    def revString(s: String): String = {
        val arr = s.toCharArray
        val len = arr.length
        val arrBuff = Array.ofDim[Char](len)
        val minIdx = 0
        val maxIdx = len - 1

        for (i <- minIdx to maxIdx) {
            arrBuff(maxIdx - i) = arr(i)
        }
        new String(arrBuff)
    }

    println(revString("size"))

    import scala.reflect.ClassTag
    def revList[A: ClassTag](xs: List[A]): List[A] = {   
        var arrBuff = Array.ofDim[A](xs.length)
        val minIdx = 0
        val maxIdx = xs.length - 1
        for (i <- minIdx to maxIdx) {
            arrBuff(maxIdx - i) = xs(i)
        }
        arrBuff.toList
    }

    println(revList(List(1, 2, 3, 4, 5, 6))) //List(6, 5, 4, 3, 2, 1)
    println(revList(List("A", "B", "C", "D", "E", "F"))) //List(F, E, D, C, B, A)

    val exponent: BigDecimal = Math.pow(2, 5) // value defaults to Double
    println(exponent) // 32.0

    val sqrt: BigDecimal = Math.sqrt(5)
    println(sqrt)

    val nextDown: Double = Math.nextDown(3.0)
    println(nextDown) //2.9999999999999996

    println(Math.incrementExact(5)) //6

    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val numberFunc = numbers.foldLeft(List[Int]()) _

    val squares = numberFunc((acc, x) => acc :+ x * x)
    println(squares) //List(1, 4, 9, 16, 25, 36, 49, 64, 81, 100)

    def everyThird(xs: List[Int]): List[Int] = {
        if (xs.isEmpty) Nil
        else xs.head :: everyThird(xs.tail.drop(2))
    }

    println(everyThird(numbers))
    
    def reverseString(s: String): String = {
        s.foldLeft("")((acc, x) => x +: acc)
    }

    println(reverseString("reversed"))

    case class Person(name: String, age: Int)
    val people = List(Person("bob", 30), Person("ann", 32), Person("carl", 19))

    val collectX = people.collect { 
        case p @ Person(name, age) if age > 25 => p.copy(age = age + 1)
    }
    println(collectX) //List(Person(bob,31), Person(ann,33))

    val listx = List("This", "is", "a", "scala", "list", "of", "strings")
    val x47 = listx.map(_.distinct) //List(This, is, a, scal, list, of, string)
    val x48 = listx.map(_.distinct.size) //List(4, 2, 1, 4, 4, 2, 6)
    val x49 = listx.map(_.distinct.size).filter(_ >= 3).groupBy(_ >= 1) //HashMap(true -> List(4, 4, 4, 6))
    
    println(x47)
    println(x48)
    println(x49)

    val emptyL = List.empty
    println(emptyL.find(_ == 1)) // None
    // println(emptyL.init) // Exception
    println(emptyL.lastOption) // None
    println(listx.dropWhile(_.contains('i'))) //List(a, scala, list, of, strings)

    val listg = List(1, 2, 3, 4, 5)
    val listh = List(4, 5, 6, 7, 8)

    println(listg.diff(listh)) //List(1, 2, 3)
    println(listh.diff(listg)) //List(6, 7, 8)



    
}
