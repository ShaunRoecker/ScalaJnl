
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

    
    def apply[A](xs: A*): List[A] =
        if (xs.isEmpty) Nil
        else Cons(xs.head, apply(xs.tail*))

    def append[A](a1: List[A], a2: List[A]): List[A] =
        a1 match
            case Nil => a2
            case Cons(head, tail) => Cons(head, append(tail, a2))

    def foldRight[A, B](xs: List[A], z: B, f: (A, B) => B): B =
        xs match 
            case Nil => z
            case Cons(head, tail) => f(head, foldRight(tail, z, f))

    def sumViaFoldRight(xs: List[Int]): Int =
        foldRight(xs, 0, _ + _)

    extension[A](as: List[A])
        def tail: List[A] =
            as match
                case Nil => sys.error("tail on empty list")
                case Cons(_, t) => t

    def setHead[A](xs: List[A], h: A): List[A] = 
        xs match
            case Nil => sys.error("called setHead on empty list!")
            case Cons(_, tail) => Cons(h, tail)


    def drop[A](xs: List[A], n: Int): List[A] =
        if (n <= 0) xs
        else xs match
            case Nil => Nil
            case Cons(_, t) => drop(t, n-1)

    def take[A](xs: List[A], n: Int): List[A] =
        if (n <= 0) Nil
        else xs match
            case Nil => Nil
            case Cons(h, t) => Cons(h, take(t, n-1))

    
    def dropWhile[A](xs: List[A], p: A => Boolean): List[A] =
        xs match
            case Cons(h, t) if p(h) => dropWhile(t, p)
            case _ => xs
            
    
    def init[A](xs: List[A]): List[A] =
        xs match
            case Nil => sys.error("empty list")
            case Cons(_, Nil) => Nil
            case Cons(h, t) => Cons(h, init(t))

    extension[A, B](xs: List[A])
        def init2: List[A] =
            import collection.mutable.ListBuffer
            val buf = new ListBuffer[A]
            @annotation.tailrec
            def loop(cur: List[A]): List[A] =
                cur match
                    case Nil => sys.error("init of empty list")
                    case Cons(_, Nil) => List(buf.toList*)
                    case Cons(h, t) => buf += h; loop(t)
            loop(xs)

        def length: Int =
            foldRight(xs, 0, (_, acc) => acc + 1)

        @annotation.tailrec
        def foldLeft(acc: B)(f: (B, A) => B): B = 
            xs match
                case Nil => acc
                case Cons(h, t) => t.foldLeft(f(acc, h))(f)
            
        
    def foldLeft2[A, B](xs: List[A], acc: B, f: (B, A) => B): B =
        xs match
            case Nil => acc
            case Cons(h, t) => foldLeft2(t, f(acc, h), f)
        
    def sumViaFoldLeft(xs: List[Int]): Int = 
        xs.foldLeft(0)((a, b) => a + b)

    def lengthViaFoldLeft(xs: List[Int]): Int =
        xs.foldLeft(0)((acc, _) => acc + 1)

    def reverse[A](xs: List[A]): List[A] =
        xs.foldLeft(List[A]()){ (acc, i) => Cons(i, acc) }


    // Note: for foldRight the accumulator is on the RIGHT, and with foldLeft
    // the accumulator is on the LEFT

    def foldRightViaFoldLeft[A, B](xs: List[A], acc: B, f: (A, B) => B): B =
        foldLeft2(reverse(xs), acc, (b, a) => f(a, b))

    def foldLeftViaFoldRight[A, B](xs: List[A], acc: B, f: (B, A) => B): B =
        foldRight(xs, (b: B) => b , (a, g) => b => g(f(b, a)))(acc)


    def appendViaFoldRight[A](a1: List[A], a2: List[A]): List[A] =
        foldRight(a1, a2, Cons(_, _))

    def concat[A](xs: List[List[A]]): List[A] =
        foldRight(xs, Nil: List[A], append)

    def append2[A](a1: List[A], a2: List[A]): List[A] =
        a1 match
            case Nil => a2
            case Cons(h, t) => Cons(h, append(t, a2))

    
    def incrementEach(xs: List[Int]): List[Int] =
        foldRight(xs, Nil: List[Int], (i, acc) => Cons(i + 1, acc))


    def doubleToString(xs: List[Double]): List[String] =
        foldRight(xs, Nil: List[String], (i, acc) => Cons(i.toString, acc))

    
    // map

    def map[A, B](xs: List[A], f: A => B): List[B] =
        foldRight(xs, Nil: List[B], (h, t) => Cons(f(h), t))

    def map1[A, B](xs: List[A], f: A => B): List[B] =
        foldRightViaFoldLeft(xs, Nil: List[B], (h, t) => Cons(f(h), t))

    def map_2[A, B](l: List[A], f: A => B): List[B] =
        val buf = new collection.mutable.ListBuffer[B]
        def go(l: List[A]): Unit = l match
            case Nil => ()
            case Cons(h, t) => buf += f(h); go(t)
        go(l)
        List(buf.toList*) // converting from the standard Scala list to the list we've defined here
            
    // filter

    def filter[A](xs: List[A], f: A => Boolean): List[A] =
        foldRight(xs, Nil: List[A], (h, t) => if f(h) then Cons(h, t) else t)

    def filter2[A](xs: List[A], f: A => Boolean): List[A] =
        foldRightViaFoldLeft(xs, Nil: List[A], (h, t) => if f(h) then Cons(h, t) else t)

    def filter3[A](xs: List[A], f: A => Boolean): List[A] =
        val buf = new collection.mutable.ListBuffer[A]
        def go(xs: List[A]): Unit =
            xs match
                case Nil => ()
                case Cons(h, t) => if f(h) then buf += h; go(t)
        go(xs)
        List(buf.toList*)

    // flatMap

    def flatMap[A, B](xs: List[A], f: A => List[B]): List[B] =
        concat(map(xs, f))

    def filterViaFlatMap[A](xs: List[A], f: A => Boolean): List[A] =
        flatMap(xs, a => if f(a) then List(a) else Nil)

    def fizzBuzz(n: Int) =
        for (i <- 1 to n) yield {
            (i % 3, i % 5) match
                case (0, 0) => "FizzBuzz"
                case (_, 0) => "Buzz"
                case (0, _) => "Fizz"
                case (_, _) => i.toString
        }

    @annotation.tailrec
    def startsWith[A](xs: List[A], prefix: List[A]): Boolean = 
        (xs, prefix) match
            case (_, Nil) => true
            case (Cons(h, t), Cons(h2, t2)) if h == h2 => startsWith(t, t2)
            case _ => false

    
    case class SafeValue[+T](private val internalValue: T):
        def get: T = synchronized {
            // does something
            internalValue
        }
    

    trait SemiGroup[T]:
        extension(x: T) def combine(y: T): T

    trait Monoid[T] extends SemiGroup[T]:
        def unit: T

    object Monoid:
        def apply[T](using m: Monoid[T]) = m
    

    
    given Monoid[String] with
        extension (x: String) 
            def combine(y: String): String = 
                x.concat(y)
        def unit: String = ""

    given Monoid[Int] with
        extension (x: Int) def combine(y: Int): Int = x + y
        def unit: Int = 0


    def combineAll[T: Monoid](xs: scala.List[T]): T =
        xs.foldLeft(Monoid[T].unit)(_.combine(_))

    

    
                                 

import List._

def main(args: Array[String]): Unit = 
    
    // println(take(List(1, 2, 3, 4, 5, 6, 7), 4).length) // 4

    import scala.List

    def addOne(x: Int): Int = x + 1

    val listOfFunctions = scala.List.fill(10)(addOne)
    // println(listOfFunctions)

    val executedFunctions = 
        listOfFunctions.foldLeft(identity(0)){case (acc, func) => 
            func(acc) 
        }
    
    println(executedFunctions) // 10

    // def stringExecutor[A, B](fns: scala.List[A => Option[A]], init: A): Option[A] =
    //     fns.foldLeft(init) { case (acc, fn) =>
           
    //     }
    println(fizzBuzz(15))