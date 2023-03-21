object Kata extends App {
    // multiplication table
    def mulTable(n: Int): List[List[Int]] = {
        List.tabulate(n, n){ (a, b) => (a + 1) * (b + 1) }
    }
    println(mulTable(4))
    //List(
        // List(1, 2,  3,  4), 
        // List(2, 4,  6,  8), 
        // List(3, 6,  9, 12), 
        // List(4, 8, 12, 16)
    //)

    // swap a tuple2
    def swap[A, B](tup: (A, B)): (B, A) =
        (tup._2, tup._1)

    val newTuple2 = swap(("a", 1))
    println(newTuple2) //(1,a)

    // rotate a tuple3
    def rotate[A, B, C](tup: (A, B, C)): (C, A, B) =
        (tup._3, tup._1, tup._2)

    val rotateTuple3 = rotate(("a", 1, true))
    println(rotateTuple3)

    // ****************************************************************
    // IsUnique- Implement an algorithm to determine if a 
    // string has all unique characters. What if you can't 
    // use the method .disctinct?

    def isUnique1(s: String): Boolean = {
        if (s.filterNot(_ == ' ').distinct == 
                s.filterNot(_ == ' ')) 
            true
        else 
            false
    }

    println(isUnique1("abcdefg tyup i")) //true
    println(isUnique1("abcdefg tyu pia")) //false

    // above holds even with spaces, the below would work 
    // if spaces aren't considered
    def isUnique2(s: String): Boolean = {
        s.distinct == s
    }

    println(isUnique2("abcdefg tyup i")) //false
    println(isUnique2("abcdefg tyu pia")) //false
    
    // A recursive solution
    def isUniqueRec(s: String, chrs: Set[Char] = Set()): Boolean = {
        s.length == 0 ||
        !chrs(s.head) && isUniqueRec(s.tail, chrs + s.head)
    }

    println(isUniqueRec("abcdefg tyupi")) //
    println(isUniqueRec("abcdefg tyupia")) //false

    // ****************************************************************
    // Check Permutation (two words wiith the same  character count): 
        // Given two strings, write a method to decide if one is
        // a permutation of the other
    
    def  checkPermutation(a: String, b: String): Boolean = {
        def sortMkString(s: String) = s.sorted.mkString
        (a == b) ||
        (a.length == b.length) && (sortMkString(a) == sortMkString(b))
    }

    println(checkPermutation("abcd", "abgd")) // false
    println(checkPermutation("abcd", "acdb")) // true

    // ****************************************************************
    // Write a method to replace all spaces in a string with '%20'

    def replaceSpaces(s: String): String = {
        s.trim.replaceAll("[ ]+", "%20")
    }

    println(replaceSpaces("Mr John  Smith  "))

    implicit val sorter: (Int, Int) => Boolean = _ > _

    def sortListOfInts(list: List[Int])(implicit sorter: (Int, Int) => Boolean): List[Int] =
        list.sortWith(sorter)

    println(Console.CYAN_B + 
            sortListOfInts(List(5, 3, 6, 8, 9, 1)) 
            + Console.RESET)
    


}
