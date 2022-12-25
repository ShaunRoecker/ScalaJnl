object SCALA_12_23_22:
    def main(args: Array[String]): Unit =
        println("12/23/22")

        val r1 = """^([-+]?[0-9]+(\.[0-9]*)?)([CF])?$""".r
        val temp = "+11.8"
        println(r1.matches(temp))
        
        val r2 = """^(.*)?(\d{4})(.*)?$""".r
        val num1 = "       4450        "
        println(r2.matches(num1))

        val r3 = """[0-9]""".r 

        val date = """(\d{4})[-./](\d{2})[-./](\d{2})""".r
        "2004-01-20" match 
            case date(year, month, day) => println(s"$year was a good year for PLs.")

        val r4 = """[    ]+([0-9]*)""".r
        val tab = "    9" //tab is 4 spaces
        println(r4.matches(tab))

        import scala.util.matching.Regex

        val numberPattern: Regex = "[a-zA-Z0-9]+".r

        numberPattern.findFirstMatchIn("awesomepassword") match
            case Some(_) => println("Password OK")
            case None => println("Password must contain a number")


        val keyValPattern: Regex = "([0-9a-zA-Z- ]+): ([0-9a-zA-Z-#()/. ]+)".r

        val input: String =
              """background-color: #A03300;
                |background-image: url(img/header100.png);
                |background-position: top center;
                |background-repeat: repeat-x;
                |background-size: 2160px 108px;
                |margin: 0;
                |height: 108px;
                |width: 100%;""".stripMargin

        for patternMatch <- keyValPattern.findAllMatchIn(input) do
            println(s"key: ${patternMatch.group(1)} value: ${patternMatch.group(2)}")

        def saveContactInfomation(contact: String): Unit =
            import scala.util.matching.Regex

            val emailPattern: Regex = """^(\w+)@(\w+(.\w+)+)$""".r
            val phonePattern: Regex = """^(\d{3}-\d{3}-\d{4})$""".r

            contact match
                case emailPattern(localPart, domainName, _) =>
                    println(s"Hi $localPart, we have saved your email address.")
                case phonePattern(phoneNumber) =>
                    println(s"Hi, we have saved your phone number $phoneNumber.")
                case _ =>
                    println("Invalid contact information, neither an email address nor phone number.")

        println()
        saveContactInfomation("123-456-7890")
        saveContactInfomation("JohnSmith@sample.domain.com")
        saveContactInfomation("2 Franklin St, Mars, Milky Way")

        //  Scala FP
        case class Debuggable[A](value: A, message: String):
            def map[B](f: A => B): Debuggable[B] =
                val newValue = f(this.value)
                Debuggable(newValue, this.message)

            def flatMap[B](f: A => Debuggable[B]): Debuggable[B] =
                val newValue: Debuggable[B] = f(this.value)
                Debuggable(newValue.value, this.message + "\n" + newValue.message)

        def f2(a: Int): Debuggable[Int] = 
            val result = a * 2
            val message = s"f: a ($a) * 3 = $result"
            Debuggable(result, message)

        def g2(a: Int): Debuggable[Int] = 
            val result = a * 3
            val message = s"g: a ($a) * 3 = $result"
            Debuggable(result, message)

        def h2(a: Int): Debuggable[Int] = 
            val result = a * 4
            val message = s"h: a ($a) * 3 = $result"
            Debuggable(result, message)

        val finalResult2 = for {
            fResult <- f2(100)
            gResult <- g2(fResult)
            hResult <- h2(gResult)
        } yield hResult

        println(finalResult2)

        case class Wrapper[A](value: A, log: List[String]):
            def map[B](f: A => B): Wrapper[B] =
                val newValue = f(this.value)
                Wrapper(newValue, this.log)
            
            def flatMap[B](f: A => Wrapper[B]): Wrapper[B] =
                val newValue: Wrapper[B] = f(value)
                Wrapper(newValue.value, this.log ::: newValue.log)

        
        def f(a: Int): Wrapper[Int] =
            val result = a * 2
            Wrapper(result, List(s"f: multiply $a * 2 = $result"))

        val newResult = for {
            a <- f(100)
            b <- f(a)
            c <- f(b)
        } yield c

        println(newResult.value)
        println(newResult.log)

        newResult.log.foreach(x => println(s"Log: $x"))

        val newList = 1 :: Nil

        // IO Monad

        class IO[A] private (constructorCodeBlock: => A) {

            def run = constructorCodeBlock

            def flatMapOrig[B](f: A => IO[B]): IO[B] = IO(f(run).run)

            def flatMap[B](customAlgorithm: A => IO[B]): IO[B] = {
                val result1: IO[B] = customAlgorithm(run)
                val result2: B = result1.run
                IO(result2)
            }

            def map[B](f: A => B): IO[B] = flatMap(a => IO(f(a)))

        }

        object IO {
            def apply[A](a: => A): IO[A] = new IO(a)
        }

        // IO Methods
        ////////////////////////////////////////////////////////////////
        def getLine: IO[String] = IO(scala.io.StdIn.readLine())
        def putStrLn(s: String): IO[Unit] = IO(println(s))
        ////////////////////////////////////////////////////////////////


        val first = for {
            _           <- putStrLn("First name?")
            firstName   <- getLine
            _           <- putStrLn("Last name?")
            lastName    <- getLine
            _           <- putStrLn(s"First: ${firstName} Last: ${lastName}")
        } yield firstName

        

        



        


        






        


        
            


    



