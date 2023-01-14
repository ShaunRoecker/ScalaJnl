object SCALA_1_13_2023_3 extends App:
    import java.util.{ Date, Calendar }
    import scala.util.{ Try, Success, Failure }
    import collection.mutable.{ Map => MMap }

    object common:
        type Amount = BigDecimal
        def today = Calendar.getInstance.getTime
    

    import common._

    case class Balance(amount: Amount = 0)

    case class Account(
        no: String, 
        name: String, 
        dateOfOpening: Date = today, 
        dateOfClosing: Option[Date] = None, 
        balance: Balance = Balance(0)
    )
    
    // module
    trait AccountService[Account, Amount, Balance]:
        def open(no: String, name: String, openingDate: Option[Date]): Try[Account]
        def close(account: Account, closeDate: Option[Date]): Try[Account]
        def debit(account: Account, amount: Amount): Try[Account]
        def credit(account: Account, amount: Amount): Try[Account]
        def balance(account: Account): Try[Balance]

        def transfer(from: Account, to: Account, amount: Amount): Try[(Account, Account, Amount)] = 
            for {
                a <- debit(from, amount)
                b <- credit(to, amount)
            } yield (a, b, amount)

    
    object AccountService extends AccountService[Account, Amount, Balance]:
        def open(no: String, name: String, openingDate: Option[Date], repo: AccountRepository) = 
            repo.query(no) match 
                case Success(Some(a)) => Failure(new Exception(s"Already existing account with no $no"))
                case Success(None) => {
                    if (no.isEmpty || name.isEmpty) 
                        Failure(new Exception(s"Account no or name cannot be blank") )
                    else if (openingDate.getOrElse(today) before today) 
                        Failure(new Exception(s"Cannot open account in the past"))
                    else repo.store(Account(no, name, openingDate.getOrElse(today)))
                    }
                case Failure(ex) => Failure(new Exception(s"Failed to open account $no: $name", ex))
            

        def close(no: String, closeDate: Option[Date], repo: AccountRepository) =
            repo.query(no) match 
                case Success(Some(a)) =>
                    if (closeDate.getOrElse(today) before a.dateOfOpening) 
                    Failure(new Exception(s"Close date $closeDate cannot be before opening date ${a.dateOfOpening}")) 
                    else repo.store(a.copy(dateOfClosing = closeDate))
                case Success(None) => Failure(new Exception(s"Account not found with $no"))
                case Failure(ex) => Failure(new Exception(s"Fail in closing account $no", ex))
            

        def debit(no: String, amount: Amount, repo: AccountRepository) =
            repo.query(no) match 
                case Success(Some(a)) =>
                    if (a.balance.amount < amount) Failure(new Exception("Insufficient balance"))
                    else repo.store(a.copy(balance = Balance(a.balance.amount - amount)))
                case Success(None) => Failure(new Exception(s"Account not found with $no"))
                case Failure(ex) => Failure(new Exception(s"Fail in debit from $no amount $amount", ex))
            
        def credit(no: String, amount: Amount, repo: AccountRepository) =
            repo.query(no) match 
                case Success(Some(a)) => repo.store(a.copy(balance = Balance(a.balance.amount + amount)))
                case Success(None) => Failure(new Exception(s"Account not found with $no"))
                case Failure(ex) => Failure(new Exception(s"Fail in credit to $no amount $amount", ex))
            
        def balance(no: String, repo: AccountRepository) = repo.balance(no)


    // Repository module
    trait Repository[A, IdType]:
        def query(id: IdType): Try[Option[A]]
        def store(a: A): Try[A]
        

    trait AccountRepository extends Repository[Account, String]:
        def query(no: String): Try[Option[Account]]
        def store(a: Account): Try[Account]
        def balance(no: String): Try[Balance] = query(no) match {
            case Success(Some(a)) => Success(a.balance)
            case Success(None) => Failure(new Exception(s"No account exists with no $no"))
            case Failure(ex) => Failure(ex)
        }
        def query(openedOn: Date): Try[Seq[Account]]

    
    trait AccountRepositoryInMemory extends AccountRepository:
        lazy val repo = MMap.empty[String, Account]

        def query(no: String): Try[Option[Account]] = Success(repo.get(no))
        def store(a: Account): Try[Account] = {
            val r = repo += ((a.no, a))
            Success(a)
        }
        def query(openedOn: Date): Try[Seq[Account]] = 
            Success(repo.values.filter(_.dateOfOpening == openedOn).toSeq)
        

    object AccountRepositoryInMemory extends AccountRepositoryInMemory
    import AccountRepositoryInMemory.*


    val account = Account("001", "Account 1")

    store(account)
    println(repo) //HashMap(001 -> Account(001,Account 1,Fri Jan 13 21:08:46 EST 2023,None,Balance(0)))

    println(query(today))

    import AccountService._

    val r = AccountRepositoryInMemory
    def op(no: String) = for {
        _ <- credit(no, BigDecimal(100), r)
        _ <- credit(no, BigDecimal(300), r)
        _ <- debit(no, BigDecimal(160), r)
        b <- balance(no, r)
    } yield b
