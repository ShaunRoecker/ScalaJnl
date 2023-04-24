import scala.annotation.tailrec



object Sessions {

    sealed trait Stream[+A]
    case object Empty extends Stream[Nothing]
    case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

    object Stream:
        def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = 
            lazy val head = hd
            lazy val tail = tl
            Cons(() => head, () => tail)

        def empty[A]: Stream[A] = Empty

        // def takeWhile[A](p: A => Boolean): Stream[A] =
        //     this match
        //         case Cons(h, t) if p(h()) => cons(h(), t().takeWhile(p))
        //         case _ => empty
            
        // def take[A](n: Int): Stream[A] = this match
        //     case Cons(h, t) if n > 1 => cons(h(), t().take(n - 1))
        //     case Cons(h, _) if n == 1 => cons(h(), empty)
        //     case _ => empty

        def apply[A](as: A*): Stream[A] =
            if (as.isEmpty) Empty 
            else cons(as.head, apply(as.tail: _*))
          
        def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = ???


    enum LazyList[+A]:
        case Empty
        case Cons(h: () => A, t: () => LazyList[A])
        
    // ^^ This type looks identical to our List type, except that the Cons data constructor 
    // takes explicit thunks (() => A and () => Stream[A]) instead of regular strict values.
    
    val ones: Stream[Int] = Stream.cons(1, ones)


    trait RNG:
        def nextInt: (Int, RNG)

    case class SimpleRNG(seed: Long) extends RNG:
        def nextInt: (Int, RNG) =
            val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
            val nextRNG = SimpleRNG(newSeed)
            val n = (newSeed >>> 16).toInt
            (n, nextRNG)

    val generatedNum = SimpleRNG(12943L).nextInt

    type Rand[+A] = RNG => (A, RNG)

    val int: Rand[Int] = _.nextInt


    // example of a sum function that can be parallelized
    def sum(ints: IndexedSeq[Int]): Int =
        if (ints.size <= 1)
            ints.headOption.getOrElse(0)
        else
            val (left, right) = ints.splitAt(ints.length/2)
            sum(left) + sum(right)




    trait Par[A]


    def unit[A](a: => A): Par[A] = ??? // promotes a constant value to a parallel computation
    def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] = ??? // combines the results of two parallel computations with a binary function
    def fork[A](a: => Par[A]): Par[A] = ??? // marks a computation for concurrent evaluation. The evaluation won't actually occur until forced by run
    def lazyUnit[A](a: => A): Par[A] = ??? // fork(unit(a)) // wraps its unevaluated argument in a Par and marks it for concurrent evaluation
    def run[A](a: Par[A]): A = ??? // extracts a value from a Par by actually performing the computation

    


    
    // def main(args: Array[String]): Unit = 
    //     println(generatedNum) // (497865076,SimpleRNG(32628085668609))
    //     println(int(SimpleRNG(123L))) // (47324114,SimpleRNG(3101433181802))
    //     println(5556789 % 5)
        
     
    




        
}