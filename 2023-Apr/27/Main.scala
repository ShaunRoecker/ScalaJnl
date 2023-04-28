
//> using scala "3.2.1"


enum List[+A]:
  case Nil
  case Cons(head: A, tail: List[A])

  

object List:
  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail*))


  extension(xs: List[Int])
    def sum: Int =
      @annotation.tailrec
      def go(list: List[Int], acc: Int): Int =
        list match {
          case Nil => acc
          case Cons(h, t) =>  go(t, acc + h)
      }
      go(xs, 0)

    def sumViaFoldRight: Int =
      xs.foldRight(0)((a, b) => a + b)
    
      
  extension[A](xs: List[A])
    def append(a: List[A]): List[A] =
      xs match
        case Nil => a
        case Cons(h, t) => Cons(h, t.append(a))

    def foldRight[B](acc: B)(f: (A, B) => B): B =
      xs match
        case Nil => acc
        case Cons(h, t) => f(h, t.foldRight(acc)(f))

    def tail: List[A] =
      xs match
        case Nil => sys.error("tail on empty list")
        case Cons(h, t) => t

    def setHead(nh: A): List[A] =
      xs match
        case Nil => sys.error("head on empty list")
        case Cons(_, t) => Cons(nh, t)

    def drop(n: Int): List[A] =
      if (n <= 0) xs
      else xs match
        case Nil => Nil
        case Cons(h, t) => t.drop(n-1)

    def take(n: Int): List[A] =
      if (n <= 0) Nil
      else xs match
        case Nil => Nil
        case Cons(h, t) => Cons(h, t.take(n-1)) 

    def dropWhile(p: A => Boolean): List[A] =
      xs match
        case Cons(h, t) if p(h) => t.dropWhile(p)
        case _ => xs

    def init: List[A] =
      xs match
        case Nil => sys.error("init on empty list")
        case Cons(_, Nil) => Nil
        case Cons(h, t) => Cons(h, t.init)

    def length: Int =
      xs.foldRight(0)((_, acc) => acc + 1)

    @annotation.tailrec
    def foldLeft[B](acc: B)(f: (B, A) => B): B =
      xs match
        case Nil => acc
        case Cons(h, t) => t.foldLeft(f(acc, h))(f)

     
    
object Program:
  @main 
  def main =
    println("List, Stream(Lazy List) and LRU(Least Recently Used) data structures") 
    val list1: List[Int] = List(1, 2, 3)
    val list2: List[Int] = List(4, 5, 6)
    println(list1.sum)
    println(list1.tail) // Cons(2,Cons(3,Nil))
    val list3 = list2.setHead(10)
    println(list3) // Cons(10,Cons(5,Cons(6,Nil)))
    println(list1.drop(2)) // Cons(3,Nil)
    println(list1.take(2)) // Cons(1,Cons(2,Nil))
    println(list1.length) // 3









