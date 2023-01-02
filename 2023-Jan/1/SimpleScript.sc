
case class Coordinates(x: Int, y: Int) {
  def moveX(offset: Int): Coordinates = Coordinates(x + offset, y)
  def moveY(offset: Int): Coordinates = Coordinates(x, y + offset)
}

val point = Coordinates(10, 15)
println(point.x)

val movedPoint = point.moveX(7)
println(movedPoint.x)

//watch mode: amm -w SimpleScript.sc
// runs the script each time it's saved

import $file.Constants, Constants._

println(s"Imported value: $aValue")
//Imported value: 5

// import external libraries
import $ivy.`com.typesafe.play::play:2.8.8` 


// Can parameterize the script
@main
def mainA(name: String, age: Int) = 
  println(s"Hello, $name. I'm $age")

// then, in ther terminal
//amm SimpleScript.sc John 28

// @main
// def mainB(fruits: String*) = {
//   fruits foreach {println(_)}
// }


// annomite supports multiple main methods,
// just call the one you want in the terminal

@main
def funA(arg: String): Unit = {
  println(s"""funA called with param "$arg"""")
}

@main
def funB(n: Int): Unit = {
  println(s"""funB called with param "$n"""")
}

// << amm SimpleScript.sc funB 22
// >> Hello, funB. I'm 22

import scala.io.Source

@main
def countLines(path: String): Unit = {
  val src = Source.fromFile(path)
  println(src.getLines.size)
  src.close()
}

in: amm SimpleScript.sc ./SimpleScript.sc
out: 64

relative paths in Ammonite start from the 
working directory where the script is being run.


can add documentation for the args
@arg(doc = "Sum two numbers and print out the result")
@main
def add(
  @arg(doc = "The first number")
  n1: Int,
  @arg(doc = "The second number")
  n2: Int
): Unit = {
  println(s"$n1 + $n2 = ${n1 + n2}")
}
in: amm SimpleScript.sc --n1 10 --n2 20
out: 10 + 20 = 30

{
  import javax.swing._, java.awt.event._
  val frame = new JFrame("Hello World Window")

  val button = new JButton("Click Me")
  button.addActionListener(new ActionListener{
    def actionPerformed(e: ActionEvent) = button.setText("You clicked the button!")
  })
  button.setPreferredSize(new java.awt.Dimension(200, 100))
  frame.getContentPane.add(button)
  frame.pack()
  frame.setVisible(true)

  }


