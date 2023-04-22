import scala.annotation.tailrec


object Sessions {

    private def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = 
        @tailrec
        def loop(i: Int): Boolean =
            if (i == as.length - 1) ordered(as(i - 1), as(i))
            else if (! ordered(as(i - 1), as(i))) false
            else loop(i + 1)
        if (as.size == 0 || as.size == 1) true
        else loop(1)

    private def findFirstPolymorphic[A](as: Array[A], p: A => Boolean): Int = 
        @tailrec
        def loop(i: Int): Int =
            if (i >= as.length) -1
            else if (p(as(i))) i
            else loop(i + 1)
        loop(0)
    
    private def addOne(xs: List[Int]): List[Int] =
        for {
            i <- xs
        } yield i + 1

    
    

    def curry[A,B,C](f: (A, B) => C): A => (B => C) = 
        a => b => f(a, b)

    sealed trait List_[+A]
    case object Nil_ extends List_[Nothing]  // <- data constructor
    case class Cons_[+A](head: A, tail: List_[A]) extends List_[A]  // <- data constructor 
    
    object List_ { self =>

        def sum(ints: List_[Int]): Int =
            ints match
                case Nil_ => 0
                case Cons_(x, xs) => x + sum(xs)
            
        
        def product(ints: List_[Int]): Int =
            ints match
                case Nil_ => 1
                case Cons_(x, xs) => x * sum(xs)


        def apply[A](as: A*): List_[A] = // <- variadic function, meaning it accepts 
            if (as.isEmpty) Nil_               // zero of more elements of A
            else Cons_(as.head, apply(as.tail: _*)) // <- _* allows us to pass a Seq to a 
                                                            // variadic method


        def append[A](a1: List_[A], a2: List_[A]): List_[A] =
            a1 match
                case Nil_ => a2
                case Cons_(h, t) => Cons_(h, append(t, a2))


        def foldRight[A, B](as: List_[A], z: B, f: (A, B) => B): B =
            as match
                case Nil_ => z
                case Cons_(x, xs) => f(x, foldRight(xs, z, f))


        def sumViaFoldRight(xs: List_[Int]): Int =
            foldRight(xs, 0, (a, b) => a + b)


        def tail[A](xs: List_[A]): List_[A] =
            xs match
                case Nil_ => sys.error("tail of empty list")
                case Cons_(_, t) => t


        def dropWhile[A](xs: List_[A])(f: A => Boolean): List_[A] =
            xs match
                case Cons_(h, t) if f(h) => dropWhile(t)(f)
                case _ => xs


        def drop[A](xs: List_[A], n: Int): List_[A] =
            if (n <= 0) xs
            else xs match
                case Nil_ => Nil_
                case Cons_(_, t) => drop(t, n-1)


        def init[A](xs: List_[A]): List_[A] =
            xs match
                case Nil_ => sys.error("init of empty list")
                case Cons_(_, Nil_) => Nil_
                case Cons_(h, t) => Cons_(h, init(t))


        def init2[A](xs: List_[A]): List_[A] =
            xs match
                case Nil_ => sys.error("init of empty list")
                case Cons_(_, Nil_) => Nil_
                case Cons_(h, t) => Cons_(h, init(t))


        def contains[A](xs: List_[A], target: A): Boolean =
            xs match
                case Nil_ => false
                case Cons_(h, _) if (h == target) => true 
                case Cons_(h, t) => contains(t, target)


        def incrementEach(xs: List_[Int]): List_[Int] =
            foldRight(xs, Nil_ : List_[Int], (i, acc) => Cons_(i + 1, acc))

        
            

        
    }


    val a = List(1, 2, 3, 42) match
        case List(1, 2, _*) :+ 42 => true
        case _ => false

    
    extension(xs: List[Int])
        def increment1: List[Int] =
            xs.foldLeft(List[Int]()){ (acc, i) => acc :+ (i + 1) }    


    def main(args: Array[String]): Unit = 
        println(isSorted[Int]( Array(1, 2, 3, 4, 5), (a, b) => a < b ))
        println(isSorted[Int]( Array(1, 2, 3, 2, 5), (a, b) => a < b ))
        println(findFirstPolymorphic[String](Array("a", "b", "c"), _ == "c"))  // 2
        println(findFirstPolymorphic[String](Array("a", "b", "c"), _ == "d"))  // -1
        println(addOne(List(1, 2, 3))) // List(2, 3, 4)
        println(a) // true
        println(List_.contains(List_(1, 2, 3), 2)) // true
        println(List_.contains(List_(1, 2, 3), 4)) // false
        println(List(1, 2, 3, 4).increment1)






        
}