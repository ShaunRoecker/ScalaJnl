object SCALA_1_11_2023:
    val list = List(List(1,2,3), List(4,5,6))

    println(list.foldLeft(List[Int]())(_ ::: _))
    println(list.reduce(_ ::: _))
    
    // Functional Domain Modeling
    def calculateInterest: SavingsAccount => BigDecimal = { a =>
        a.balance.amount * a.rate   
    }

    val calculateInterest2 = (a: SavingsAccount) => 
        a.balance.amount * a.rate

    def deductTax: BigDecimal => BigDecimal = { interest =>
        if (interest < 1000) interest else (interest - 0.1 * interest)    
    }

    trait Account:
        def number: String
        def name: String
        def balance: Balance

    case class SavingsAccount(
        number: String,
        name: String,
        balance: Balance,
        rate: BigDecimal
    ) extends Account

    case class Balance(amount: BigDecimal)

    val a1 = SavingsAccount("a-0001", "ibm", Balance(100000), 0.12)
    val a2 = SavingsAccount("a-0002", "google", Balance(2000000), 0.13)
    val a3 = SavingsAccount("a-0003", "chase", Balance(125000), 0.15)

    val accounts = List(a1, a2, a3)

    val x1 = accounts.map(calculateInterest2).map(deductTax)
    // the below technique is called fusion
    // we are composing functions within the map so we only have
    // to run 1 map
    // instead of: functor.map(f(x)).map(g(x))
    // we do: functor.map(g(f(x)))
    val x2 = accounts.map(calculateInterest andThen deductTax)

    println(x1) //List(10800.000, 234000.000, 16875.000)
    println(x2) //List(10800.000, 234000.000, 16875.000)

object ApiImplimentation extends App:
    import java.util.{ Date, Calendar }
    import util.{ Try, Success, Failure }
    type Amount = BigDecimal
    def today = Calendar.getInstance.getTime

    case class Balance(amount: Amount = 0)
    
    case class Account(
        no: String,
        name: String,
        dateOfOption: Date,
        dateOfOpening: Date = today,
        dateOfClosing: Option[Date] = None,
        balance: Balance = Balance(0)
    )

    trait AccountService[Account, Amount, Balance]:
        def open(no: String, name: String, openingDate: Option[Date]): Try[Account]
        def close(account: Account, closeDate: Option[Date]): Try[Account]
        def debit(account: Account, amount: Amount): Try[Account]
        def credit(account: Account, amount: Amount): Try[Account]
        def balance(account: Account): Try[Balance]

        def transfer(from: Account, to: Account, amount: Amount): Try[(Account, Account, Amount)] = for {
            a <- debit(from, amount)
            b <- credit(to, amount)
        } yield (a, b, amount)
    

    object AccountService extends AccountService[Account, Amount, Balance]:
        def open(no: String, name: String, openingDate: Option[Date]): Try[Account] = 
            if (no.isEmpty || name.isEmpty) Failure(new Exception(s"Account no or name cannot be blank") )
            else if (openingDate.getOrElse(today) before today) Failure(new Exception(s"Cannot open account in the past"))
            else Success(Account(no, name, openingDate.getOrElse(today)))
        

        def close(account: Account, closeDate: Option[Date]): Try[Account] = 
            val cd = closeDate.getOrElse(today)
            if (cd before account.dateOfOpening) 
            Failure(new Exception(s"Close date $cd cannot be before opening date ${account.dateOfOpening}")) 
            else Success(account.copy(dateOfClosing = Some(cd)))
        

        def debit(a: Account, amount: Amount): Try[Account] = 
            if (a.balance.amount < amount) Failure(new Exception("Insufficient balance"))
            else Success(a.copy(balance = Balance(a.balance.amount - amount)))
        

        def credit(a: Account, amount: Amount): Try[Account] = 
            Success(a.copy(balance = Balance(a.balance.amount + amount)))

        def balance(account: Account): Try[Balance] = Success(account.balance)
        








    

  

