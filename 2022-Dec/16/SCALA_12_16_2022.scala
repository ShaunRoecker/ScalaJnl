object SCALA_12_16_2022 extends App {
    println("Hello World!")

    import scala.collection.mutable.StringBuilder
    // def strbldr(s: String): String =
    //     val sb = StringBuilder("")

    // DAILY CTCI 1.1 && 1.2
    /////////////////////////////////////////////////////////
    // 1.1 (pg.90)
    // checking if an input string has all unique characters
    def strCharIsUnique(str: String): Boolean =
        if (str.distinct == str) then true else false
    
    println(strCharIsUnique("abcdefghijklmnopqrstuvwxyzABCDEF"))

    // another idea:
    def strCharIsUnique2(str: String): Boolean =
        if (str.toSet.size == str.size) then true else false
    
    println(strCharIsUnique2("abcdefghijklmnopqrstuvwxyzABCDEF"))


    // 1.2 (pg.90)
    // check permutation: Given two strings write a method to decide
    // if one is a permutation of the other

    def isPermuation(s1: String, s2: String): Boolean = 
        if s1.sorted == s2.sorted then true else false

    println(isPermuation("ACBAB", "BCAAB")) //true
    println(isPermuation("ACBAB", "CAAB")) //false
    /////////////////////////////////////////////////////////

    case class Person(name: String = "Unknown")
    val personList = Vector(Person("A"), Person("B"), Person("C"))
    println(personList) //Vector(Person(A), Person(B), Person(C))

    def aFunction(personList: Vector[Person]): Vector[Person] = 
        def isA(s: String): String =
            s match
                case "A" => "Alpha"
                case "B" => "Bravo"
                case "C" => "charlie"
                case _ => s
        
        personList.map(x => x.copy(name = isA(x.name) + "X"))
        
    
    val newPersonList = aFunction(personList)
    println(newPersonList) //Vector(Person(AX), Person(BX), Person(CX))











}
