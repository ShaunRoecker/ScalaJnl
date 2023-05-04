package functional.datastructures.errorhandling


trait Option[+A]
case object None extends Option[Nothing]
case class Some[A](get: A) extends Option[A]

object Option:
    extension[A](opt: Option[A])
        def map[B](f: A => B): Option[B] =
            opt match
                case Some(a) => Some(f(a))
                case None => None

        def flatMap[B](f: A => Option[B]): Option[B] =
            ???


        def filter(p: A => Boolean): Option[A] = ???

        
        
        def getOrElse[B >: A](default: => B): B =
            opt match
                case Some(a) => a
                case None => default

        def isEmpty: Boolean =
            opt match
                case Some(_) => false
                case None => true



object Various:
    extension(xs: List[Int])
        def mean: Option[Double] =
            if (xs.isEmpty) None
            else Some(xs.sum / xs.length)

