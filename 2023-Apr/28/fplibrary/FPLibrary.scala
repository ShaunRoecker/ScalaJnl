package fplibrary

type Thunk[+A] = () => A

final case class IO[+A](unsafeRun: Thunk[A]):
    override lazy val toString: String =
        "this is a description"
    
    def map[B](f: A => B): IO[B] = 
        IO.delay{ f(unsafeRun()) }

object IO:
    def delay[A](a: => A): IO[A] =
        IO(() => a)
