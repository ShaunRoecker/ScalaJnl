object SCALA_12_18_2022 extends App {
    /*                     __                                               
    **     ________ ___   / /  ___                  
    **    / __/ __// _ | / /  / _ |         
    **  __\ \/ /__/ __ |/ /__/ __ |                     
    ** /____/\___/_/ |_/____/_/ | |                                         
    **                          |/           
    */

    // DAILY CTCI 1.3 && 1.4
    /////////////////////////////////////////////////////////
    // 1.5 (pg.199)  
    // Given 2 strings, write a function to check if they are
    // one edit or zero edits away
    // pale, ple  --> true
    // pales, pale --> true
    // pale, bale --> true
    // pale, bae --> false

    import scala.annotation.tailrec

    @tailrec
    def isEdit(s1: String, s2: String): Boolean = (s1, s2) match 
        case (x, y) if x == y => true
        case (x, y) if x.headOption == y.headOption => isEdit(x.tail, y.tail)
        case (x, y) if x.length == y.length => x.tail == y.tail
        case (x, y) if x.length < y.length => x == y.tail
        case (x, y) if x.length > y.length => x.tail == y
        case _ => false
    
    // 1.6 (pg.201)
    /////////////////////////////////////////////////////////
    
    def compress(str: String): String =
        @tailrec
        def helper(s: String, acc: List[(Char, Int)]): List[(Char, Int)] =
            s match
                case x if x.isEmpty => acc
                case x if acc.isEmpty || x.head != acc.head._1 => helper(s.tail, (s.head, 1) :: acc)
                case _ => helper(s.tail, (acc.head._1, acc.head._2 + 1) :: acc.tail)
        helper(str, List()).reverse.map(x => x._1.toString + x._2.toString).mkString
              
    /////////////////////////////////////////////////////////

    
    def sumST(list: List[Int]): Int = list match
        case Nil => 
            val stackTraceAsArray = Thread.currentThread.getStackTrace
            stackTraceAsArray.foreach(println)
            0
        case x :: xs => x + sumST(xs)
    
    val list10 = List.range(1, 5)
    //println(sumST(list10))

    @tailrec
    private def sumWithAccumulator(list: List[Int], accumulator: Int): Int =
        list match
            case Nil => accumulator
            case x :: xs => sumWithAccumulator(xs, accumulator + x)
    
    val list11 = List(1,2,3,4,5)


    def sumX(list: List[Int]): Int = sumWithAccumulator(list, 0)
    println(sumX(list11))
        

    // write the sum function like this to limit it's scope:
    def sum(list: List[Int]): Int = 
        @tailrec
        def sumWithAccumulator(list: List[Int], accumulator: Int): Int =
        list match
            case Nil => accumulator
            case x :: xs => sumWithAccumulator(xs, accumulator + x)
        sumWithAccumulator(list, 0)
    

    def subtract(list: List[Int]): Int =
        @tailrec
        def subtractRec(list: List[Int], acc: Int): Int =
            list match
                case Nil => acc
                case x :: xs => subtractRec(xs, acc - x)
        subtractRec(list, 0)
    
    println(subtract(list11))

    def max(list: List[Int]): Int =
        @tailrec
        def maxRec(list: List[Int], max: Int): Int =
            list match
                case Nil => max
                case x :: xs => 
                    val newMax = if (x > max) x else max
                    maxRec(xs, newMax)
        maxRec(list, 0)
    
    println(max(List(100, 5, 8, 55, 20)))

    // calculate fibbonacci sequence recursively
    def fibbonacci(x: Int): BigInt =
        @tailrec 
        def fibbonacciRec(x : Int, prev: BigInt = 0, next: BigInt = 1): BigInt =
            x match
                case 0 => prev
                case 1 => next
                case _ => fibbonacciRec(x - 1, next, (next + prev))
        fibbonacciRec(x)

    println(fibbonacci(10)) // 55

    // handling state with immutable values
    case class GameState(
        numFlips: Int,
        numCorrect: Int
    )
    
    val s = GameState(0, 0)
    
    def showPrompt(): Unit = { print("\n(h)eads, (t)ails, (q)uit\n\n") }

    import scala.io.StdIn.readLine
    def getUserInput() = readLine.trim.toUpperCase

    def printableFlipResult(flip: String): String = flip match {
        case "H" => "Heads"
        case "T" => "Tails"
    }

    def printGameState(printableFlipResult: String, gameState: GameState): Unit = {
        print(s"Flip was $printableFlipResult. ")
        printGameState(gameState)
    }

    def printGameState(gameState: GameState): Unit = {
        println(s"#Flips: ${gameState.numFlips}, #Correct: ${gameState.numCorrect}")
    }

    def printGameOver(): Unit = println("\n=== GAME OVER ===")
    
    import scala.util.Random
    val r = new Random

    def tossCoin(r: Random): String = {
        val i = r.nextInt(2)
        i match {
            case 0 => "H"
            case 1 => "T"
        }
    }
    

    @tailrec
    def mainLoop(gameState: GameState, random: Random): Unit = {
        showPrompt()
        val userInput = getUserInput()
        // handle the result
        userInput match {
            case "H" | "T" => {
                val coinTossResult = tossCoin(random)
                val newNumFlips = gameState.numFlips + 1
                if (userInput == coinTossResult) {
                    val newNumCorrect = gameState.numCorrect + 1
                    val newGameState = gameState.copy(numFlips = newNumFlips, numCorrect = newNumCorrect)
                    printGameState(printableFlipResult(coinTossResult), newGameState)
                    mainLoop(newGameState, random)
                } else {
                    val newGameState = gameState.copy(numFlips = newNumFlips)
                    printGameState(printableFlipResult(coinTossResult), newGameState)
                    mainLoop(newGameState, random)
                }
            }
            case _   => {
                printGameOver()
                printGameState(gameState)
                // return out of the recursion here
            }
        }
    }
    //mainLoop(s, r)
    // case classes
    case class Person(name: String, relation: String)

    val christina = Person("Christina", "niece")

    println(christina.name)

    christina match 
        case Person(n, r) => println(s"Name: ${n}, Relation: ${r}")

    case class BaseballTeam(name: String, lastWorldSeriesWin: Int)

    val cubs1908 = BaseballTeam("Chicago Cubs", 1908)

    val cubs2016 = cubs1908.copy(lastWorldSeriesWin = 2016)

    val hannah = Person("Hannah", "niece")
    println(christina == hannah) // false

    // Copy Method with FP
    case class BillingInfo(
    creditCards: Seq[CreditCard]
    )

    case class Name(
        firstName: String, 
        mi: String, 
        lastName: String
    )

    case class User(
        id: Int, 
        name: Name, 
        billingInfo: BillingInfo, 
        phone: String, 
        email: String
    )

    case class CreditCard(
        name: Name, 
        number: String, 
        month: Int, 
        year: Int, 
        cvv: String
    )

    val hannahsName = Name(
            firstName = "Hannah",
            mi = "C",
            lastName = "Jones"
    )

    // create hannah1
    val hannah1 = User(
        id = 1,
        name = hannahsName,
        phone = "907-555-1212",
        email = "hannah@hannahjones.com",
        billingInfo = BillingInfo(
            creditCards = Seq(
                CreditCard(
                    name = hannahsName,
                    number = "1111111111111111",
                    month = 3,
                    year = 2020,
                    cvv = "123"
                )
            )
        )
    )
    println(hannah1)

    // hannah2
    val hannah2 = hannah1.copy(phone = "720-555-1212")
    println("\nhannah2: new phone")
    println(hannah2)

    // hannah3: new last name
    val newName = hannah2.name.copy(lastName = "Smith")
    val hannah3 = hannah2.copy(name = hannah2.name.copy(lastName = "XXXXXXSmithXXXXXX"))
    println("\nhannah3: new last name")
    println(hannah3)

    // hannah4
    val oldCC = hannah3.billingInfo.creditCards(0)
    val newCC = oldCC.copy(name = newName)
    val newCCs = Seq(newCC)
    val hannah4 = hannah3.copy(billingInfo = BillingInfo(newCCs))
    println("\nhannah4: new name on cc")
    println(hannah4)   

    // Advanced for comprehensions

    // def stackManip: State[Stock, Int] = 
    //     for
    //         _ <- push(3)
    //         a <- pop
    //         b <- pop
    //     yield b

    case class Caveman(firstName: String, lastName: String)

    val cavemen = List(
        Caveman("barney", "rubble"),
        Caveman("fred", "flintstone")
    )

    val namesStartingWithB = for {
        c <- cavemen
        fname = c.firstName
        if (fname startsWith "b")
    } yield fname.toUpperCase

    println(namesStartingWithB)

    val namesStartingWithBMapped = cavemen.filter(_.firstName.startsWith("b"))
                                          .map(_.firstName.toUpperCase)
    println(namesStartingWithBMapped)

    


    
    

    

    

    
    
    
            

}
