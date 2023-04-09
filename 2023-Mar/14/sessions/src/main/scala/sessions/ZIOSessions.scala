

object Recursion1  {
    def sortList(list: List[Int]): List[Int] = {

        def insert(number: Int, sortedList: List[Int]): List[Int] =
            if (sortedList.isEmpty || number <= sortedList.head) number :: sortedList
            else sortedList.head :: insert(number, sortedList.tail)

        if (list.isEmpty || list.tail.isEmpty) list
        else insert(list.head, sortList(list.tail))
    }

}

object Recursion2 extends scala.App {
    import scala.annotation.tailrec

    def sum1(xs: List[Int]): Int = {
        xs.foldLeft(0)(_ + _)
    }

    def sum2(xs: List[Int]): Int = {
        @tailrec
        def sum2Rec(xs: List[Int], acc: Int): Int = {
            xs match {
                case Nil => acc
                case head :: tail => sum2Rec(tail, acc + head)
            }
        }
        sum2Rec(xs, 0)
    }

    println(sum2(List(2, 3, 2, 3, 1)))


    def factorial(n: Int): Int = {
        @tailrec
        def factorialRec(acc: Int, n: Int): Int = {
            if (n == 0) acc
            else factorialRec(acc * n, n - 1)
        }
        factorialRec(1, n)
    }

    println(factorial(0))

    def uniqueInOrder[T](xs: Iterable[T]): Seq[T] = {
        def concat(list: List[T]): List[T] = {
            if (list.isEmpty) list 
            else list.head :: concat(list.tail.dropWhile(_ == list.head))
        }
        concat(xs.toList)
    }

    def uniqueInOrder2[T](xs: Iterable[T]): Seq[T] = {
        def concat(list: List[T]): List[T] = {
            list match {
                case Nil => list 
                case x :: xs => x :: concat(xs.dropWhile(_ == x))
            }

        }
        concat(xs.toList)
    }
    
    
    println(uniqueInOrder2("AAABBBbCCAA"))
    println(uniqueInOrder2(List(1, 2, 1, 2, 2, 3, 4, 4, 4, 1)))

    def uniqueInOrder3[T](xs: Iterable[T]): Seq[T] = {
        if(xs.isEmpty) Seq()
        @scala.annotation.tailrec
        def orderHelper(it: Iterable[T], acc: List[T]): List[T] = {
            if(it.isEmpty) acc
            else if(it.head == acc.head) orderHelper(it.tail, acc)
            else orderHelper(it.tail, it.head :: acc)
        }
        orderHelper(xs, List(xs.head)).toSeq.reverse
    }

    println(uniqueInOrder3("AAABBBbCCAA"))
    println(uniqueInOrder3(List(1, 2, 1, 2, 2, 3, 4, 4, 4, 1)))


    // with tailrec
    def uniqueInOrder4[T](xs: Iterable[T]): Seq[T] = { 
        if (xs.isEmpty) Nil
        else {
            @tailrec
            def uniqueOrder(it: Seq[T], acc: Seq[T]): Seq[T] = {
                if (acc.isEmpty) it.reverse
                else {
                    val head = acc.head
                    uniqueOrder(head +: it, acc.dropWhile(_ == head))
                }
            }
            uniqueOrder(Seq(), xs.toSeq)
        }
    }

    println(uniqueInOrder4("AAABBBbCCAA"))
    println(uniqueInOrder4(List(1, 2, 1, 2, 2, 3, 4, 4, 4, 1)))
    


}
