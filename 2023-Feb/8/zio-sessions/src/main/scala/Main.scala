
import zio._

final case class Coord(x: Int, y: Int)

trait Parser:
  def parse(input: String): Coord


object ParserZIO extends ZIOAppDefault:

  def run = for {
    _ <- ZIO.unit
  } yield ()


object AppTwo extends ZIOAppDefault:
  def run = ZIO.unit.delay(1.second) *> Console.printLine("AppTwo")



object MainApp extends ZIOApp.Proxy( ParserZIO <> AppTwo )

