package sessions

import zio._
import zio.optics._

object MyZIOApp extends ZIOAppDefault {
    case class Person(name: String)

    object Person {
        val name: Lens[Person, String] =
            Lens(
                person => Right(person.name),
                newName => person => Right(person.copy(name = newName))
            )
    }

    val person = Person("John")
    val person2 = Person.name.set("Frank")(person)
    val person3 = Person.name.update(person)(_.toLowerCase)

    case class Name(firstName: FirstName)
    case class FirstName(value: String)

    object Name {
        val firstName: Lens[Name, FirstName] =
            Lens(
                name => Right(name.firstName),
                newFirstName => name => Right(name.copy(firstName = newFirstName))
            )
        
        val firstNameVal: Lens[FirstName, String] =
            Lens(
                firstName => Right(firstName.value),
                newValue => firstName => Right(firstName.copy(value = newValue))
            )
        val nameToFirstNameVal = firstName >>> firstNameVal
    }

    val name1 = Name(FirstName("Jane"))
    val name2 = Name.nameToFirstNameVal.update(name1)(_ + "t")
    val name3 = Name.nameToFirstNameVal.set("Jane2")(name1)

    sealed trait Direction
    case object North extends Direction
    case object South extends Direction
    case object East extends Direction
    case object West extends Direction

    val first = Lens.first
    val second = Lens.second

    val tuple2 = (1, "one")
    
    
    def run = for {
        _ <- ZIO.succeed(person2).debug //Right(Person(Frank))
        _ <- ZIO.succeed(person3).debug //Right(Person(john))
        _ <- ZIO.succeed(name1).debug //Name(FirstName(Jane))
        _ <- ZIO.succeed(name2).debug //Right(Name(FirstName(Janet)))
        _ <- ZIO.succeed(name3).debug //Right(Name(FirstName(Jane2)))
        

    } yield ()
}