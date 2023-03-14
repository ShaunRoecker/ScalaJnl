import zio._
import zio.kafka._


object Example extends ZIOAppDefault {
    trait Animal
    trait Cat extends Animal

    // implicitly tests that whatever is on the left with '<:<', is a subtype of whatever is on the right
    // if it isn't true it won't compile, if it is true it will just continue
    implicitly[Cat <:< Animal]

    implicitly[Nothing <:< Any]

    implicitly[Nothing <:< Cat <:< Animal <:< Any]

    val effect1: Task[Unit] = 
        for {
            time <- Clock.nanoTime
            _ <- Console.printLine(time.toString)
        } yield ()


    sealed trait PasswordError 

    sealed trait IOError extends PasswordError
    sealed trait InvalidPassword extends PasswordError
    
    def getInput: ZIO[Any, IOError, String] = 
        ???

    def validateInput(password: String): ZIO[Any, InvalidPassword, Unit] =
        ???

    def getAndValidateInput: ZIO[Any, PasswordError, Unit] =
        for {
            input <- getInput
            _ <- validateInput(input)
        } yield ()
        // alternatively... and better...
        // getInput.flatMap(validateInput)
    
    override def run = for {
        _ <- Console.printLine("ZIO")
        _ <- effect1
    } yield ()
    
    val fizzbuzz = for (i <- Range.inclusive(1, 20)) yield {
        (i % 3, i % 5) match {
            case (0, 0) => "fizzbuzz"
            case (0, _) => "fizz"
            case (_, 0) => "buzz"
            case _ => i
        }
    }
    println(fizzbuzz)
    
    
}
