
//> using scala "3.2.1"

// sealed trait List[+A]
// case object Nil extends List[Nothing]
// case class Cons[A](head: A, tail: List[A]) extends List[A]



sealed trait Either[+E,+A] {

 def map[B](f: A => B): Either[E, B] = 
   this match {
     case Right(a) => Right(f(a))
     case Left(e) => Left(e)
   }
   
 def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] =
   this match {
     case Left(e) => Left(e)
     case Right(a) => f(a)
   }

 def orElse[EE >: E, AA >: A](b: => Either[EE, AA]): Either[EE, AA] =
   this match {
     case Left(_) => b
     case Right(a) => Right(a)
   }

 def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): 
   Either[EE, C] = for { a <- this; b1 <- b } yield f(a,b1)
}
case class Left[+E](get: E) extends Either[E,Nothing]
case class Right[+A](get: A) extends Either[Nothing,A]

object Either
  

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] =
    this match
      case None => None
      case Some(a) => Some(f(a))
}
case object None extends Option[Nothing]
case class Some[A](get: A) extends Option[A]

object Option

enum LazyList[+A] { self =>
  case Empty
  case Cons(h: () => A, t: () => LazyList[A])

  def toList: List[A] =
    @annotation.tailrec
    def go(ll: LazyList[A], acc: List[A]): List[A] = 
      ll match
        case Cons(h, t) => go(t(), h() :: acc)
        case Empty => acc.reverse
    go(self, Nil)

  def toListFast: List[A] =
    val buf = new collection.mutable.ListBuffer[A]
    @annotation.tailrec
    def go(ll: LazyList[A]): List[A] = 
      ll match
        case Cons(h, t) =>
          buf += h()
          go(t())
        case Empty =>
          buf.toList
    go(self)
      
  
    
}

object LazyList:
  def cons[A](hd: => A, tl: => LazyList[A]): LazyList[A] =
    lazy val head = hd
    lazy val tail = tl
    Cons(() => hd, () => tl)


object Program:
  @main
  def main =
    println("Hello, world!") 

    def fib(n: Int): Int =
      @annotation.tailrec
      def go(n: Int, first: Int, second: Int): Int =
        if (n == 1) first
        else if (n == 2) second
        else go(n-1, second, first + second) 
      go(n, 0, 1)

    println(fib(5))

    def factorial(n: Int): Int =
      @annotation.tailrec
      def go(n: Int, acc: Int): Int =
        if (n <= 0) acc
        else go(n-1, n*acc)
      go(n, 1)

    println(factorial(5)) //120

    def factorialWhile(n: Int): Int =
      var acc = 1
      var i = n
      while i > 0 do
        acc *= i
        i -= 1
      acc

    println(factorialWhile(5))

    def benchmark(f: Int => Int, n: Int): Unit =
      val start = System.nanoTime()
      f(n)
      val end = System.nanoTime()
      println(math.abs(start - end))


    benchmark(factorial, 5) // 5091
    benchmark(factorialWhile, 5) // 4888

    def findFirst(arr: Array[String], target: String): Int =
      @annotation.tailrec
      def go(n: Int): Int =
        if (n >= arr.length) -1
        else if (arr(n) == target) n
        else go(n + 1)
      go(0)

    println(findFirst(Array("a", "b", "b", "a"), "b")) // 1

    def findLast(arr: Array[String], target: String): Int =
      @annotation.tailrec
      def go(n: Int): Int =
        if (n <= -1) -1
        else if (arr(n) == target) n
        else go(n - 1)
      go(arr.length - 1)

    println(findLast(Array("a", "b", "b", "a"), "b")) // 2
    println(findLast(Array("a", "b", "b", "a"), "a")) // 3

    def isSorted[A](list: List[A], ordered: (A, A) => Boolean): Boolean =
      @annotation.tailrec
      def go(n: Int): Boolean =
        if (n == list.length - 1) ordered(list(n-1), list(n))
        else if (! ordered(list(n-1), list(n))) false
        else go(n+1)
      if (list.size == 0 || list.size == 1) true
      else
        go(1)

    println(isSorted[Int](List(1,2,3,4), (a, b) => a < b)) //true
    println(isSorted[Int](List(1,2,3,4), _ > _)) // false
    println(isSorted[Int](List(4, 3, 2, 1), _ > _)) // true

    def partial1[A, B, C](a: A, f: (A, B) => C): B => C =
      (b: B) => f(a, b)

    def compose[A, B, C](f: A => B, g: B => C): A => C =
      a => g(f(a))


    import scala.util.{Using, Try}

    def wordCount(filename: String): Try[List[String]] =
      Using(io.Source.fromFile(filename)) { bufferedSource =>
        bufferedSource
          .getLines
          .map(line => s"$line\n")
          .mkString
          .split("\n")
          .toList
      }
    
    println(wordCount("text.txt"))

    



    

//
    
  

