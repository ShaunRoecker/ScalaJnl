
package fpinscala.datastructures.lists

// sealed trait List[+A]
// case object Nil extends List[Nothing]
// case class Cons(head: A, tail: List[A]) extends List[A]

// enum List[+A]:
//     case Nil 
//     case Cons(head: A, tail: List[A])

def factorial(n: Int): Int =
    @annotation.tailrec
    def go(n: Int, acc: Int): Int =
        if (n <= 0) acc
        else go(n-1, n*acc)
    go(n, 1)

// Tree

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree { self =>

    extension[A](tree: Tree[A])
        def size: Int =
            tree match
                case Leaf(_) => 1
                case Branch(l, r) => l.size + 1 + r.size


        def depth: Int =
            tree match
                case Leaf(_) => 0
                case Branch(l, r) => 1 + (l.depth max r.depth)


        def map[B](f: A => B): Tree[B] =
            tree match
                case Leaf(a) => Leaf(f(a))
                case Branch(l, r) => Branch(l.map(f), r.map(f))


        def fold[B](f: A => B)(g: (B, B) => B): B = 
            tree match
                case Leaf(a) => f(a)
                case Branch(l, r) => g(l.fold(f)(g), r.fold(f)(g))
        

        def sizeViaFold: Int =
            tree.fold(a => 1)(_ + 1 + _)


        def depthViaFold: Int =
            tree.fold(a => 0)((a, b) => 1 + (a max b))

        def mapViaFold[B](f: A => B): Tree[B] =
            tree.fold(a  => Leaf(f(a)):  Tree[B])(Branch(_, _))




    def maximum(tree: Tree[Int]): Int =
        tree match
            case Leaf(value) => value
            case Branch(l, r) => maximum(l) max maximum(r)
    
    extension(tree: Tree[Int])
        def maximumExt: Int =
            tree match
                case Leaf(value) => value
                case Branch(l, r) => l.maximumExt max r.maximumExt

        def maximumViaFold: Int =
            tree.fold(identity)(_ max _)

}

// The three monad laws:
// 1. Right Associativity: m flatMap f flatMap g == m.flatMap(x => f(x).flatMap(g))
// 2. Left Unit: unit(x).flatMap(f) == f(x)
// 3. Right Unit: m.flatMap(unit) = m

sealed trait Option[+A]
case class Some[A](value: A) extends Option[A]
case object None extends Option[Nothing]

object Option:
    extension[A](option: Option[A])
        def flatMap[B](f: A => Option[B]): Option[B] =
            option match
                case Some(x) => f(x)
                case None => None

        def map[B](f: A => B): Option[B] =
            option match
                case Some(x) => Some(f(x))
                case None => None

        // def apply(value: A)
            



sealed trait Try[+A]
case class Success[A](value: A) extends Try[A]
case class Failure(ex: Exception) extends Try[Nothing]

case class NonFatal(ex: Exception) extends Throwable


object Try:
    extension[A](tr: Try[A])
        def flatMap[B](f: A => Try[B]): Try[B] = 
            tr match
                case Success(a) => try f(a) catch { case NonFatal(ex) => Failure(ex) }
                case fail: Failure => fail 


        def map[B](f: A => B): Try[B] =
            tr match
                case Success(a) => Try(f(a))
                case fail: Failure => fail

            
    def apply[A](expr: => A): Try[A] =
        try Success(expr)
        catch { case NonFatal(ex) => Failure(ex) }
    
        

extension[A](list: List[A])
    def map2[B](f: A => B): List[B] =
        list match 
            case Nil => Nil
            case x :: xs => f(x) :: xs.map2(f)

implicit class Lister[A](xs: List[A]) {
    def map3[B](f: A => B): List[B] =
        xs match {
            case Nil => Nil
            case x :: xs => f(x) :: xs.map3(f)
        }
}

object Main:
    import scala.Option._

    def consecutive(s: String): Option[(Char, Int)] =
        val list = s.toList
        if (list.isEmpty) None
        else 
            val res =
                list.tail.scanLeft((list.head -> 1)) { case ((prevChar, count), char) =>
                    val nextAcc =
                        if (prevChar == char) count + 1
                        else 1
                    char -> nextAcc
            }
            Some(res.maxBy(_._2))

    def pivotIndex(nums: List[Int]): Int =
        @annotation.tailrec
        def go(i: Int, leftSum: Int, rightSum: Int): Int =
            if (i == nums.length) -1
            else if (leftSum == rightSum - nums(i)) i
            else go(i + 1, leftSum + nums(i), rightSum - nums(i))
        go(0, 0, nums.sum)

    def findFirst[A](list: List[A], target: A): Int =
        @annotation.tailrec
        def loop(n: Int): Int =
            if (n >= list.length) -1
            else if (list(n) == target) n
            else loop(n + 1)
        loop(0)

    def findLast[A](list: List[A], target: A): Int =
        @annotation.tailrec
        def loop(n: Int): Int =
            if (n <= -1) -1
            else if (list(n) == target) n
            else loop(n - 1)
        loop(list.length - 1)

    def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean =
        @annotation.tailrec
        def loop(n: Int): Boolean =
            if (n >= as.length - 1) ordered(as(n-1), as(n))
            else if (! ordered(as(n-1), as(n))) false
            else loop(n + 1)
        if (as.size == 0 || as.size == 1) true
        else loop(1)

    val lessThan = new Function2[Int, Int, Boolean] {
        def apply(x: Int, y: Int): Boolean = x < y
    }

    def main(args: Array[String]): Unit = 

        println("Trees")

        val tree = new Branch[Int](Leaf(1), Leaf(2))

        val map2List = List(1, 2, 3, 4, 5).map2(_ + 1)
        println(map2List) // List(2, 3, 4, 5, 6)

        val map3List = List(1, 2, 3, 4, 5).map3(_ + 1)
        println(map3List) // List(2, 3, 4, 5, 6)

        println(consecutive("abbcccddaa"))  // Some((c, 3))
        println(findFirst(List('a', 'b', 'b', 'a'), 'b')) // 1
        println(findLast(List('a', 'b', 'b', 'a'), 'b')) // 2

        println(findFirst(List(), 'b')) // -1
        println(findLast(List(), 'b')) // -1

        println(isSorted(Array(1, 2, 3, 4), (a, b) => a < b)) // true
        println(isSorted(Array(1, 2, 10, 4), (a, b) => a < b)) // false
        println(isSorted(Array[Int](), (a, b) => a < b)) // true
        println(isSorted(Array(1), (a, b) => a < b)) // true





    
