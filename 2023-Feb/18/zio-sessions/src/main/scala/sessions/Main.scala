package sessions

import zio._
// import zio.http._
// import zio.http.model.Method
import zio.Console._
import zio.prelude._
import zio.prelude.newtypes._
import zio.test.Assertion._


object App1 extends scala.App { 

    // object Name extends Subtype[String]
    // type Name = Name.Type
    
    object Name extends SubtypeSmart[String](hasSizeString(isGreaterThan(0)))
    type Name = Name.Type

    def nameLength(name: Name): Int = name.length
    // implicitly(String <:< Name)

    val name = Name.make("Violet")

    final case class User(name: String, age: Int)

    def validateUser(name: String, age: Int): Validation[String, User] =
        Validation.validateWith(validateName(name), validateAge(age)){ (name, age) =>
            User(name, age)
        }

    def validateName(name: String): Validation[String, String] =
        if (name.length > 0 && name.length < 20) Validation.succeed(name)
        else Validation.fail("Name was too long")

    def validateAge(age: Int): Validation[String, Int] =
        if (age > 0 && age <= 100) Validation.succeed(age)
        else Validation.fail("Age out of range")

    val validateUser: Validation[String, User] = validateUser("", -1)

    println(validateUser)

    

    


    // def run = ZIO.unit
}


// object HelloWorld extends ZIOAppDefault {

//   val app: HttpApp[Any, Nothing] = Http.collect[Request] {
//     case Method.GET -> !! / "text" => Response.text("Hello World!")
//   }

//   override val run =
//     Server.serve(app).provide(Server.default)
// }