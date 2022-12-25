package patternmatching

package object PatternMatch:
    def Something: Unit = println("something")
// object AdvancedPatternMatching extends App {
@main
def main(): Unit =
    println("AdvancedPatternMatching")

        val numbers = List(1)
        def description(xs: List[Int]) =
            xs match 
                case head :: Nil => println(s"the only element is ${head}")
                case head :: tail => head match {
                    case 1 => println(s"the head value is 1")
                    case _ => println(s"the head value is not 1")
                }
                case Nil => println("List is Empty")
        
        description(numbers) //the only element is 1
        description(List(1, 2))

        

        





// }
