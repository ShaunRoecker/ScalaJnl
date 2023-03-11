
import zio._


object HOS1 extends scala.App {

    // FizzBuzz
    def fizzBuzz = for (i <- Range.inclusive(1, 100)) {
        if (i % 3 == 0 && i % 5 == 0) println("FizzBuzz")
        else if (i % 3 == 0) println("Fizz")
        else if (i % 3 == 0 && i % 5 == 0) println("Buzz")
        else println(i)
    }

    def fizzBuzz2 = for (i <- Range.inclusive(1, 100)) {
        println(
            if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
            else if (i % 3 == 0) "Fizz"
            else if (i % 3 == 0 && i % 5 == 0) "Buzz"
            else i
        )
    }

    // ToVector
    val fizzBuzzVector = for (i <- Range.inclusive(1, 100)) yield {
            if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
            else if (i % 3 == 0) "Fizz"
            else if (i % 3 == 0 && i % 5 == 0) "Buzz"
            else i.toString
    }

    // comprehensions
    val a = List(1, 2, 3, 4)

    val a2 = for(i <- a) yield i * i
    val a2m = a.map(x => x * x)

    val a3 = for(i <- a) yield "hello" + i
    val a3m = a.map("hello" + _)

    val a4 = for(i <- a if i % 2 == 0) yield "hello" + i
    val a4m = a.filter(_ % 2 == 0).map(x => "hello" + x)

    val al = List(1, 2)
    val bl = List("hello", "world")

    val flattened = for {
        i <- al
        s <- bl
    } yield s + i
    
    val flattened2 = for {
        s <- bl
        i <- al
    } yield s + i

}






