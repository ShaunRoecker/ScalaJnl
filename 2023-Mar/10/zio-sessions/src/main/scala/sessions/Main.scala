

object FizzBuzz {
    // flexible fizzbuzz
    def flexibleFizzBuzz(handleLine: String => Unit): Unit = {
        for (i <- Range.inclusive(1, 10)) {
            handleLine(
                if (i % 3 == 0 && i % 2 == 0) "FizzBuzz"
                else if (i % 3 == 0) "Fiz"
                else if (i % 5 == 0) "Buzz"
                else i.toString
            )
        }
    }
    
    flexibleFizzBuzz(s => println(s))
}


object PrintMessage {
    class Msg(val id: Int, val parent: Option[Int], val txt: String)

    def printMessages(messages: Array[Msg]): Unit = {
        def printFrag(parent: Option[Int], indent: String): Unit = {
            for (msg <- messages if msg.parent == parent) {
                println(s"$indent#${msg.id} ${msg.txt}")
                printFrag(Some(msg.id), indent + "    ")
            }
        }
        printFrag(None, "")
    }

    printMessages(Array(
        new Msg(0, None, "Hello"),
        new Msg(1, Some(0), "World"),
        new Msg(2, None, "I am Cow"),
        new Msg(3, Some(2), "Hear me moo"),
        new Msg(4, Some(2), "Here I stand"),
        new Msg(5, Some(2), "I am Cow"),
        new Msg(6, Some(5), "Here me moo, moo")
    ))

}

object CollectionsMethods extends scala.App {

    def stdDev(xs: List[Double]): Double = {
        val mean = xs.sum / xs.length
        val squareErrors = xs.map(_ - mean).map(x => x * x)
        math.sqrt(squareErrors.sum / xs.length)
    }

    println(stdDev(List(2, 5, 34, 23, 99, 535, 4))) //180.22027338447668

    // Factory methods
    List.fill(5)("hello")

    val listTab = List.tabulate(5)(x => x + 3) // x is the index
    println(listTab) //List(3, 4, 5, 6, 7)

    val d = (List(1, 2, 3) ++ List(4, 5, 6)) == (List(1, 2, 3) ::: List(4, 5, 6))
    println(d) //true

    println(List(1, 2, 3, 4, 5, 6, 7).slice(1, 4)) //List(2, 3, 4)

    val xs = List(1, 2, 6, 3, 7, 9 ,5, 7, 3, 1, 3, 6, 7, 9, 10)
    val uniqueVals = xs.distinct
    println(uniqueVals) //List(1, 2, 6, 3, 7, 9, 5, 10)

    val xs1 = List(1, 2, 3, 4, 6, 7, 8).find(x => x % 2 == 0 && x > 4)
    println(xs1)

    








}






