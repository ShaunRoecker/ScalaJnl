object LambdaFunctions {


    def concatenator: (String, String) => String = new Function2[String, String, String] {
        override def apply(s1: String, s2: String): String = s1 + s2
    }
    println(concatenator("Hello, ", "world!"))

    def addExclamation: (String) => String = new Function1[String, String] {
        override def apply(s1: String): String = s1 + s1
    }

    // define a function which takes an int and returns a function that takes an int and returns an int
    val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
        override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
            override def apply(y: Int): Int = x + y
        }
    }

    // anonymous functions
    val doubler: Int => Int = x => x * 2

    val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

    val noParam = () => 3
    // note: lambdas must be called with parentheses, unlike methods

    println(noParam) // X$$$Lambda$1274/0x00000008014695b8@64ed18ec
    println(noParam()) //3

    val stringToInt = { (str: String) => 
        str.toInt
    }

    val niceIncrement: Int => Int = (x: Int) => x + 1

    val niceAdder: (Int, Int) => Int = _ + _

    val lambda1: (Int, Int) => Int = (x: Int, y: Int) => x + y

    val lambda2 = (x: Int, y: Int) => x + y

    val lambda3 = (s: String, i: Int) => {
        s match 
            case "hello" => i
            case _ => "Default"
    }
    println(lambda3("hell0", 6))

    val superAdd = (x: Int) => (y: Int) => x + y
    println(superAdd(3)(4))





}

object HOFsCurries extends App {

    // val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = ???

    // higher-order functions are functions that can take functions
    // as parameters

    // function that applies a function n times over a value x
    // nTimes(f, n, x)
    // nTimes(f, 3, x) = f(f(f(x)))

    // nTimes(f, n, x) = nTimes(f(f(...f(x))) = nTimes(f, n-1, f(x))
    def nTimes(f: Int => Int, n: Int, x: Int): Int =
        if (n <= 0) x
        else nTimes(f, n-1, f(x))

    

    val plusOne = (x: Int) => x + 1

    println(nTimes(plusOne, 10, 1))

    // ntb(f, n) = x => f(f(f...(x)))
    // increment10 = ntb(plusOne, 10)  = x => plusOne(plusOne...(x))
    // val y = increment10(1)

    def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
        if (n <= 0) (x: Int) => x
        else (x: Int) => nTimesBetter(f, n-1)(f(x))

    val increment10 = nTimesBetter(plusOne, 10)
    println(increment10(1))

    // curried functions
    val supperAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
    val add3 = supperAdder(3)
    println(add3(10))

    println(supperAdder(3)(10))

    // takes an x and returns a function y = x + y

    def curriedFormatter(c: String)(x: Double): String = c.format(x)

    val standardFormat: (Double => String) = curriedFormatter("%4.2f")
    val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))







}