package sessions

import zio._
import zio.optics._

object MyZIOApp extends ZIOAppDefault {
   
    def run = for {
        _ <- ZIO.unit
    } yield ()
}