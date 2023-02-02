

import zio._
import zio.Console._


object Example extends ZIOAppDefault {
  
    

    def run = for {
        _ <- Console.printLine("February 1st, 2023!")
        
    } yield ()
    //


}

