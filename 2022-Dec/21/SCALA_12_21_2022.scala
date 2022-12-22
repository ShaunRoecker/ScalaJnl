object SCALA_12_21_2022 extends App {
    println("12/21/2022")
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 2.2 && 2.3
    /////////////////////////////////////////////////////////
    // 2.2 (pg.209)  
    import scala.util.{Try, Success, Failure}
    def getKElemFromLast[A](list: List[A], k: Int): Try[A] = 
        val listReversed = list.reverse
        Try(listReversed(k - 1))
            
            
        

            
    val x = List(1,2,3,4,5)
    println(getKElemFromLast(x, 3))
        
    def fromLast[T](input: List[T], k: Int): T = 
        import scala.annotation.tailrec
        @tailrec
        def getListFrom(l: List[T], pos: Int): List[T] = pos match 
            case 1 => l
            case _ => getListFrom(l.tail, pos - 1)
        

        @tailrec
        def getElementFromLast(l1: List[T], l2: List[T]): T = l2.tail match 
            case Nil => l1.head
            case _ => getElementFromLast(l1.tail, l2.tail)
            

        
        getElementFromLast(input, getListFrom(input, k))
    
    println(fromLast(x, 2))

    /////////////////////////////////////////////////////////

    // Functors
    // Functors are things that can be mapped over
    //  List, Option, Future, and many others

    // In pseudo-code, you can think of a functor as being a trait

    // trait Functor[A]:
    //     def map(f: A => B): Functor[B]

    def f(a: Int): Int = a * 2
    def g(a: Int): Int = a * 3

    // Because the output of f is the input of g we can do this
    val x2 = g(f(100))
    println(x2) //600

    def f2(a: Int): (Int, String) = 
        val result = a * 2
        (result, s"\nf result: $result")
    
    def g2(a: Int): (Int, String) = 
        val result = a * 3
        (result, s"\ng result: $result")

    // get output of f
    val (fInt, fString) = f2(100)
    

    // plug the Int from 'f' as the input to 'g'
    val (gInt, gString) = g2(fInt)

    // create the total 'debug string' by manually merging
    // the strings from f and g
    val debug = fString + " " + gString
    println(s"result: $gInt, debug: $debug")



    
    

    



}