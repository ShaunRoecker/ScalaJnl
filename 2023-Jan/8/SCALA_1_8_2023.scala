object SCALA_1_8_2023:
    // Functional And Reactive Domain Modeling
    import java.util.{ Date, Calendar }
    import scala.util.{ Try, Success, Failure }

    def today = Calendar.getInstance.getTime
    type Amount = BigDecimal
    
    // Case classes model ADTs in Scala, and we use them
    // to hold state information
    case class Balance(
        amount: Amount = 0
    )

    case class Account(
        no: String, 
        name: String,
        dateOfOpening: Date,
        balance: Balance = Balance()
    )

    // We use trait to modularize functionality in Scala FP
    trait AccountServices:
        def debit(a: Account, amount: Amount): Try[Account] =
            if (a.balance.amount < amount)
                Failure(new Exception("Insufficient balance in account"))
            else 
                Success(a.copy(balance = Balance(a.balance.amount + amount)))

        def credit(a: Account, amount: Amount): Try[Account] = 
            Success(a.copy(balance = Balance(a.balance.amount + amount)))

    // Extend the trait to a singleton object
    object AccountServices extends AccountServices

    import AccountServices.*

    val a = Account("account1", "John", today)
    println(a.balance == Balance(0)) //true

    val b = credit(a, 1000)

    println(
        b match 
            case Success(value) => value
            case Failure(e) => e
    )
    //Account(account1,John,Sun Jan 08 11:31:22 EST 2023,Balance(1000))

    // 
    val newAccount = for
        b <- credit(a, 1000)
        c <- debit(b, 200)
        d <- debit(c, 500)
    yield d

    println(newAccount)

    // functional composition
    val inc1 = (n: Int) => n + 1
    val square = (n: Int) => n * n

    println( (1 to 10).map(inc1) )
    println( (1 to 10) map inc1 )

    val incAndSquare = inc1 andThen square // `andThen` runs the first function first
    println( incAndSquare(4) ) // 25

    val squareAndInc = inc1 compose square // `compose` runs the second function first
    println( squareAndInc(4) ) // 17


    object Verifications:
        def verifyRecord(x: Any): Boolean = true

    
    class Customer

    trait AccountService:
        def verifyCustomer(customer: Customer): Option[Customer] =
            if (Verifications.verifyRecord(customer)) Some(customer) else None

        def openCheckingAccount(customer: Customer, effectiveDate: Date): Option[Customer] = ???


    // Scala 2 enums:
        sealed trait Currency
        case object USD extends Currency
        case object BTC extends Currency
        case object LTC extends Currency
        case object EUR extends Currency


object ADTsAndPatterMatching extends App:
    import java.util.{ Date, Calendar }
    import scala.util.{ Try, Success, Failure }

    def today = Calendar.getInstance.getTime

    case class Account(
        no: String, 
        name: String,
        dateOfOpening: Date,
        balance: Balance = Balance(0, USD, today)
    )

    sealed trait Instrument

    case class Equity(
        isin: String, 
        name: String, 
        dateOfIssue: Date
    ) extends Instrument

    case class FixedIncome(
        isin: String, 
        name: String, 
        dateOfIssue: Date, 
        issueCurrency: Currency,
        nominal: BigDecimal
    ) extends Instrument

    sealed trait Currency extends Instrument
    case object USD extends Currency
    case object JPY extends Currency

    // enum Currency extends Instrument:
    //     case USD, JPY
    
    case class Amount(
        a: BigDecimal, 
        c: Currency
    ):
        def + (that: Amount) =
            require(that.c == c)
            Amount(a + that.a, c)

    case class Balance(
        amount: BigDecimal,
        ins: Instrument,
        asOf: Date
    )

    def getMarketValue(e: Equity, a: BigDecimal): Amount = Amount(100, USD)
    def getAccruedInterest(i: String): Amount = Amount(110, USD)

    def getHolding(account: Account): Amount = account.balance match
        case Balance(a, c: Currency, _) => Amount(a, c)
        case Balance(a, e: Equity, _)   => getMarketValue(e, a)
        case Balance(a, FixedIncome(i, _, _, c, n), _) =>
            Amount(n * a, c) + getAccruedInterest(i)

    
    extension(i: Int)
        def **(n: Int): Int =
            val list = List.fill(n)(i) 
            list.reduce(_ * _)

    println(5 ** 2)

    


    
    
    



    

    

    

    