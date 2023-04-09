package sessions

import zio._
import zio.optics._


object MyZIOApp extends ZIOAppDefault {
    // case class Optic[-GetWhole, -SetWholeBefore, -SetPiece, +GetError, +SetError, +SetPiece, +SetWholeAfter](
    //     get: GetWhole => Either[GetError, GetPiece],
    //     set: SetPiece => SetWholeBefore => SetWholeAfter
    // )

    // type Lens[Whole, Piece] = Optic[Whole, Whole, Piece, Nothing, Nothing, Piece, Whole]
    // type Prism[Whole, Piece] = Optic[Whole, Any, Piece, OpticFailure, Nothing, Piece, Whole]

   
    def run = for {
        _ <- ZIO.unit
    } yield ()
}