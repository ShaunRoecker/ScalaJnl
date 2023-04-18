object Sessions:
    def main(args: Array[String]): Unit =
        
        case class Card(number: Int)

        def createCards(count: Int): Seq[Card] =
            (0 to count)
                .map(Card.apply)

        println(createCards(10))
    // Vector(Card(0), Card(1), Card(2), Card(3), Card(4), Card(5), Card(6), Card(7), Card(8), Card(9), Card(10))

        val fizzBuzz = for (i <- Range.inclusive(1, 15)) yield {
            val res = (i % 3, i % 5) match {
                case (0, 0) => "FizzBuzz"
                case (0, _) => "Fizz"
                case (_, 0) => "Buzz"
                case (_, _) => i.toString
            }
            res
        }
        println(fizzBuzz)

    
    