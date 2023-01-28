
// Onion Architecture 


// trait Issue
// trait HttpConfig


// trait BusinessLogic {
//     def run: ZIO[Any, Throwable, Unit]
// }
// trait Github { 
//     def getIssues(organization: String): ZIO[Any, Throwable, Chunk[Issue]] 
    
//     def postComment(issue: Issue, comment: Comment): ZIO[Any, Throwable, Unit]
// }
// trait Http { 
//     def get(url: String): ZIO[Any, Throwable, Chunk[Byte]] 
    
//     def post(url: String, body: Chunk[Byte]): ZIO[Any, Throwable, Chunk[Byte]]
// }


// final case class Comment(text: String) extends Issue

// final case class GithubLive(http: Http) extends Github { 
//     def getIssues(
//         organization: String
//     ): ZIO[Any, Throwable, Chunk[Issue]] = ???

//     def postComment( 
//         issue: Issue, 
//         comment: Comment
//     ): ZIO[Any, Throwable, Unit] = ???
// }

// final case class BusinessLogicLive(github: Github) extends BusinessLogic {
//     val run: ZIO[Any, Throwable, Unit] = for {
//         issues <- github.getIssues("zio")
//         comment = Comment("I am working on this!")
//         _ <- ZIO.getOrFail(issues.headOption).flatMap { issue =>
//                 github.postComment(issue, comment)
//             }
//     } yield () 
// }

// final case class HttpLive(config: HttpConfig) extends Http { 
//     def get(
//         url: String
//     ): ZIO[Any, Throwable, Chunk[Byte]] = ???

//     def post(
//         url: String,
//         body: Chunk[Byte]
//     ): ZIO[Any, Throwable, Chunk[Byte]] = ???

//     def start: ZIO[Any, Throwable, Unit] = ???

//     def shutdown: ZIO[Any, Nothing, Unit] = ???
// }

// // If a service doesn't require initialization or finalization,
// // you can simply use ZLayer.fromFunction to create the ZLayer
// object BusinessLogicLive {
//     val layer: ZLayer[Github, Nothing, BusinessLogic] =
//         ZLayer.fromFunction(BusinessLogicLive(_))
// }

// object GithubLive {
//     val layer: ZLayer[Http, Nothing, Github] = 
//         ZLayer.fromFunction(GithubLive(_))
// }

// // However, if a service does require initialization,
// // you create a for-comprehension to construct the ZLayer
// // and we can use the ZLayer.scoped constructor if we need to
// // implement any finalization for the service

// // Also, if your service depends on other services, you can
// // you can access other services with this pattern as well,
// // just add ZIO.service[Dependency]
// object HttpLive {
//     val layer: ZLayer[HttpConfig, Throwable, Http] = 
//         ZLayer.scoped {
//             for {
//                 config <- ZIO.service[HttpConfig]
//                 http <- ZIO.succeed(HttpLive(config))
//                 _ <- http.start 
//                 _ <- ZIO.addFinalizer(http.shutdown)
//             } yield http
//         }
// }

// object Main extends ZIOAppDefault {

//     val run = 
//         ZIO.serviceWithZIO[BusinessLogic](_.run)
//            .provide(
//                 BusinessLogicLive.layer,
//                 GithubLive.layer,
//                 HttpLive.layer
//             )
// }

// // To compose layers:
// val githubLayer: ZLayer[Any, Throwable, Github] = ZLayer.make[Github](
//     GithubLive.layer,
//     HttpLive.layer
//   )

// // use provideSome to leave out services
// val testable: ZIO[Http, Throwable, Unit] = ZIO
//     .serviceWithZIO[BusinessLogic](_.run)
//     .provideSome[Http](
//       BusinessLogicLive.layer,
//       GithubLive.layer
//     )



