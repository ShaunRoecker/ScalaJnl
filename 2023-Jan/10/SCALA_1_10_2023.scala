import java.text.DateFormat
object SCALA_1_10_2023:
    // functional domain modeling
    import java.util.{ Date, Calendar }
    import scala.util.{ Try, Success, Failure }

    def today = Calendar.getInstance.getTime
    type Amount = BigDecimal

    // dummy object for external side effect process
    object Verifications:
        def verifyRecord(customer: Customer) = true

    import Verifications.*


    case class Balance(amount: Amount = 0)
    case class Account(no: String, name: String, dateOfOpening: Date, balance: Balance = Balance())
    class Customer
    
    trait AccountService:
        def debit(a: Account, amount: Amount): Try[Account] = 
            if (a.balance.amount < amount) 
                Failure(new Exception("Insufficient balance in account"))
            else Success(a.copy(balance = Balance(a.balance.amount - amount)))
        
        def credit(a: Account, amount: Amount): Try[Account] = 
            Success(a.copy(balance = Balance(a.balance.amount + amount)))

        def transfer(from: Account, to: Account, amount: Amount) = 
            for
                d <- debit(from, amount)
                c <- credit(to, amount)
            yield (d, c)

        def verifyCustomer(customer: Customer): Option[Customer] = 
            if (Verifications.verifyRecord(customer)) Some(customer)
            else None

        def openCheckingAccount(customer: Customer, effectiveDate: Date): Option[Customer] =
            ???
        

        
    object AccountService extends AccountService

    import AccountService.*

    val a = Account("a1", "John", today)
    val b = Account("a2", "Sam", today)

    val a2 = credit(a,  1000) match
        case Success(x) => x
        case Failure(e) => a
    

    println(a2) //(Account(a1,John,Tue Jan 10 17:41:31 EST 2023,Balance(1000))

    val b2 = credit(b, 2000)
    println(b2) //Success(Account(a2,Sam,Tue Jan 10 17:56:41 EST 2023,Balance(2000)))

    val date = new Date
    val dateFormat = DateFormat.getInstance().format(date)
    println(dateFormat)

    // Because we returned our Accounts wrapped in a monad: Try, we can compose
    // functions with for comprehensions

    val z = Account("12345", "Zed", today, Balance(50000))
    val z2 = for 
        b <- credit(z, 2000)
        c <- debit(b, 20000)
        d <- debit(c, 30000)
    yield d

    println(z2) //Success(Account(12345,Zed,Tue Jan 10 18:14:43 EST 2023,Balance(2000)))

    val inc = (n: Int) => n + 1
    val square = (n: Int) => n * n

    val incAndSquare = inc andThen square
    val squareAndInc = inc compose square

    val list = List(1, 2, 3, 4, 5)

    println(list.map(inc))
    println(list.map(square))
    println(list.map(incAndSquare))
    println(list.map(squareAndInc))

    // List(2, 3, 4, 5, 6)
    // List(1, 4, 9, 16, 25)
    // List(4, 9, 16, 25, 36)
    // List(2, 5, 10, 17, 26)

object Polymorphism extends App:
    import java.util.{ Date, Calendar }
    import scala.util.{ Try, Success, Failure }
    case class DateRange(difference: BigDecimal)
    type Amount = BigDecimal
    //base abstraction for domian entity Account
    trait Account:  
        def number: String
        def name: String
        // ... may contain other attributes

    // Extended abstraction for more specific Account type
    trait InterestBearingAccount extends Account:
        def rateOfInterest: BigDecimal


    // Domain Data modeled with Algebraic Data Types
    // Scala case classes are Product ADTs
    // (enums -Scala3, and sealed traits - Scala2 are Sum ADTs)  
    case class CheckingAccount(
        number: String, 
        name: String
    ) extends Account


    case class SavingsAccount(
        number: String, 
        name: String, 
        rateOfInterest: BigDecimal
    ) extends InterestBearingAccount


    case class MoneyMarketAccount(
        number: String, 
        name: String, 
        rateOfInterest: BigDecimal
    ) extends InterestBearingAccount

    
    // Our functionality is separated from our data
    trait AccountService:
        def calculateInterest[A <: InterestBearingAccount]
            (account: A, period: DateRange): Try[BigDecimal] = 
                Try(account.rateOfInterest * period.difference)


    object AccountService extends AccountService
    import AccountService.*
    // examples

    val s1 = SavingsAccount("dg", "sb001", 0.5)
    val s2 = SavingsAccount("sr", "sb002", 0.75)
    val s3 = SavingsAccount("ty", "sb003", 0.27)

    val dateRange = DateRange(10)

    val listCalcInterest = List(s1, s2, s3).map(calculateInterest(_, dateRange))

    println(listCalcInterest) //List(Success(5.0), Success(7.50), Success(2.70))

    val listFold = List(s1, s2, s3)
        .map(calculateInterest(_, dateRange))
        .foldLeft(BigDecimal(0))((a, e) => e.map(_ + a).getOrElse(a))


    println(listFold) //15.20

    val listFilter = List(s1, s2, s3)
        .map(calculateInterest(_, dateRange))
        .filter(_.isSuccess)

    println(listFilter) //List(Success(5.0), Success(7.50), Success(2.70))

    def getCurrencyBalance(account: Account): Try[Amount] = ???

    def getAccountFrom(number: String): Try[Account] = ???

    def calculateNetAssetValue(account: Account, balance: Amount): Try[Amount] = ???

    // val result: Try[(Account, Amount)] = for
    //     s <- getAccountFrom("account1")
    //     b <- getCurrencyBalance(s)
    //     v <- calculateNetAssetValue(s, b)
    //     if (v > 100000)
    // yield (s, v)







    







    

    
    

    

    



        

        

