package functional.datastructures.functor

trait Functor[F[_]]:
    extension[A](x: F[A])
        def map[B](f: A => B): F[B]


given Functor[List] with
    extension[A](xs: List[A])
        def map[B](f: A => B): List[B] =
            xs.map(f)


