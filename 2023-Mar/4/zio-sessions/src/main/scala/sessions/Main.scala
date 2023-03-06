

object ListOps extends scala.App {
    val lista = List("a", "b", "c", "d", "e")
    val listb = List(1, 2, 3, 4)

    println(lista.last) // d
    println(lista.init) // List(a, b, c)

    println(listb.reverse) //List(4, 3, 2, 1)

    // not optimal version of reverse 
    def rev[T](xs: List[T]): List[T] =
        xs match {
            case List() => xs
            case x :: xs1 => rev(xs1) ::: List(x)
        } 

    println(rev(lista)) //List(d, c, b, a)
    println(rev(rev(lista))) //List(a, b, c, d)

    println(lista.reverse.reverse) // List(a, b, c, d)

    // drop, take, and splitAl

    val take = lista.take(2); println(take) //List(a, b)
    // take- takes from beginning of list

    val drop = lista.drop(2); println(drop) //List(c, d, e)
    // drop- drops from beginning of list

    val splitAt = lista.splitAt(2); println(splitAt) //(List(a, b), List(c, d, e)
    // Returns tuple that is equivalent to List.splitAt(x) = (List.take(x), List.drop(x))

    val flatten = List(List(1, 2), List(3, 4), List(5, 6)).flatten
    println(flatten) // List(1, 2, 3, 4, 5, 6)

    println(List("abc", "def").map(_.toList).flatten) //List(a, b, c, d, e, f)

    //val lista = List("a", "b", "c", "d", "e")
    val ind = lista.indices.zip(lista) 
    println(ind) //Vector((0,a), (1,b), (2,c), (3,d), (4,e))

    val ind2 = lista.indices
    println(ind2) //Range 0 until 5

    val indToList = lista.indices.toList
    println(indToList) //List(0, 1, 2, 3, 4)

    val zippedList1 = lista.zip(listb)
    println(zippedList1) //List((a,1), (b,2), (c,3), (d,4))

    // For zip, if the two lists are of different length, any unmatched elements are dropped

    val zippedWithIndex = lista.zipWithIndex
    println(zippedWithIndex) //List((a,0), (b,1), (c,2), (d,3), (e,4))

    // Any list of tuples can be change back to a tuple of lists with unzip

    val unzipped = zippedWithIndex.unzip
    println(unzipped) //(List(a, b, c, d, e),List(0, 1, 2, 3, 4))

    val listToString = lista.toString
    println(listToString)

    def conc(s: String): String =
        s + "!!!!!"

    println(conc(listToString)) // List(a, b, c, d, e)!!!!!

    val madeString = lista.mkString("[ ", " :: ", " ]")
    println(madeString) //[ a :: b :: c :: d :: e ]

    println(lista.mkString) // abcde

    val buf = new StringBuilder
    lista.addString(buf, "(",";",")")

    println(buf) //(a;b;c;d;e)

    // Higher-order methods on lists

    val map1 = List(1, 2, 3).map(_ + 1)
    println(map1) //List(2, 3, 4)

    val words = List("the", "quick", "brown", "fox")
    val wordLengths = words.map(_.length)
    println(wordLengths) //List(3, 5, 5, 3)

    val filtered = words.filter(_.contains("o"))
    println(filtered) //List(brown, fox)

    // partition is like filter, but returns a tuple-2 of lists,
    // one for which the predicate evaluates to true, and one for false

    val partitioned = words.partition(_.contains("brown"))
    println(partitioned) //(List(brown),List(the, quick, fox))
    println(partitioned._1) //List(brown)
    println(partitioned._2) //List(the, quick, fox)

    // xs.partition(p) == (xs.filter(p), xs.filter(!p(_)))

    // find
    println(List(1, 2, 3, 4, 5).find(_ == 4)) //Some(4)

    println(List(1, 2, 3, 4, 5, 2).find(_ == 2)) //Some(2)

    println(List(1, 2, 3, 4, 5).find(_ == 8)) //None
    
    
    println(List(1, 2, 3, 4, 5, 2).findAll(_ == 2)) //None










        
    
















}