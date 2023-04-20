// commands:
// scala-cli setup-ide Script.scala
// scala-cli fmt Script.scala
// ^^ option: --save-scalafmt-conf

/* Sudoku.scala */
//> using scala "3.2.1"

final case class Sudoku private (date: Vector[Int])

object Sudoku {

  def from(s: String): Either[String, Sudoku] = ???
}
