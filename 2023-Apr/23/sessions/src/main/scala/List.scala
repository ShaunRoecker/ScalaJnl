
package fpinscala.datastructures.lists



enum List[+A]:
    case Nil 
    case Cons(head: A, tail: List[A])

object List:
    def sum(xs: List[Int]): Int =
        @annotation.tailrec
        def loop(list: List[Int], acc: Int): Int =
            list match
                case Nil => acc
                case Cons(h, tail) => loop(tail, acc + h)
        loop(xs, 0)

    
    def product(xs: List[Int]): Int =
        @annotation.tailrec
        def loop(list: List[Int], acc: Int): Int =
            list match
                case Nil => acc
                case Cons(h, tail) => loop(tail, acc + h)
        loop(xs, 0)

    def apply[A](as: A*): List[A] =
        if (as.isEmpty) Nil
        else Cons(as.head, apply(as.tail*))
    
    def append[A](a1: List[A], a2: List[A]): List[A] =
        a1 match
            case Nil => a2
            case Cons(h, tail) => Cons(h, append(tail, a2))

        
    def foldRight[A, B](xs: List[A], z: B, f: (A, B) => B): B =
        xs match
            case Nil => z
            case Cons(h, tail) => f(h, foldRight(tail, z, f))

    def sumViaFoldRight(xs: List[Int]): Int =
        foldRight(xs, 0, _ + _)

    def productViaFoldRight(xs: List[Int]): Int =
        foldRight(xs, 0, (a, b) => a * b)

    
    def tail[A](xs: List[A]): List[A] =
        xs match
            case Nil => sys.error("tail of empty list")
            case Cons(_, tail) => tail

    def setHead[A](xs: List[A], head: A): List[A] = 
        xs match
            case Nil => sys.error("setHead on empty list")
            case Cons(_,t) => Cons(head,t)

    
    def drop[A](xs: List[A], n: Int): List[A] =
        if (n <= 0) xs
        else xs match
            case Nil => Nil
            case Cons(_, tail) => drop(tail, n-1)

    def dropWhile[A](xs: List[A], f: A => Boolean): List[A] =
        xs match
            case Cons(h, t) if f(h) => dropWhile(t, f)
            case _ => xs


def main(args: Array[String]): Unit = 
    println("Lists")
    