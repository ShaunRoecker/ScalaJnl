
import zio._
import zio.prelude._
import zio.prelude.newtypes._


import scala.util.matching.Regex




object One extends scala.App {
    val func: PartialFunction[Int, String] = {
        case 0 => "zero"
    }

    val list1 = List(0, 1, 2, 0)
    println(list1.collect(func)) //List(zero, zero)

}

object TypeClassSummable extends scala.App {
    // Type Class Pattern 

    trait Summable[T] {  // <- type class
        def sumElements(list: List[T]): T
    }

    implicit object IntSummable extends Summable[Int] { // <- type class instance
        override def sumElements(list: List[Int]) = list.sum
    }

    implicit object StringSummable extends Summable[String] {
        override def sumElements(list: List[String]) = list.mkString("")
    }

    def processMyList[T](list: List[T])(implicit summable: Summable[T]): T = { //ad-hoc polymorphism
        summable.sumElements(list)
    }

    val intSum = processMyList(List(1,2,3))
    val stringSum = processMyList(List("Scala ", "is ", "awesome"))

    println(intSum)
    println(stringSum)

}

object TypeClassHtmlWriter extends scala.App {

    trait HtmlWriter[T] {
        def convertToHtml(value: T): String
    }

    implicit object StringHtmlWriter extends HtmlWriter[String] {
        override def convertToHtml(value: String) = s"<h1>${value}</h1>"
    }

    implicit object IntHtmlWriter extends HtmlWriter[Int] {
        override def convertToHtml(value: Int) = s"<h1>${value.toString}</h1>"
    }

    case class Person(name: String, age: Int)

    implicit object PersonHtmlWriter extends HtmlWriter[Person] {
        override def convertToHtml(value: Person) = {
            s"<div><h1>Name: ${value.name}<h1><h2>Age: ${value.age}<h2></div>"
        }
    }

    def toHtml[T](item: T)(implicit htmlWriter: HtmlWriter[T]): String = {
        htmlWriter.convertToHtml(item)
    }

    val string = "This is a String"
    val integer = 14563
    val person = Person("John", 45)

    println(toHtml(string)) // <h1>This is a String</h1>
    println(toHtml(integer)) // <h1>14563</h1>
    println(toHtml(person))




}

object Two extends scala.App {
    def stripDot(s: String): String = {
        if (s.endsWith(".0")) s.substring(0, s.length - 2)
        else s
    }

    val regex = new Regex(".ot")
    val replaceAR = regex.replaceAllIn("potdotnothotokayslot","**") // <- Regex method
    println(replaceAR) //********okays**

    val replaceA = "potdotnothotokayslot".replaceAll(".ot", "**") // <- String method
    println(replaceA) //********okays**
    
    val replaceF = "potdotnothotokayslot".replaceFirst(".ot", "**")
    println(replaceF) //**dotnothotokayslot

    val split = "xpotxdotynotzhotokayslot".split(".ot").toList
    println(split) //List(x, x, y, z, okays)

    val splitLimit = "xpotxdotynotzhotokayslot".split(".ot", 2).toList
    println(splitLimit) //List(x, xdotynotzhotokayslot) // 2 -> the number of members in the list
    
}

object TypeClassTemplate extends scala.App {

    case class User(name: String, email: String)

    // TYPE CLASS
    trait Equal[T] {
        def apply(a: T, b: T): Boolean
    }

    object TCInstances {
        implicit object NameEquality extends Equal[User] {
            override def apply(a: User, b: User): Boolean = 
                a.name == b.name
        }

        implicit object EmailEquality extends Equal[User] {
            override def apply(a: User, b: User): Boolean = 
                a.email == b.email
        }

        implicit object FullEquality extends Equal[User] {
            override def apply(a: User, b: User): Boolean = 
                a.name == b.name && a.email == b.email
        }
    }
    import TCInstances._

    object EqualityChecker {
        def equalityCheck[A](a: A, b: A)(implicit equilizer: Equal[A]): Boolean =
            equilizer.apply(a, b)

    }

    import EqualityChecker._
    
    val user1 = User("John", "blah@gmail.com")
    val user2 = User("Sarah", "blah@gmail.com")

    println(equalityCheck(user1, user2)(FullEquality)) //false
    println(equalityCheck(user1, user2)(EmailEquality)) //true



}

object ListOps extends scala.App {
    val list1 = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val emptList = List.empty[Int]

    val List(a, b, c, _*) = list1

    val List(_, d, _, _, e, _*) :+ last = list1

    val x :: y :: rest = list1


    println(a) // 1
    println(b) // 2
    println(c) // 3

    println((d, e)) // (2, 5)

    println(last) // 9

    println((x, y, rest)) // (1,2,List(3, 4, 5, 6, 7, 8, 9))

    def isort(xs: List[Int]): List[Int] =
        xs match {
            case List() => List()
            case x :: xs1 => insert(x, isort(xs1))
        }

    def insert (x: Int, xs: List[Int]): List[Int] = 
        xs match {
            case List() => List(x)
            case y :: ys => if (x <= y) x :: xs
                            else y :: insert(x, ys)
        }

    val lista = List(1, 2, 3)
    val listb = List(4, 5, 6)

    val concatLists = lista ::: listb
    println(concatLists) // List(1, 2, 3, 4, 5, 6)
    println(concatLists.reverse) // List(6, 5, 4, 3, 2, 1)
    println(lista) // List(1, 2, 3)


    def append(xs: List[Int], ys: List[Int]): List[Int] =
        xs match {
            case List() => ys match {
                            case List() => List()
                            case y1 :: ys1 => ys
                        }
            case x1 :: xs1 => ys match {
                            case List() => xs
                            case y1 :: ys1 => xs ::: ys
                        }
        }

    println(append(List(1, 2, 3), List(4, 5, 6))) //List(1, 2, 3, 4, 5, 6)
    println(append(List(1, 2, 3), List()))

    def appendRec(xs: List[Int], ys: List[Int]): List[Int] =
        xs match {
            case List() => ys
            case x :: xs1 => x :: appendRec(xs1, ys)
        }
    

    
    





}

object ZPrelude extends scala.App {
    object SequenceNumber extends Newtype[Int] {
        implicit class SequenceNumberExt(private val self: SequenceNumber) extends AnyVal {
            def next: SequenceNumber =
                SequenceNumber.wrap(SequenceNumber.unwrap(self) + 1)
        }
    }
    type SequenceNumber = SequenceNumber.Type

    val sequenceNumber: SequenceNumber =
        SequenceNumber(1)

    val nextSequenceNumber: SequenceNumber =
        sequenceNumber.next


    println(sequenceNumber) // 1
    println(nextSequenceNumber) // 2


}

object AssociativeTC extends scala.App {

    trait Associative[T] {
        def combine(left: T, right: T): T
    }

    




}