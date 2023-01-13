object SCALA_1_12_2023 extends App:

    // Below is base off article:
        // https://www.47deg.com/blog/smart-constructors-in-scala/
    // I added some logic

    import scala.util.{ Try, Success, Failure }
    class User

    final case class DatabaseId private (value: String):
        private def copy: Unit = ()
    // Create an ADT, we can contrain a database ID to be
    // a particular type

    // Smart Constructors
    object DatabaseId:
        val regex = """[0-9]{12}""".r 
        def apply(value: String): Try[DatabaseId] = 
            if(regex.matches(value)) Success(new DatabaseId(value))
            else Failure(new Exception("Invalid database ID"))

        def create(value: String): Try[DatabaseId] = apply(value)
        
    
    def retreiveRecord(id: DatabaseId): Try[User] = ??? // would be IO[User]


    val dbID = DatabaseId("123456789234")
    println(dbID) //Success(DatabaseId(123456789234))
    

    val invalidDBID = DatabaseId("12345643")
    println(invalidDBID) //Failure(java.lang.Exception: Invalid database ID)

    // We can still use new to create an invalid databaseId:
    // val invalidDBIDBypass = new DatabaseId("123 Invalid ID")
    // println(invalidDBIDBypass) //DatabaseId(123 Invalid ID)
    // ^^^ this now wont work (after final and private)

    // We don't want this to be able to happen, so...

    // we make DatabaseID final AND the constructor fields private

    //We can still create invalid objects using 
    // the compiler-provided copy method:
    val validated: Try[DatabaseId] = DatabaseId("0123456789ab")
    // val invalid = validated.map(_.copy(value = "not valid"))

    // added the private def copy to return unit so none of our
    // users can create invalid DatabaseId with that built in method

    // Other approaches we may want to look at here is to make 
    // the apply method private too, and then provide a more 
    // descriptive API to indicate what is happening.

    val newDatabaseId = DatabaseId.create("333333333333")
    println(newDatabaseId) //Success(DatabaseId(333333333333))
    val newDatabaseIdInvalid = DatabaseId.create("3a3333a33a")
    println(newDatabaseIdInvalid) //Failure(java.lang.Exception: Invalid database ID)

    // Side Note: Applying a function while extracting a value from
    // a Try/Option
    val addDashes = (s: String) => {
        s.patch(3,"-", 0).patch(7,"-",0)
    }

    val insertedDashes = newDatabaseId match
        case Success(dbid) => addDashes(dbid.value)
        case Failure(e) => Failure(e)

    println(insertedDashes) //333-333-333333

    

    
    



