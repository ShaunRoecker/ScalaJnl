// Workthrough of the book "Hands-on Scala Programming" by Li Haoyi
//https://github.com/handsonscala/handsonscala/blob/v1/examples/5.3%20-%20PatternMatching/PatternMatching.sc

object HOS1{
    def getDayMonthYear(s: String) = s match {
        case s"$day-$month-$year" => println(s"day: $day month: $month year: $year")
        case _ => println("not a date")
    }

    getDayMonthYear("9-23-2023") //day: 9 month: 23 year: 2023

    def dayOfWeek(x: Int) = x match {
        case 1 => "Mon"; case 2 => "Tue"
        case 3 => "Wed"; case 4 => "Thu"
        case 5 => "Fri"; case 6 => "Sat"
        case 7 => "Sun"; case _ => "Unknown"
    }

    val fizzBuzz1 = for (i <- Range.inclusive(1, 100)) yield {
        val s = (i % 3, i % 5) match {
            case (0, 0) => "FizzBuzz"
            case (0, _) => "Fizz"
            case (_, 0) => "Buzz"
            case _ => i
        }
        s
    }

    assert(fizzBuzz1.take(5) == Seq(1, 2, "Fizz", 4, "Buzz"))

    val fizzBuzz2 = for (i <- Range.inclusive(1, 100)) yield {
        val s = (i % 3 == 0, i % 5 == 0) match {
            case (true, true) => "FizzBuzz"
            case (true, false) => "Fizz"
            case (false, true) => "Buzz"
            case (false, false) => i
        }
        s
    }

    assert(fizzBuzz2.take(5) == Seq(1, 2, "Fizz", 4, "Buzz"))

    case class Person(name: String, title: String)

    def greet2(husband: Person, wife: Person) = (husband, wife) match {
        case (Person(s"$first1 $last1", _), Person(s"$first2 $last2", _)) if last1 == last2 =>
            s"Hello Mr and Ms $last1"

        case (Person(name1, _), Person(name2, _)) => s"Hello $name1 and $name2"
    }

    assert(greet2(Person("James Bond", "Mr"), Person("Jane Bond", "Ms")) == "Hello Mr and Ms Bond")
    assert(greet2(Person("James Bond", "Mr"), Person("Jane", "Ms")) == "Hello James Bond and Jane")

    sealed trait Expr
    case class BinOp(left: Expr, op: String, right: Expr) extends Expr
    case class Literal(value: Int) extends Expr
    case class Variable(name: String) extends Expr

    def stringify(expr: Expr): String = expr match {
        case BinOp(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
        case Literal(value) => value.toString
        case Variable(name) => name
    }

    // Typeclasses
    trait StringParser[T] {
        def parse(s: String): T
    }

    object StringParser {
        implicit object ParseInt extends StringParser[Int] {
            def parse(s: String): Int = s.toInt
        }

        implicit object ParseBoolean extends StringParser[Boolean] {
            def parse(s: String): Boolean = s.toBoolean
        }

        implicit object ParseDouble extends StringParser[Double] {
            def parse(s: String): Double = s.toDouble
        }
    }
    import StringParser._

    def parseFromString[T](s: String)(implicit parser: StringParser[T]) =
        parser.parse(s)

    println(parseFromString[Int]("15"))
    println(parseFromString[Boolean]("true"))
    println(parseFromString[Double]("1.23"))

    def parseFromString2[T: StringParser](s: String) =
        implicitly[StringParser[T]].parse(s)

    println(parseFromString2[Int]("15"))
    println(parseFromString2[Boolean]("true"))
    println(parseFromString2[Double]("1.23"))



    



    





}

