package simpleapp.server

import zio._
import zio.http._
import simpleapp.services.SimpleService

final case class SimpleRoutes(service: SimpleService):
    val routes: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {
        case Method.GET -> !! / "resource" =>
            service.getAll.map(pets => Response.json(resource.toJson))
    }

object SimpleRoutes:
    val layer: URLayer[SimpleService, SimpleRoutes] =
        ZLayer.fromFunction(SimpleRoutes.apply _)




