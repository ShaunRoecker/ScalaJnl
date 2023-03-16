

object Kata extends scala.App {

    // uniqueInOrder("AAAABBBCCDAABBB")   == List('A', 'B', 'C', 'D', 'A', 'B')
    // uniqueInOrder("ABBCcAD")           == List('A', 'B', 'C', 'c', 'A', 'D')
    // uniqueInOrder(List(1, 2, 2, 3, 3)) == List(1, 2, 3)

    def uniqueInOrder[T](xs: Iterable[T]): Seq[T] =
        if (xs.isEmpty) Nil
        else xs.head +: uniqueInOrder(xs.dropWhile(_ == xs.head))

    println(uniqueInOrder("AAAABBBCCDAABBB"))

    ////////////////////////////////////////////////

    // "is2 Thi1s T4est 3a"  -->  "Thi1s is2 3a T4est"
    // "4of Fo1r pe6ople g3ood th5e the2"  -->  "Fo1r the2 g3ood 4of th5e pe6ople"
    // ""  -->  ""

    def orderNaive(str: String) = {
        def matchNum(s: String) = {
            val rgx = """[1-9]""".r
            val matched = rgx.findFirstMatchIn(s)
            matched match {
                case Some(value) => value.toString
                case None => ""    
            }
        }
        str.split(" ").toList.sortWith{ (a, b) =>
            matchNum(a) < matchNum(b)   
        }.mkString(" ")
        
    }
    
    def orderBetter(str: String): String =
        str.split(' ').sortBy(_.find(_.isDigit)).mkString(" ")

    def orderBest(str: String): String =
        str.split(' ').sortBy(_.sorted).mkString(" ")

    println(orderNaive("is2 Thi1s T4est 3a"))
    println(orderBetter("is2 Thi1s T4est 3a"))
    println(orderBest("is2 Thi1s T4est 3a"))


    ////////////////////////////////////////
    // Input -> Output
    // "8 j 8   mBliB8g  imjB8B8  jl  B" -> "8j8mBliB8gimjB8B8jlB"
    // "8 8 Bi fk8h B 8 BB8B B B  B888 c hl8 BhB fd" -> "88Bifk8hB8BB8BBBB888chl8BhBfd"
    // "8aaaaa dddd r     " -> "8aaaaaddddr"

    def removeSpaces(s: String): String = s.replaceAll(" ", "")

    println(removeSpaces("8 j 8   mBliB8g  imjB8B8  jl  B"))

    /////////////////////////////////////////

    def isDivisible(n: Int, a: Int, b: Int): Boolean =
        n % a == 0 && n % b == 0

    println(isDivisible(16, 2, 4))
    println(isDivisible(16, 5, 2))

    /////////////////////////////////////////
    // "the-stealth-warrior" gets converted to "theStealthWarrior"

    // "The_Stealth_Warrior" gets converted to "TheStealthWarrior"

    // "The_Stealth-Warrior" gets converted to "TheStealthWarrior"

    def toCamelCaseNaive(s: String) = {
        val xs = s.split("[-_]").toList
        (xs.head :: xs.tail.map(_.capitalize)).mkString
    }

    println(toCamelCaseNaive("the-stealth-warrior"))
    println(toCamelCaseNaive("the_stealth_warrior"))

    // don't need to convert to list...
    // :: takes List and produces List, 
        // while +: is more generic form, 
            // that can build other collections - like Vector and others

    def toCamelCaseBetter(s: String): String = {
        val xs = s.split("[-_]")
        (xs.head +: xs.tail.map(_.capitalize)).mkString
    }

    println(toCamelCaseBetter("the-stealth-warrior"))
    println(toCamelCaseBetter("the_stealth_warrior"))
    

    def toCamelCaseBest(str: String): String = 
        str.split("[_-]").reduce((a, b) => a + b.capitalize)

    println(toCamelCaseBest("the-stealth-warrior"))
    println(toCamelCaseBest("the_stealth_warrior"))

    // Ordering ADTs

    final case class Person(name: String, age: Int)

    object Person { self =>
        implicit object nameOrdering extends Ordering[Person] {
            override def compare(a: Person, b: Person) =
                a.name.compare(b.name)
        }
    }

    val personList = List(Person("Zlex", 20), Person("Aed", 30))
    println(personList.sorted)

    val sortedWithName = personList.sortWith(_.name > _.name)
    println(sortedWithName)

    ////////////////////////////////////////////////////////////
    def stringRepeat(n: Int, s: String): String = {
        List.fill(n)(s).mkString
    }

    println(stringRepeat(5, "Hello"))

    

    
}
