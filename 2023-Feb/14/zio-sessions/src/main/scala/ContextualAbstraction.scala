

import zio._
import java.io.IOException

// object MyApp42 extends ZIOAppDefault {
//     trait DatabaseConnection

//     // An effect which requires DatabaseConnection to run
//     val effect: ZIO[DatabaseConnection, IOException, String] = ???

//     // A layer that produces DatabaseConnection service
//     val dbConnection: ZLayer[Any, IOException, DatabaseConnection] = ???

//     // After applying dbConnection to our environmental effect the reurned
//     // effect has no dependency on the DatabaseConnection
//     val eliminated: ZIO[Any, IOException, String] = 
//     dbConnection { // Provides DatabaseConnection context
//         effect       // An effect running within `DatabaseConnection` context
//     }
// }


case class AppConfig(host: String, port: Int, poolSize: Int)

object AppConfig {
  // Accessor Methods
  def host: ZIO[AppConfig, Nothing, String]  = ZIO.serviceWith(_.host) 
  def port: ZIO[AppConfig, Nothing, Int]     = ZIO.serviceWith(_.port)
  def poolSize: ZIO[AppConfig, Nothing, Int] = ZIO.serviceWith(_.poolSize)
}

val myApp: ZIO[AppConfig, Nothing, Unit] =
  for {
    host     <- AppConfig.host
    port     <- AppConfig.port
    _        <- ZIO.logInfo(s"The service will be service at $host:$port")
    poolSize <- AppConfig.poolSize
    _        <- ZIO.logInfo(s"Application started with $poolSize pool size")
  } yield ()
object MainApp extends ZIOAppDefault {
  def run = myApp.provide(ZLayer.succeed(AppConfig("localhost", 8080, 10)))
}


object ZIOEnv extends ZIOAppDefault {
    val environment: ZEnvironment[Console & Clock & Random & System] =
        ZEnvironment[Console, Clock, Random, System](
            Console.ConsoleLive,
            Clock.ClockLive,
            Random.RandomLive,
            System.SystemLive
        )
    val envToString = environment.toString

    def run = ZIO.succeed(envToString).debug

    //ZEnvironment(
        // Map(
            // Console -> zio.Console$ConsoleLive$@c7a206c, 
            // Clock -> zio.Clock$ClockLive$@329b340e, 
            // Random -> zio.Random$RandomLive$@37e2032c, 
            // System -> zio.System$SystemLive$@6caee25a
        // )
    // )
}