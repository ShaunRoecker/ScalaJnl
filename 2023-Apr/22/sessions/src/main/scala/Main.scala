import scala.annotation.tailrec



object Sessions {

    sealed trait Tree[+A]
    case class Leaf[A](value: A) extends Tree[A]
    case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

    def size[A](tree: Tree[A]): Int =
        tree match
            case Leaf(_) => 1
            case Branch(left, right) => size(left) + size(right) + 1

    extension(t: Tree[Int])
        def maximum: Int =
            t match
                case Leaf(value) => value
                case Branch(left, right) => left.maximum.max(right.maximum)

    
    def isSorted(xs: List[Int], ordered: (Int, Int) => Boolean): Boolean =
        @tailrec
        def loop(n: Int): Boolean =
            if (n == xs.length-1) ordered(xs(n-1), xs(n))
            else if (! ordered(xs(n-1), xs(n))) false
            else loop(n+1)
        if (xs.size == 0 || xs.size == 1) true
        else loop(1)
        
    
    def `slowSort:/`(xs: List[Int]): List[Int] =
        if (xs.length < 2) xs
        else {
            val pivot = xs(0)
            val (less, more) = xs.tail.partition(_ < pivot)
            `slowSort:/`(less) ::: List(pivot) ::: `slowSort:/`(more)
        }

    
    // sealed trait Option[+A]
    // case class Some[+A](get: A) extends Option[A]
    // case object None extends Option[Nothing]

    def mean(xs: Seq[Double]): Option[Double] = 
        if (xs.isEmpty) None
        else Some(xs.sum / xs.length)
    
    // find pivot index (index where the sum of the ints on the right is equal to the
    // sum of the ints on the left)
    def pivotIndex(xs: List[Int]): Int =
        @tailrec
        def pivotAcc(i: Int, leftSum: Int, rightSum: Int): Int =
            if (i == xs.length) -1
            else if (leftSum == rightSum - xs(i)) i
            else pivotAcc(i + 1, leftSum + xs(i), rightSum - xs(i))
        pivotAcc(0, 0, xs.sum)

    case class Employee(name: String, department: String)
    
    def lookupByName(name: String): Option[Employee] = 
        val employees: Map[String, Employee] = 
            Map(
                "Greg" -> Employee("Greg", "Sales"),
                "Joe" -> Employee("Joe", "Development"),
                "Sarah" -> Employee("Sarah", "HR"),
            )
        employees.get(name)
    
    val joeDepartmentOption: Option[String] =
        lookupByName("Joe")
            .map(_.department)

    val joeDepartmentString: String =
        lookupByName("Joe")
            .map(_.department)
            .getOrElse("Not Found")

    val department1 =
        lookupByName("Sarah") // Option[String]
            .map(_.department)  // Option[String]
            .filter(_ != "Accounting") // Option[String]
            .getOrElse("Default Dept.") // String

    val department2 =
        lookupByName("Sara") // Option[String]
            .map(_.department)  // Option[String]
            .filter(_ != "Accounting") // Option[String]
            .getOrElse("Dept12.") // String
            .filter(_.isDigit)
            .map(_.toString.toInt) // need to convert Char toString first to get Int without
            .toList                     // getting the Decimal value of ASCII -> ("a" == 97)

    def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f
    val absO: Option[Double] => Option[Double] = lift(math.abs)

    val ex1: Option[Double] = absO(Some(-42))

    val res1: Option[Double] = Some(-42)
    val res2: Option[Int] = res1.map(_.toInt)

    def sequence1[A](as: List[Option[A]]): Option[List[A]] =
        as match
            case Nil => Some(Nil)
            case h :: t => h.flatMap(hh => sequence(t).map(hh :: _))

    extension[A](as: List[Option[A]])
        def sequence: Option[List[A]] =
            as match
                case Nil => Some(Nil)
                case h :: t => h.flatMap(hh => as.tail.sequence.map(hh :: _))
        
    val listOption: Option[List[Int]] = List(Some(1), Some(2), Some(3)).sequence

    val res3 = Some((1, "a"))  // Some((1, a))
    val unzipped = res3.unzip  // (Some(1),Some(a))

    // sealed trait Either[+E, +A]
    // case class Left[+E](value: E) extends Either[E, Nothing]
    // case class Right[+A](value: A) extends Either[Nothing, A]


    def mean2(xs: IndexedSeq[Double]): Either[String, Double] =
        if (xs.isEmpty)
            Left("mean of empty list!")
        else
            Right(xs.sum / xs.length)

    val right1 = Right(1) 
    val right2 = Right(2)
    val right3 = Right(3)
    val left1 = Left(1)

    val rightConnect = for {
        r1 <- right1
        r2 <- right2
        r3 <- right3
    } yield r1 + r2 + r3  // Right(6)

    val rightConnectWithLeft = for {
        r1 <- right1
        r2 <- right2
        l1 <- left1 
    } yield r1 + r2   // Right(6)





    def main(args: Array[String]): Unit = 
        println("Hello world")
        println(`slowSort:/`(List(1, 6, 4, 9, 2, 5, 3)))
        println(mean(Seq(2, 4, 3))) // 3.0
        println(joeDepartmentOption)  // Some(Development)
        println(joeDepartmentString)  // Department 
        println(department1) // HR
        println(department2) // List(1, 2)
        println(ex1) // Some(42.0)
        println(res2)
        println(listOption) // Some(List(1, 2, 3))
        println(unzipped) // (Some(1),Some(a))
        println(rightConnect) // Right(6)
        println(rightConnectWithLeft) // Left(1)
     



        
}