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

        def sum2: Int =
            as.foldRight(0)(_ + _)

        def product2: Int =
            as.foldRight(1)(_ * _)

    extension[A](xs: List[A])
        def ++(xs2: List[A]): List[A] =
            xs match
                case Nil => xs2
                case Cons(h, t) => Cons(h, t ++ xs2)
        
        def append(xs2: List[A]): List[A] =
            xs ++ xs2

        def tail: List[A] =
            xs match
                case Nil => sys.error("can't call tail on empty list")
                case Cons(_, t) => t

        def setHead(head: A): List[A] =
            xs match
                case Nil => sys.error("can't call setHead on empty list")
                case Cons(_, t) => Cons(head, t)

        def drop(n: Int): List[A] =
            if (n <= 0) xs
            else xs match
                case Nil => Nil
                case Cons(_, t) => tail.drop(n-1)

        def dropWhile(p: A => Boolean): List[A] =
            xs match
                case Cons(h, t) if p(h) => t.dropWhile(p)
                case _ => xs

        def init: List[A] =
            xs match
                case Nil => sys.error("init on empty list")
                case Cons(_, Nil) => Nil
                case Cons(h, t) => Cons(h, t.init)
            
        def initFaster: List[A] =
            import scala.collection.mutable.ListBuffer
            val buf = new ListBuffer[A]
            @annotation.tailrec
            def loop(curr: List[A]): List[A] =
                curr match
                    case Nil => sys.error("init on empty list")
                    case Cons(_, Nil) => List(buf.toList*)
                    case Cons(h, t) => 
                        buf += h;
                        loop(t)
            loop(xs) 

        def foldRight[B](acc: B)(f: (A, B) => B): B =
            xs match
                case Nil => acc
                case Cons(h, t) => f(h, t.foldRight(acc)(f))

        

        

        
                    




        

        

        
            

   
        

    








    