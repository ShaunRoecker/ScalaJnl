object SCALA_1_7_2023 extends App:

    // class Container[A] { def addIt(x: A) = 123 + x }

    // Type relation operators
    // A =:= B	A must be equal to B
    // A <:< B	A must be a subtype of B
    //A <%< B	A must be viewable as B

    // class Container2[A](value: A):
    //     def addIt(implicit evidence: A =:= Int) = 123 + value 

    /////////////////////////////////////////////////////////////////////
    //https://www.baeldung.com/scala/match-types
    // Match Types in Scala 3

    type FirstComponentOf[T] = T match
        case String      => Option[Char]
        case Int         => Int
        case Iterable[t] => Option[t]
        case Any         => T

    // Depending on the type T, the first element has a different meaning

    val aNumber: FirstComponentOf[Int] = 2
    val aChar: FirstComponentOf[String] = Some('b')
    val anItem: FirstComponentOf[Seq[Float]] = Some(3.2f)

    //Internally, a match type is in the form S match { 
    //     P1 => T1 
    //     … 
    //     Pn => Tn 
    // }, 
    // where S, T1, …, Tn are types and P1, …, Pn are type patterns.

        // Match types can be part of a recursive definition:
    // type Node[T] = T match
    //     case Iterable[t] => Node[t]
    //     case Array[t]    => Node[t]
    //     case AnyVal      => T

    //Thanks to match types, we can write a method returning the 
    // first element of a given type without any code duplication:

    def firstComponentOf[U](elem: U): FirstComponentOf[U] = elem match
        case s: String => if (s.nonEmpty) Some(s.charAt(0)) else Option.empty[Char]
        case i: Int => i.abs.toString.charAt(0).asDigit
        case it: Iterable[_] => it.headOption
        case a: Any => a

    println(firstComponentOf("str")) //Some(s)
    println(firstComponentOf(1.2f)) //1.2

    /////////////////////////////////////////////////////////////////////
    // To do the same thing in Scala 2: yikes!
    sealed trait FirstComponentOfScala2[-T] {
        type Result
        def firstComponentOf(elem: T): Result
    }
    object FirstComponentOfScala2 {
        implicit val firstComponentOfString: FirstComponentOfScala2[String] =
            new FirstComponentOfScala2[String] {
            override type Result = Option[Char]

            override def firstComponentOf(elem: String): Option[Char] =
                if (elem.nonEmpty) Some(elem.charAt(0)) else Option.empty[Char]
            }

        implicit val firstComponentOfInt: FirstComponentOfScala2[Int] =
            new FirstComponentOfScala2[Int] {
            override type Result = Int

            override def firstComponentOf(elem: Int): Int =
                elem.abs.toString.charAt(0).asDigit
            }

        implicit def firstComponentOfIterable[U]
            : FirstComponentOfScala2[Iterable[U]] =
            new FirstComponentOfScala2[Iterable[U]] {
            override type Result = Option[U]

            override def firstComponentOf(elem: Iterable[U]): Option[U] =
                elem.headOption
            }

        implicit val firstComponentOfAny: FirstComponentOfScala2[Any] =
            new FirstComponentOfScala2[Any] {
            override type Result = Any

            override def firstComponentOf(elem: Any): Any = elem
            }

        def firstComponentOf[T](elem: T)(implicit
            T: FirstComponentOfScala2[T]
        ): T.Result = T.firstComponentOf(elem)
    }

    // Higher Kinded Types:
    trait Collection[T[_]]:
        def wrap[A](a: A): T[A]
        def first[B](b: T[B]): B

    
    




    
    
    

    

    

