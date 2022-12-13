object Main extends App {

    class Rational(n: Int, d: Int):
        require(d != 0)  // requires that d is not constructed with a zero
        private val g = gcd(n.abs, d.abs)
        val numer: Int = n / g
        val denom: Int = d / g

        def this(n: Int) = this(n, 1)  //auxiliary constructor, this is unnecessary with default parameters

        override def toString = s"$n / $d" //override previous built-in method
        // Addition
        def + (that: Rational): Rational =
            Rational(
                numer * that.denom + that.numer * denom,
                denom * that.denom
            )
        // overloading + to take an Int parameter
        def + (i: Int): Rational =
            Rational(numer + i * denom, denom)
        // Subtraction
        def - (that: Rational): Rational =
            Rational(
                numer * that.denom - that.numer * denom,
                denom * that.denom
            )
        
        def - (i: Int): Rational =
            Rational(numer - i * denom, denom)
        // Multiplication
        def * (that: Rational): Rational =
            Rational(
                this.numer * that.numer,
                this.denom * that.denom
            )
        def * (i: Int): Rational =
            Rational(numer * i, denom)
        // Division
        def / (that: Rational): Rational =
            Rational(
                numer * that.denom,
                denom * that.numer
            )
        def / (i: Int): Rational =
            Rational(numer, denom * i)
        

        def lessThan(that: Rational): Boolean =
            this.numer * that.denom < that.numer * this.denom
        
        def max(that: Rational): Rational =
            if this.lessThan(that) then that else this
        
        private def gcd(a: Int, b: Int): Int =
            if b == 0 then a else gcd(b, a % b)
        
        
    extension (x: Int)
        def + (y: Rational) = Rational(x) + y
        def - (y: Rational) = Rational(x) - y
        def * (y: Rational) = Rational(x) * y
        def / (y: Rational) = Rational(x) / y

    
    val oneHalf = Rational(1, 2)
    val twoThirds = Rational(2, 3)
    println(oneHalf + twoThirds) // 7/6
    println(oneHalf * twoThirds) // 7/6

    val r = Rational(1, 2)
    println(r.numer)
    println(r.denom)
    println(r.lessThan(Rational(5, 7)))
    println(Rational(2, 3) max Rational(5, 4))
    val y = Rational(5)
    println(Rational(66,3))

    println(r * 2)
    println(2 * r)

    
}



