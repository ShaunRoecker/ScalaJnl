object TypeEnrichment extends App:
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Type Enrichment".toUpperCase)
    println()

    implicit class RichInt(value: Int):
        def isEven: Boolean = value % 2 == 0
        def sqrt: Double = Math.sqrt(value)

    println(42.isEven)

    def abs(n: Int): Int = if (n < 0) -n else n

    def formatAbs(x: Int): String =
        val msg = "The absolut value of %d is %d"
        msg.format(x, abs(x))

    println(formatAbs(-5))

    import scala.annotation.tailrec
    def sum(list: List[Int]): Int =
        @tailrec
        def sumrec(list: List[Int], acc: Int): Int = list match
            case Nil => acc + 1 //<- put here if you want it to only happen once
            case x :: xs => sumrec(xs, x + acc + 1) //<- put here if you want it happen every iter
        sumrec(list, 0)

    println(sum(List(1, 2, 3))) //10

    println(List(1, 2, 3).foldLeft(1)(_ + _ + 1)) //10

    trait Equal[T]:
        def apply[T](a: T, b: T): Boolean

    object Equal:
        def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = 
            equalizer.apply(a, b)

    implicit class TypeSafeEqual[T](value: T):
        def ===(other: T)(implicit equalizer: Equal[T]): Boolean =
            equalizer.apply(value, other)
        def !==(other: T)(implicit equalizer: Equal[T]): Boolean =
            ! equalizer.apply(value, other)

    



