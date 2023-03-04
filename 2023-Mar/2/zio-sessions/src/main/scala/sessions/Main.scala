

import zio._
import zio.prelude._
import zio.prelude.newtypes._

object ZIOPrelude extends scala.App {

    object Topic extends Subtype[String]
    type Topic = Topic.Type

    object Votes extends Subtype[Int] {
        implicit val VotesAssociative: Associative[Votes] =
            new Associative[Votes] {
                def combine(l: => ZIOPrelude.Votes, r: => ZIOPrelude.Votes): ZIOPrelude.Votes =
                    Votes(l + r)
            }
    }
    type Votes = Votes.Type


    final case class VoteState(map: Map[Topic, Votes]) { self =>
        // def combine(that: VoteState): VoteState =
        //     VoteState {
        //         self.map.foldLeft(that.map) { case (map, (topic, votes)) =>
        //             map.get(topic) match {
        //                 case Some(newVotes) => map + (topic -> (votes + newVotes))
        //                 case None           => map + (topic -> votes)
        //         }
        //     }   
        // }

        def combinePrelude(that: VoteState): VoteState = 
            VoteState(self.map <> that.map)
    }

    val leftVotes = VoteState(Map(Topic("zio-http") -> Votes(4), Topic("uzi-http") -> Votes(2)))
    val rightVotes = VoteState(Map(Topic("zio-http") -> Votes(2), Topic("zio-tls-http") -> Votes(2)))

    // val combinedVotes = leftVotes.combine(rightVotes)
    // println(combinedVotes)

    val combinedVotesPrelude = leftVotes.combinePrelude(rightVotes)
    println(combinedVotesPrelude)

    val list1 = List(1, 2, 3, 4, 5, 6, 7, 8)
    val fl = list1.foldLeft(0) {
        (acc, x) => acc + x
    }

    println(fl) //36

    val map2 = Map.empty[String, Int]
    println(map2)

    val x4 = Map("a" -> 0, "b" -> 1).foldLeft(Map.empty[String, Int]) {
        case (acc, (key, value)) => acc + (key -> value)
    }

    println(x4) //Map(a -> 0, b -> 1)

    val x5 = Map("a" -> 0, "b" -> 1).foldLeft(x4) {
        case (acc, (key, value)) => 
            acc.get(key) match {
                case Some(newValue) => acc + (key -> (value + newValue))
                case None => acc + (key -> value)
            }
    }

    println(x5) //Map(a -> 0, b -> 2)


}

object Unchecked extends scala.App {
    sealed trait Expr
    case class Num(value: Int) extends Expr
    case class Var(value: String) extends Expr

    def describe(e: Expr): String =
        (e: @unchecked) match {
            case num @ Num(value) => s"Number: ${value}, ${num}"
            case va @ Var(value) => s"Variable: ${value}, ${va}"
        }

    println(describe(Var("foo")))

    def extract(box: Option[String]): String =
        box match {
            case Some(value) => value
            case None => ""
        }

    println(extract(Some("thing")))

    // Patterns Everywhere
    val myTuple = (123, "abc")
    val (number, string) = myTuple

    println(number) //123
    println(string) //abc

    case class Person(name: String, age: Int, profession: String)

    val person = Person("John Doe", 35, "Developer")
    val Person(name, age, profession) = person

    println(name) //John Doe
    println(age) //35
    println(profession) //Developer

    // Patterns in PartialFunctions

    // case sequences as entry points to the function
    val extractInt: Option[Int] => Int = {
        case Some(x) => x
        case None => 0
    }

    // Partial functions written with case sequences
    val second: PartialFunction[List[Int], Int] = {
        case x :: y :: _ => y
    }

    // The above gets translated to:

    // new PartialFunction[List[Int], Int]{
    //     def apply(xs: List[Int]) = xs match {
    //         case x :: y :: _ => y
    //     }

    //     def isDefinedAt(xs: List[Int]) = xs match {
    //         case x :: y :: _ => true
    //         case _ => false
    //     }
    // }

    val oneTwoThree: PartialFunction[Int, String] = {
        case 1 => "one"
        case 2 => "two"
        case 3 => "three"
    }

    val list = List(0, 1, 2, 4, 3, 5)

    println(list.collect(oneTwoThree)) //List(one, two, three)
    // Infix notation
    println(list collect oneTwoThree)

    // Patterns in for expressions
    val capitals: Map[String, String] = Map("France" -> "Paris", "Japan" -> "Tokyo")

    val forExprPatterns = for {
        (country, city) <- capitals
    } yield s"The capital of ${country} is ${city}"

    println(forExprPatterns) //List(The capital of France is Paris, The capital of Japan is Tokyo)

    val results = List(Some("apple"), None, Some("orange"))
    val fruits = for {
        Some(fruit) <- results 
    } yield fruit

    println(fruits) // List(apple, orange)


}