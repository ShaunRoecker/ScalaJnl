object SCALA_1_1_2023 extends App:
    println()

    // Regex Daily
    // to match 1+1=2, the regex would be raw"1\+1=2".r

    
    import scala.util.matching.Regex
    import java.util.regex.Pattern

    // \Q...\E  <- escape sequence, everything between \Q and \E will be escaped
    val regex1 = """\Q*\d+*\E""".r
    println(regex1.matches(raw"*\d+*")) //true

    // however don't use this syntax with java

    val regex2 = """(\d{3})(\d{3})(\d{3})""".r
    val str1 = "123456789"

    val m1 = regex2.replaceAllIn(str1, "$2$1$3") 
    println(m1) //456123789

    //  $0 inserts the entire regex match
    
    // Line Breaks
    // Look into \R

    // for removing 'Carriage Returns' and 'Line Feed' characters CRLF
    // [\r\n]

    // Possible solution to the Sub issue
    // ("\u001a", "");

    // [^0-9\r\n] matches any character that is not a digit or a line break
    // [\\x] matches a backslash or an x.

    // Programming in Scala

    class Rational(n: Int, d: Int):
        require(d != 0)

        private val g = gcd(n.abs, d.abs)
        val numer = n / g
        val denom = d / g

        def this(n: Int) = this(n, 1)
     
        def +(that: Rational): Rational =
            Rational(
                numer * that.denom + that.numer * denom, 
                denom * that.denom
            )
        // method overloading
        def +(i: Int): Rational =
            Rational(numer + i * denom, denom)
        
        def *(that: Rational): Rational =
            Rational(
                numer * that.numer, 
                denom * that.denom
            )
        
        def lessThan(that: Rational): Boolean =
            this.numer * that.denom < that.numer * this.denom

        def max(that: Rational): Rational =
            if this.lessThan(that) then that else this

        override def toString: String = s"$numer/$denom"

        private def gcd(a: Int, b: Int): Int =
            if b == 0 then a else gcd(b, a % b)

    // any code in the class constructor will be ran when a new class 
    // instance is created, unless otherwise created as lazy

    // so the println statement in the Rational constructor will be
    // executed when a new class instance is created
    val rational = Rational(1, 2) // out: Created: 1/2
    // Also note that since this class takes one or more parameters,
    // we can optionally leave off the "new" keyword
    // This is done with the "Universal apply method"
    // (can also do this with case classes)
    println(rational)

    val oneHalf = Rational(1, 2)
    val twoThirds = Rational(2, 3)

    val addedRational = oneHalf + twoThirds
    println(addedRational) // 7/6

    println(addedRational.denom) //6
    println(addedRational.numer) //7

    val twoTenths = Rational(2, 10)
    println(twoTenths) // 1/5


    //  extension methods
    // so that we can evaluate 2 + rational
    extension (x: Int)
        def + (y: Rational) = Rational(x) + y
        def * (y: Rational) = Rational(x) + y

    println(2 + rational) // 5/2

    val filesHere = (new java.io.File(".")).listFiles
    for file <- filesHere do 
        if file.getName.endsWith(".scala") then 
            println(file)

    val files1 = for 
            file <- filesHere  
            if file.isFile
            if file.getName.endsWith(".scala")
        do println(file)

    val files2 = for 
            file <- filesHere
            if file.getName.endsWith(".scala")
        yield file

    // using multiple generators in a for expression
    //val filesHere = (new java.io.File(".")).listFiles
    println()
    val localFiles = (new java.io.File(".")).listFiles
    def fileLines(file: java.io.File) =
        scala.io.Source.fromFile(file).getLines().toVector

    def grep(string: String, fileType: String) =
        val pattern = string.r
        val fileTypeFormatted = "." + fileType
        // listFile:
        //Returns an array of abstract pathnames denoting the files 
        // in the directory denoted by this abstract pathname.
        for        
            file <- localFiles
            if file.getName.endsWith(fileTypeFormatted)
            line <- fileLines(file)
            trimmed = line.trim
            if pattern.unanchored.matches(trimmed)
        do println(s"$file: ${trimmed}")

    grep("twoTenths", "scala")

    def scalaFiles = 
        for
            file <- localFiles
            if file.getName.endsWith(".scala")
        yield file

    // In this case, the yield returns an Array[File]
    println(scalaFiles(0)) //./SCALA_1_1_2023.scala

    val forLineLengths =
        for 
            file <- filesHere //Array[File]
            if file.getName.endsWith(".scala")
            line <- fileLines(file) //Vector[String]
            trimmed = line.trim
            if trimmed.matches(".*for.*")
        yield trimmed.length

    println(forLineLengths.getClass.getSimpleName) //int[] 

    import java.io.FileReader
    val file5 = new FileReader("SCALA_1_1_2023.scala")
    try
        println(file5.read())
    finally
        file5.close()

    // dont return values from finally clauses,
    // use for cleanup operations only
    
    import java.net.URL
    import java.net.MalformedURLException

    def urlFor(path: String): URL =
        try new URL(path)
        catch case e: MalformedURLException =>
            new URL("https://www.scala-lang.org")

    println(urlFor("https://www.github.com"))

    // a recursive alternative to looping with vars
    // def searchFrom(i: Int): Int =
    //     if i >= args.length then -1
    //     else if args(i).startsWith("-") then searchFrom(i + 1)
    //     else if args(i).endsWith(".scala") then i
    //     else searchFrom(i + 1)
    // val i = searchFrom(0)

    def makeRowSeq(row: Int, size: Int) =
        for col <- 1 to size yield
            val prod = (row * col).toString
            val padding = " " * (4 - prod.length)
            padding + prod

    //println(makeRowSeq(10)) 
    //Vector(  10,   20,   30,   40,   50,   60,   70,   80,   90,  100)
    def makeRow(row: Int, size: Int) = makeRowSeq(row, size).mkString

    def multiTable(size: Int = 10) =
        val tableSeq =
            for row <- 1 to size
            yield makeRow(row, size)
        tableSeq.mkString("\n")

    println(multiTable())

    // 1   2   3   4   5   6   7   8   9  10
    // 2   4   6   8  10  12  14  16  18  20
    // 3   6   9  12  15  18  21  24  27  30
    // 4   8  12  16  20  24  28  32  36  40
    // 5  10  15  20  25  30  35  40  45  50
    // 6  12  18  24  30  36  42  48  54  60
    // 7  14  21  28  35  42  49  56  63  70
    // 8  16  24  32  40  48  56  64  72  80
    // 9  18  27  36  45  54  63  72  81  90
    //10  20  30  40  50  60  70  80  90 100

    object Padding:
        def padLines(text: String, minWidth: Int): String =
            def padLine(line: String): String = // can access minWidth for it's enclosing
                if line.length >= minWidth then line  // function (closure)
                else line + " " * (minWidth - line.length)

            val paddedLines = 
                for line <- text.linesIterator 
                yield padLines(line, minWidth)
            paddedLines.mkString("\n")

    // function literals
    val f20 = (x: Int, y: Int) => x + y 
    println(f20(5, 6)) // 11
    val f21 = (_: Int) + (_: Int)
    println(f21(5,6)) // 11

    def sum(a: Int, b: Int, c: Int): Int = a + b + c
    val f23 = sum(_, _, _)
    println(f23(5, 4, 3)) //12

    // Partially Applied Functions
    val f24 = (x: Int, y: Int) => x + y

    val add7 = f24(7, _)
    println(add7(1)) //8

    def makeIncreaser(more: Int) = (x: Int) => x + more
    val mI = makeIncreaser(1) // func: (x: Int) => x + 1

    def varargsFunc(strings: String*) = strings.foreach(println)

    val list5 = List("a", "b", "c")
    varargsFunc(list5*) // can do list5* or list5:_*
    varargsFunc(list5:_*)

    def fileMatching(query: String, matcher: (String, String) => Boolean) =
        for file <- filesHere if matcher(file.getName, query)
        yield file

    
    println(fileMatching(".scala", _.contains(_)).toList) //List(./SCALA_1_1_2023.scala)
    println(fileMatching("a", _.endsWith(_)).toList) //List(./SCALA_1_1_2023.scala)

    object FileMatcher:
        private def filesHere = (new java.io.File(".").listFiles)
        private def filesMatching(matcher: String => Boolean) =
            for file <- filesHere if matcher(file.getName)
            yield file
        def filesEnding(query: String) =
            filesMatching(_.endsWith(query)).toList
        def filesContaining(query: String) =
            filesMatching(_.contains(query)).toList
        def filesRegex(query: String) =
            filesMatching(_.matches(query)).toList

    println(FileMatcher.filesContaining("a"))
    // List(./Constants.sc, ./SCALA_1_1_2023.scala)

    val intKit = List(1, 2, 3, 4)
    println(intKit.exists(_ % 2 == 1)) //exists take a predicate

    val s = "hello world"
    println(s.substring(3,6))







      
    


    




    



    
            


    

    


        















