

object Kata extends App {

    def multiple3or5(number: Int): Long = 
        (1 until number).view.filter(x => x % 3 == 0 || x % 5 == 0).foldLeft(0L)(_ + _)

    
    def isNarcissist(n: Int): Boolean = 
         n == n.toString.map(i => math.pow(i.asDigit, n.toString.size)).sum
    

    println(isNarcissist(153))

    
    def nbDig(n: Int, d: Int) = {
        (0 to n).map(i => i * i).toString.count(_.asDigit == d)
    }

    println(nbDig(5750, 0))
    
    
}
//