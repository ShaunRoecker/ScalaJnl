package functional.datastructures
        


sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List:

    def apply[A](elems: A*): List[A] =
        if (elems.isEmpty) Nil
        else Cons(elems.head, apply(elems.tail: _*))


    extension(as: List[Int])
        def sum: Int =
            @annotation.tailrec
            def loop(list: List[Int], acc: Int): Int =
                list match
                    case Nil => acc
                    case Cons(h, t) => loop(t, h + acc)
            loop(as, 0)

        def product: Int =
            @annotation.tailrec
            def loop(list: List[Int], acc: Int): Int =
                list match
                    case Nil => acc
                    case Cons(h, t) => loop(t, h * acc)
            loop(as, 1)

    extension[A](first: List[A])
        def ++(second: List[A]): List[A] =
            first match
                case Nil => second
                case Cons(h, t) => Cons(h, t ++ second)
        
        def append(second: List[A]): List[A] =
            first ++ second

   
        

    








    