object Regex:

    import scala.util.matching.Regex

    val r1 = "portmanteau".r
    val s1 = " Scala is a portmanteau of scalable and language"

    val m1 = r1.findFirstIn(s1) match
        case Some(s) => s
        case None => ""

    println(m1) //portmanteau

    val r2 = """(P|p)ython""".r 
    val s2 = "This is python, it's called Python"

    val m2 = r2.replaceAllIn(s2, "Scala")
    println(m2)

    for {
        pattern <- r2.findAllMatchIn(s2)
    } do println(pattern)
    // python <- these are "Match" types, not strings
    // Python
    // Most Regex methods (the "find" methods) return an Option[Match] type,
    // so keep in mind they are not Strings when they come back

    // The replace methods return a string

    val rgx = """(?<=(?:\s*(P|p)y))(?=(thon))""".r 
    val python = "Python"
    val py3thon = rgx.replaceAllIn(python, "3")
    println(py3thon) //Py3thon
    
    val s3 = "Find this address address"
    val r3 = """(add)(ress)""".r
    val m3 = r3.findAllIn(s3)

    println(r3.replaceAllIn(s3, "$2")) //Find this ress ress
    println()
    println(m3)
    println("********")
    val m4 = r3.findAllMatchIn(s3)
    val m5 = m4.toList
    println(m5)
    println(m5.reverse)

    println("*******")
    // def findAllIn(source: CharSequence): MatchIterator
    // def findAllMatchIn(source: CharSequence): Iterator[Match]

    // def findFirstIn(source: CharSequence): Option[String]
    // def findFirstMatchIn(source: CharSequence): Option[Match]

    // def matches(source: CharSequence): Boolean

    // def replaceAllIn(target: CharSequence, replacer: (Match) => String): String
    // def replaceAllIn(target: CharSequence, replacement: String): String

    // def replaceFirstIn(target: CharSequence, replacement: String): String
    // def replaceSomeIn(target: CharSequence, replacer: (Match) => Option[String]): String

    // In the replacement String, a dollar sign ($) followed by a number will be
    //  interpreted as a reference to a group in the matched pattern, 
    // with numbers 1 through 9 corresponding to the first nine groups, 
    // and 0 standing for the whole match.

    val regex1 = """regex""".r
    val regex1Unanchored = regex1.unanchored

    println(regex1.matches("regex")) //true
    println(regex1.matches("  regex  ")) //false
    println(regex1Unanchored.matches("regex")) //true
    println(regex1Unanchored.matches("   regex   ")) //true

    def >>> = println("****************************************************************")
    >>>

    println("This string".split(" ").toList)

    object NameRegex:
        private val name = """(Mr|Mrs|Ms)\. ([A-Z][a-z]+) ([A-Z][a-z]+)""".r
        def unapply(x: String) = {
            val tryName = name.unapplySeq(x).collect {
                case(List(title, first, last)) => (title, first, last)
            }
            tryName match
                case Some((a, b, c)) => (a, b, c)
                case None => ("", "", "")
        }
    
    val extract = NameRegex.unapply("Mrs. Danko Frederix")
    println(extract) // Some((Mrs,Danko,Frederix))

    >>>
    val timestamp = raw"([0-9]{2}):([0-9]{2}):([0-9]{2}).([0-9]{3})".r
    val description = "11:34:01.411" match 
        case timestamp(hour, minutes, _, _) => s"It's $minutes minutes after $hour"

    println(description)
    >>>
    val timestampUnanchored = timestamp.unanchored
    val description2 = "Timestamp: 11:34:01.411 error appeared" match 
        case timestampUnanchored(hour, minutes, _, _) => s"It's $minutes minutes after $hour"

    println(description2)
    >>>
    // ReplaceAllIn
    // def replaceAllIn(target: CharSequence, replacer: (Match) => String): String
    val minutes = timestamp.replaceAllIn("11:34:01.311", m => m.group(2))
    println(minutes)

    >>>
    // very cool
    val secondsThatDayInTotal = timestamp.replaceAllIn("11:34:01.311", _ match {
        case timestamp(hours, minutes, seconds, _) => s"$hours-$minutes"
    })
    println(secondsThatDayInTotal)

object Collections extends App:
    // collection methods

    val vector = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val exists = vector.exists(_ > 8) // <- takes a predicate
    println(exists) //true

    // group by
    val list2 = List(15, 10, 5, 8, 20, 12)
    val groups = list2.groupBy(x => x >= 10)  // <- takes a predicate
    println(groups) // HashMap(false -> List(5, 8), true -> List(15, 10, 20, 12))

    val listOfTrues = groups(true) ; println(listOfTrues) //List(15, 10, 20, 12)
    val listOfFalses = groups(false) ; println(listOfFalses) //List(5, 8)

    // emptyGroups = List().groupBy(_ > 4)
    // println(emptyGroups) // errors out

    // Can create more than true/false groups
    def groupByFives(x: Int): Int =
        if (x <= 5) then 5
        else if (x > 5 && x <= 10) then 10
        else 15

    val gb5 = list2.groupBy(groupByFives) ; println(gb5)
    // HashMap(5 -> List(5), 10 -> List(10, 8), 15 -> List(15, 20, 12))

    //  splitAt
    // create 2 subSequences from a Seq by providing an index
    val spl = list2.splitAt(3) ; println(spl) //(List(15, 10, 5),List(8, 20, 12))
    val (asplit, bsplit) = list2.splitAt(3) ; println(asplit) ; println(bsplit)
    // List(15, 10, 5)
    // List(8, 20, 12) 

    // Partition returns the same thing as splitAt, but based on a predicate
    val (partA, partB) = list2.partition(_ % 2 == 0) ; println(partA) ; println(partB)
    // List(10, 8, 20, 12)
    // List(15, 5)     

    val slide2 = List(1, 2, 3, 4, 5).sliding(2).toList ; println(slide2)
    //List(List(1, 2), List(2, 3), List(3, 4), List(4, 5))
    val slide3size2step = List(1, 2, 3, 4, 5).sliding(3, 2).toList ; println(slide3size2step)
    //List(List(1, 2, 3), List(3, 4, 5))

    // unzip
    val listOfTuples = List((1, "a"), (2, "b"), (3, "c"))
    val (zipa, zipb) = listOfTuples.unzip ; println(zipa) ; println(zipb)
    // List(1, 2, 3)
    // List(a, b, c)
    val zippedAgain = zipa.zip(zipb) ; println(zippedAgain)
    // List((1,a), (2,b), (3,c))

    




    

    
        

    
            





    

       








    




    





    

