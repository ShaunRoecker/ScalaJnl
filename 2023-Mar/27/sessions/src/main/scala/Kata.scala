object Kata extends App {
   // transpose matrix
    def transposeM(matrix: List[List[Int]]): List[List[Int]] = matrix.transpose

    println(transposeM(List(List(1, 2), List(3, 4), List(5, 6))))
    // List(List(1, 3, 5), List(2, 4, 6))

    // String Methods

    // charAt
    println("Ayushi".charAt(1)) // y

    // compareTo
    // 0, if both strings are the same.
    // x, if string1 is greater than string2. The value of x is denoted by the difference of value of characters.
    // -x, if string1 is smaller than string2. The value of x is denoted by the difference of value of characters.
    
    println("Ayushi".compareTo("Ayushi")) // 0

    println("Ayushi".compareTo("Ayush")) // 1
    println("Ayushi".compareTo("Ayus")) // 2
    println("Ayushi".compareTo("Ayu")) // 3

    println("Ayushi".compareTo("Ayushi123")) // -3
    println("Ayushi".compareTo("you are")) // -56


    // compareToIgnoreCase
    println("Ayushi".compareToIgnoreCase("ayushi")) // 0
    println("Ayushi".compareToIgnoreCase("ayushi ")) // -1
    println("Ayushi".compareToIgnoreCase("ayush")) // 1

    // concat
    println("Ayushi".concat("Sharma")) // AyushiSharma

    // endsWith
    println("Ayushi".endsWith("i")) // true
    println("Ayushi".endsWith("sha")) // false

    //equalsIgnoreCase
    println("Ayushi".equalsIgnoreCase("aYuShi")) // true

    // getChars(srcBegin: Int, srcEnd: Int, dst: Array[Char], dstBegin: Int)

    println("fgafdg  afgf   ".trim)
    println("abc".getBytes("Unicode"))

    // Sequence methods:
    
    println(List(1, 2, 3, 4, 5).endsWith(List(4, 5))) //true
    println(List(1, 2, 3, 4, 5).endsWith(List(4, 6))) //false

    def orderedCount(chars: String): List[(Char, Int)] = {
        chars.distinct.map(c=>(c,chars.count(_ == c))).toList
    }

    println("gunwale".groupBy(identity))
    // HashMap(e -> e, n -> n, u -> u, a -> a, w -> w, g -> g, l -> l)

    if (List(1).isDefinedAt(0)) println("not empty")


    lazy val hasSlice = if (List(1, 2, 3, 4).containsSlice(List(2,3))) println("has slice")
    hasSlice

    val collectList = List(1, 2, 3, 4, 5, 6).collect{ 
        case x if (x + 3) % 2 == 0 => x
    }
    println(collectList) // List(1, 3, 5)

    val donuts: Seq[String] = Seq("Plain Donut", "Strawberry Donut", "Glazed Donut")
    println(donuts.groupBy(_.charAt(0)))
    // HashMap(G -> List(Glazed Donut), P -> List(Plain Donut), S -> List(Strawberry Donut))

    println(donuts.groupBy(_.split(" ").last.contains("no".reverse)))

    def orderedCount2(chars: String): List[(Char, Int)] =
        chars.filterNot(_ == ' ').distinct.map(char => (char, chars.count(_ == char))).toList


    println(orderedCount2("how many donuts do you have"))
    // List((h,2), (o,4), (w,1), (m,1), (a,2), (n,2), (y,2), (d,2), (u,2), (t,1), (s,1), (v,1), (e,1))

     






}
