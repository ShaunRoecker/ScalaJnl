
import fplibrary.{Description, PointFreeProgram}

object Interpreter {
    def main(args: Array[String]): Unit = {
        print(Console.RED)

        lazy val description: Description[Unit] =
            PointFreeProgram.run(args)
        
        def interpret[A](description: Description[A]): A =
            description.apply()


        def upcr: Unit = println {
             Console.GREEN + 
                """_____  _________________________ 
                  |__  / / /__  __ \_  ____/__  __ \
                  |_  / / /__  /_/ /  /    __  /_/ /
                  |/ /_/ / _  ____// /___  _  _, _/ 
                  |\____/  /_/     \____/  /_/ |_|  """.stripMargin + 
                  "\n" +
                  Console.RESET 

        }

        upcr
        println(Console.GREEN)
        interpret(description)
        println(Console.RESET)
        
    
        

    }
}
