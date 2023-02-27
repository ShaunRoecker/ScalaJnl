package sessions

import zio._
import zio.Console._
import zio.prelude._
import zio.prelude.newtypes._


  /**
    * Key Points:
        1. Data Type Validation
        2. Newtypes and Subtypes
        3. 
    */
object Zym extends scala.App {
    final case class VoteState(mapV: Map[String, Int]) { self =>
        def combine(that: VoteState): VoteState =
            VoteState {
                self.mapV.foldLeft(that.mapV) { case (mapV, (topic, votes)) =>
                        mapV.get(topic) match {
                            case Some(currentVotes) => mapV + (topic -> (currentVotes + votes))
                            case None => mapV + (topic -> votes)
                        }    
                }
        }
    }
}

object ValidationEx extends scala.App {
    import Assertion._

    // Type safety for custom types with no runtime overhead...
    object Name extends Subtype[String]
    type Name = Name.Type

    // Subtype retains the methods for string, but is still considered a different 
    // type altogether

    def nameLength(name: Name): Int = name.length

    // 'Newtype' is the same thing but doesn't inherit the methods from the 
    // type it is modeling



    // This is for scala 3, scala 2 is a little different
    // scala3: SmartTypes: 
        // https://github.com/zio/zio-prelude/blob/df64514cc982fe612039c6f762edf14d632feac4/examples/shared/src/main/scala-3/examples/SmartTypes.scala
    // scala2: SmartTypes:
        // https://github.com/zio/zio-prelude/blob/df64514cc982fe612039c6f762edf14d632feac4/examples/shared/src/main/scala-2/examples/SmartTypes.scala
    
    type Age = Age.Type
    object Age extends Subtype[Int] {
        override inline def assertion = {
            greaterThanOrEqualTo(0) && lessThanOrEqualTo(150)
        }
    }

    import Regex.*

    type MyRegex = MyRegex.Type
    object MyRegex extends Newtype[String] {
        override inline def assertion = {
        matches {
            start ~ 
                anyChar ~ 
                alphanumeric ~ 
                (nonAlphanumeric | whitespace) ~ 
                nonWhitespace ~ 
                digit.min(0) ~ 
                nonDigit.min(1) ~
                literal("hello") ~ 
                anyCharOf('a', 'b', 'c').min(2) ~ 
                notAnyCharOf('d', 'e', 'f').? ~
                inRange('a', 'z').max(2) ~ 
                notInRange('1', '5').min(1).max(3) 
            ~ end
        }
        }
    }

    val myRegex: MyRegex = MyRegex("ab#l*helloccayj678")
    println(myRegex)

    type Email = Email.Type
    object Email extends Subtype[String] {
        override inline def assertion = {
            matches {
                start
                    ~ anyRegexOf(alphanumeric, literal("-"), literal("\\.")).+
                    ~ literal("@")
                    ~ anyRegexOf(alphanumeric, literal("-")).+
                    ~ literal("\\.").+
                    ~ anyRegexOf(alphanumeric, literal("-")).between(2, 4)
                ~ end
            }
        }
    }

    val email: Email = Email("test@test.com")
    println(email)


    final case class User(name: String, age: Int)

    def validateUser(name: String, age: Int): Validation[String, User] =
        Validation.validateWith(validateName(name), validateAge(age)) { 
            (name, age) => User(name, age)
        }

    def validateName(name: String): Validation[String, String] =
        if (name.length > 0 && name.length < 20) Validation.succeed(name)
        else Validation.fail("Name is too long")

    def validateAge(age: Int): Validation[String, Int] = 
        if (age < 0 || age > 100) Validation.fail("Age out of range!")
        else Validation.succeed(age)

    val validatedUser1 = validateUser("Alan", 33)
    val validatedUser2 = validateUser("", -1)
    println(validatedUser1)
    println(validatedUser2)

    

    
}