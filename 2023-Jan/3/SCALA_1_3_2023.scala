object SCALA_1_3_2023 extends App: 
    println("Regex Daily")
    //^((19|20)\d\d)[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$ 
    // matches a date in yyyy-mm-dd format from 1900-01-01 through 2099-12-31, 
    // with a choice of four separators.

    val regexYYYYmmdd = """^(?<YYYY>(19|20)\d\d)[- /.](?<MM>0[1-9]|1[012])[- /.](?<DD>0[1-9]|[12][0-9]|3[01])$""".r

    val regexmmddYYYY = 
        """^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.]((19|20)\d\d)$""".r

    val dateString = "1967/02/20"
    val date = dateString match
        case regexYYYYmmdd(year, _, month, day) => s"$month/$day/$year"
        // case regexmmddYYYY(month, day, year, _) => s"$month/$day/$year"
        case _ => "blank"

    println(date)


    val regex2 = """\b(\d{4})(0[1-9]|1[0-2])(0[1-9]|[12]\d|30|31)\b""".r


    val dateString2 = "3/12/2000"
    val rDateDDMMYYYY = 
        """(?<DD>[0-3]?[0-9])[-/.](?<MM>[01]?[0-9])[-/.](?<YYYY>[0-2][0-9][0-9][0-9])""".r

    val date2 = dateString2 match
        case rDateDDMMYYYY(day, month, year) => s"$dateString2 => $day-$month-$year"
        case _ => s"Not Found"

    println(date2)
    
    def dateMatcher(date: String, format: String): String = 
        ???

    // Givens and the Using Clause
    println("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")
    println("Givens and Using".toUpperCase)
    println()

    val aList = List(2,4,3,1)
    implicit val descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)

    val anOrderedList = aList.sorted
    println(anOrderedList)

    

    
