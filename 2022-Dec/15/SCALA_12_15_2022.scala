object SCALA_12_15_2022 extends App {
    println("String".count(_ == 'B'))

    // sum method (functional)
    def sum(xs: List[Int]): Int = xs match
        case Nil => 0
        case x :: tail => x + sum(tail)
        
    def multiply(xs: List[Int]): Int = xs match
        case Nil => 1
        case x :: tail => x * multiply(tail)
    
    println(sum(List(1,2,3,4))) //10
    println(sum(List())) 

    println(multiply(List(1,2,3)))

    // 1) check for a base case to exit recursion
    // 2) 

    def createWorldPeace = ???
    println(System.currentTimeMillis())

    def xTimes(f: Int => Int, n: Int, x: Int): Int =
        if (n <= 0) x
        else xTimes(f, n -1, f(x))

    println(xTimes(_ + 1, 5, 1))

    val twoTimes = xTimes(_, 2, _)
    println(twoTimes(_ + 1, 10))

    // In FP, just like in mathematics, dont reuse variable
    // build them from other simple variables...

    // simple functions are like lego blocks, to build
    // more complex functions

    // val a = f(x)
    // val b = g(a)
    // val c = h(b)

    // val b = g(f(x))
    // val c = h(g(f(x)))

    val func = (x: Int) => x + 2
    List(1,2,3).map(x => x + 2)








}
