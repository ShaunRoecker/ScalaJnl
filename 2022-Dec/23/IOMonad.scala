
package io_monad

class IO[A] private (constructorCodeBlock: => A) {

    def run = constructorCodeBlock

    def flatMapOrig[B](f: A => IO[B]): IO[B] = IO(f(run).run)

    def flatMap[B](customAlgorithm: A => IO[B]): IO[B] = {
        val result1: IO[B] = customAlgorithm(run)
        val result2: B = result1.run
        IO(result2)
    }

    def map[B](f: A => B): IO[B] = flatMap(a => IO(f(a)))

}

object IO {
    def apply[A](a: => A): IO[A] = new IO(a)
}

def getLine: IO[String] = IO(scala.io.StdIn.readLine())
def putStrLn(s: String): IO[Unit] = IO(println(s))

object IOMonad extends App {
   def loop: IO[Unit] = for {
        _     <- putStrLn("Type something: ")
        input <- getLine
        _     <- putStrLn(s"You said '$input'.")
        _     <- if (input.toLowerCase == "quit") putStrLn("") else loop  //RECURSE
    } yield ()

    loop.run
}

