package functional.datastructures.strictness

import functional.datastructures.functor.Functor
import Stream._

sealed trait Stream[+A] extends Product with Serializable { self =>
    
    def headOption: Option[A] =
        self match
            case Empty => None
            case Consx(h, _) => Some(h())

    
    // not tail recursive
    def toListRecursive: List[A] =
        self match
            case Empty => List()
            case Consx(h, t) => h() :: t().toListRecursive

        
    def toList: List[A] =
        @annotation.tailrec
        def go(s: Stream[A], acc: List[A]): List[A] =
            s match
                case Consx(h, t) => go(t(), h() :: acc)
                case _ => acc
        go(self, List()).reverse

    // in order to avoid using reverse at the end...

    def toListFast: List[A] =
        val buf = new collection.mutable.ListBuffer[A]()
        @annotation.tailrec
        def go(s: Stream[A]): List[A] =
            s match
                case Consx(h, t) => buf += h(); go(t())
                case _ => buf.toList
        go(self)


    def take(n: Int): Stream[A] = 
        self match
            case Consx(h, t) if n > 1 => cons(h(), t().take(n-1))
            case Consx(h, _) if n == 1 => cons(h(), empty)
            case _ => empty


    def takeWhile(p: A => Boolean): Stream[A] =
        self match
            case Consx(h, t) if p(h()) => cons(h(), t().takeWhile(p))
            case _ => empty
            

    @annotation.tailrec
    final def drop(n: Int): Stream[A] =
        self match
            case Consx(_, t) if n > 0 => t().drop(n-1)
            case _ => self


    
    def foldRight[B](acc: => B)(f: (A, B) => B): B =
        self match
            case Consx(h, t) => f(h(), t().foldRight(acc)(f))
            case _ => acc
            

    
    def exists(p: A => Boolean): Boolean =
        self match
            case Consx(h, t) => p(h()) || t().exists(p)
            case _ => false  

    def existsFR(p: A => Boolean): Boolean =
        self.foldRight(false)((i, acc) => p(i) || acc)

    
    
    
    
}
case object Empty extends Stream[Nothing]
case class Consx[+A](h: () => A, t: () => Stream[A]) extends Stream[A]     


object Stream {

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
        lazy val head = hd
        lazy val tail = tl
        Consx(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] =
        if (as.isEmpty) empty
        else cons(as.head, apply(as.tail: _*))

}


