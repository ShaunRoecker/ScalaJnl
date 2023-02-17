package simpleapp.server

import zio._
import zio.http._
import zio.http.Server


final case class SimpleServer(simpleRoutes: SimpleRoutes):
    val routes: HttpApp[Any, Throwable] = simpleRoutes.routes

    def start: ZIO[Any, Throwable, Unit] =
        for {
            port <- System.envOrElse("PORT", "8080").map(_.toInt)
            _    <- Server.start(port, routes)            
        } yield ()



object SimpleServer:
    val layer: URLayer[SimpleRoutes, SimpleServer] =
        ZLayer.fromFunction(SimpleServer.apply _)

