object Kata extends App {
    val xs  =  List(1, 2, 3, 4, 5, 6, 7, 8)
    // create a list of functions from a list of integers
    val functions = xs.map{x => 
        val func = (y: Int) => x + y
        func    
    }
    println(functions)

    val functions2 = xs.map{x => 
        (y: Int) => x + y   
    } 
    println(functions2)

    // Credit Card Mask
    def maskify(cc: String): String = {
        if (cc.length <= 4) cc
        else "#" concat maskify(cc.substring(1))
    }

    println("".toList) // List()

    println("America".substring(1)) // merica

    println(maskify("4556364607935616")) // ############5616

    def maskifyBetter(cc: String): String =
        cc.replaceAll(".(?=.{4})", "#")

    

    def multiplyAll(xs: List[Int])(n: Int): List[Int] = {
        xs.map(x => n * x).toList
    }

    // input: List(1, 2, 3)(2)
    println(multiplyAll(List(1, 2, 3))(2))
    // output: List(2, 4, 6)

    

                                        

}
