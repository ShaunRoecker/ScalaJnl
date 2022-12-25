

import java.util.regex.Pattern
import scala.util.matching.Regex

object SCALA_12_25_2022 extends App:
    // REGEX
    def rgxOne(s: String) =
        val r1 = """(\.\d\d[1-9]?)\d*""".r


    // reading a text file to a list
    import scala.io.Source

    val file = Source.fromFile("myFile.txt").getLines.toList
    println(file.head)

    for (line <- file) do println(line)

    val date = raw"(\d{4})-(\d{2})-(\d{2})".r

    "2004-01-20" match 
        case date(year, _*) => println(s"$year was a good year for PLs.")

    "2004-01-20" match 
        case date(_*) => println("It's a date!")

    // In a pattern match, regex usually matches the entire input
    // However, an unanchored regex finds a pattern anywhere in the input
    val imbeddedDate = date.unanchored
    val longString = """Date: 2004-01-20 2005-01-20 17:25:18 GMT (10 years,  
                       |8 weeks, 5 days, 17 hours and 51 minutes ago)""".stripMargin
    
    println(longString)

    // this finds the first match
    longString match 
        case imbeddedDate(year, _*) => println(s"In $year, a star is born 1")

    // In 2004, a star is born
    
    val replaceYear = longString match 
        case imbeddedDate(year, _*) => longString.replaceAll(year, "2005")

    println(replaceYear)

    // Date: 2005-01-20 17:25:18 GMT (10 years,  
    // 8 weeks, 5 days, 17 hours and 51 minutes ago)

    val dates = "Important dates in history: 2004-01-20, " +
                "1958-09-05, 2010-10-06, 2011-07-15"

    val firstDate = date.findFirstIn(dates).getOrElse("No date found")
    println(firstDate) //2004-01-20


    val firstYear = for (m <- date.findFirstMatchIn(dates)) yield m.group(1)
    println(firstYear) //Some(2004)

    firstYear match 
        case Some(x) => println(x)
        case None => println("None found")



    val allYears = for (m <- date.findAllMatchIn(dates)) yield m.group(1)
    println(allYears.isInstanceOf[List[String]]) //false
    val allYearsList = allYears.toList
    println(allYearsList) //List(2004, 1958, 2010, 2011)

    val allYM = for (m <- date.findAllMatchIn(dates)) yield (m.group(1), m.group(2))
    val list1 = allYM.toList
    println(list1) //List((2004,01), (1958,09), (2010,10), (2011,07))
    val unzippedListY = list1.unzip._1
    val unzippedListM = list1.unzip._2
    println(unzippedListY) //List(2004, 1958, 2010, 2011)
    println(unzippedListM) //List(01, 09, 10, 07)

    println(date.matches("2018-03-01"))                     // true
    println(date.matches("Today is 2018-03-01"))            // false
    println(date.unanchored.matches("Today is 2018-03-01")) // true

    val mi = date.findAllIn(dates) //this is an iterator
    while (mi.hasNext) 
        val d = mi.next
        if (mi.group(1).toInt < 1960) println(s"$d: An oldie but goodie.")

    val num = raw"(\d+)".r
    val all = num.findAllIn("123").toList  // List("123"), not List("123", "23", "3")
    println(all)

    val redacted = date.replaceAllIn(dates, "XXXX-XX-XX")
    println(redacted)
    // Important dates in history: XXXX-XX-XX, XXXX-XX-XX, XXXX-XX-XX, XXXX-XX-XX

    val yearsOnly = date.replaceAllIn(dates, m => m.group(1))
    println(yearsOnly) //Important dates in history: 2004, 1958, 2010, 2011

    import java.util.Calendar
    val months = (0 to 11).map { i => 
        val c = Calendar.getInstance 
        c.set(2014, i, 1) 
        f"$c%tb" 
    }

    println(months) //Vector(Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec)

    val c = Calendar.getInstance
    println()
    println(c)
    println()
    println(c.getTimeZone())
    println()
    println(c.get(Calendar.YEAR)) //2022
    println(c.get(Calendar.WEEK_OF_YEAR)) //53

    println(c.get(Calendar.SECOND)) //14
    // a few seconds later....
    // Thread.sleep(5000)
    println(c.get(Calendar.SECOND)) //14

    // The calendar only continues between runtimes, its doesn't cahange
    // when pausing the thread

    // get the max possible value for a calender value
    val maxWeeksInYear = c.getMaximum(Calendar.WEEK_OF_YEAR)
    println(maxWeeksInYear) //53

    val reformatted = date.replaceAllIn(dates, _ match { 
        case date(y,m,d) => f"${months(m.toInt - 1)} $d, $y" 
    })

    println(reformatted)






    

    



    

    






