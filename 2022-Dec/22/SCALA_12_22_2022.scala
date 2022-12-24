object SCALA_12_22_2022 extends App {
    println("12/22/2022")
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 2.4 && 2.5
    /////////////////////////////////////////////////////////
    // 2.4 (pg.209)  


    /////////////////////////////////////////////////////////
    // 2.5 (pg.209)
  

    val date = raw"(\d{4})-(\d{2})-(\d{2})".r
    "2004-01-20" match 
        case date(year, month, day) => println(s"$year was a good year for PLs.")

    // val input = scala.io.StdIn.readLine()
    // println(s"Input: $input")
    // println(input.isInstanceOf[String]) //true

    // println("Enter temperature in Celcius: ")
    // val celcius = scala.io.StdIn.readLine()
    // val rCelcius = raw"\\A[0-9]+\\z".r

    // if (rCelcius == raw"\\A[0-9]+\\z".r) 
    //     val celcius2 = celcius.toFloat
    //     val fahrenheit = (celcius2 * 9 / 5) + 32
    //     println(s"${celcius} is ${fahrenheit} F\n")
    // else
    //     println("Expecting a number")

    val r2 = raw"[0-9]".r
    val str2 = "12345"

    val matches2 = r2.findAllIn(str2)
    println(matches2.toList) //List(1, 2, 3, 4, 5)


    



    






}
