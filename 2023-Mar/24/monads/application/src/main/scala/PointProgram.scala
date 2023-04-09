import fplibrary.{Description}

object PointProgram {

  val input: String = prompt()
  val integerAmount: Int = convertStringToInteger(input)
  val positiveAmount: Int = ensureAmountIsPositive(integerAmount)
  val balance: Int = round(positiveAmount)
  val message: String = createMessage(balance)

  private val hyphens: String = 
    "\u2500" * 50

  private val question: String =
    "How much money would you like to deposit?"

  private def display(input: Any): Unit = {
    println(input)
  }

  private def prompt(): String = "5"
    // scala.io.StdIn.readLine()
  
  private def convertStringToInteger(input: String): Int =
    input.toInt

  private def ensureAmountIsPositive(amount: Int): Int =
    if (amount < 1) 1
    else amount

  private def round(amount: Int): Int = 
    if(isDivisibleByHundred(amount)) amount
    else round(amount + 1)

  private def isDivisibleByHundred(amount: Int): Boolean =
    if (amount % 100 == 0) true
    else false

  private def createMessage(balance: Int): String =
    s"Congratulations, you now have $$${balance}.}"
  

  def run(args: Array[String]): IO[Unit] = IO.create {
    display(hyphens)
    display(question)
    display(message)
    display(hyphens)
  }

  val list = List(1, 2, 3).flatMap()

}
