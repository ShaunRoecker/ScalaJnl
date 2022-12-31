import java.util.Date

object TypeClassesPart3:
    def >> = println("///////////////////////////////////////////////////////////////")

    // Classes
    case class User(name: String, age: Int, email: String)

    case class Book(title: String, pages: Int)

    
    val john = User("John", 32, "john@example.com")
    val book = Book("El Sorborno", 402)
    val date = java.util.Date()
    val integer = 1001

    // Type Class (trait)
    trait HTMLSerializer[T]:
        def serialize(value: T): String

    // Type Class Companion Object
    object HTMLSerializer:
        def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
            serializer.serialize(value)
        // we can create a factory method
        def apply[T](implicit serializer: HTMLSerializer[T]) = serializer


    // An Implicit Type Class Instance for User
    implicit object UserSerializer extends HTMLSerializer[User]:
        def serialize(user: User): String = 
            s"<div>${user.name}:${user.age}:${user.email}</div>"
        
    
    // A Type Class Instance for Book
    implicit object BookSerializer extends HTMLSerializer[Book]:
        override def serialize(book: Book): String = 
            s"""<div>
               |<h1>${book.title}</h1>
               |<h2>Pages: ${book.pages}</h2>
               |</div>""".stripMargin

    // An Implicit Type Class Instance for Date
    implicit object DateSerializer extends HTMLSerializer[Date]:
        def serialize(date: Date): String = s"<h1>${date.toString}</h1>"
    
    // An Implicit Type Class Instance for Int
    implicit object IntSerializer extends HTMLSerializer[Int]:
        override def serialize(value: Int): String = s"<div>$value</div>"

    >>
    // Type Enrichment (Conversion with implicit classes)
    implicit class HTMLEnrichment[T](value: T):
        def toHTML(implicit serializer: HTMLSerializer[T]): String = 
            serializer.serialize(value)

    
    john.toHTML //works

    def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
        s"<html><body>${content.toHTML(serializer)}</body></html>"
    
    // The below is sugar for the above
    // Advantage: Much more concise
    // Disadvantage: can't use "serializer", as its injected implicitly
    def htmlSugar[T : HTMLSerializer](content: T): String =
        val serializer = implicitly[HTMLSerializer[T]]
        s"<html><body>${content.toHTML(serializer)}</body></html>"

    // implicitly
    case class Permissions(mask: String)
    implicit val defaultPermissions: Permissions = Permissions("0744")

    // in some other part of the source code
    // we want to stdout, what is the implicit value of Permissions?
    val standardPerms = implicitly[Permissions]
    println(standardPerms) //Permissions(0744)


    val userA = User("A", 1, "A@example.com")
    val userB = User("B", 2, "B@example.com")


    trait Equality[T]:
        def hasEquality(a: T, b: T): Boolean

    object Equality:
        def apply[T](implicit instance: Equality[T]): Equality[T] = instance

    implicit object UserEquality extends Equality[User]:
        def hasEquality(user1: User, user2: User): Boolean = 
            if user1.email == user2.email then true else false

        def ageDiffOneYear(user1: User, user2: User): Boolean = 
            if (Math.abs(user1.age - user2.age) == 1) then true else false

    implicit object StringEquality extends Equality[String]:
        def hasEquality(s1: String, s2: String): Boolean = 
            if s1 == s2 then true else false

    implicit class EqualityEnrichment[T](value: T):
        def ===(other: T)(implicit equilizer: Equality[T]): Boolean = 
            equilizer.hasEquality(value, other)

        def !==(other: T)(implicit equilizer: Equality[T]): Boolean = 
            !equilizer.hasEquality(value, other)
        

    >>

    println("This" === "That") //false
    println("This" === "This") //true
    println("This" !== "This") //false
    println("This" !== "That") //true

object JSONSerialization:
    

    case class User(name: String, age: Int, email: String)
    case class Post(content: String, createdAt: Date)
    case class Feed(user: User, posts: List[Post])

    // Steps:
        // 1. Create an itermediary data types
        // 2. Create type class instances for conversion from wild types to
                // intermediary type
        // 3. then serialize the intermediate data type to JSON
    
    sealed trait JSONValue: //intermediate data type
        def stringify: String 
           

   
    final case class JSONString(value: String) extends JSONValue:
        def stringify: String =
            "\"" + value + "\""

    final case class JSONNumber(value: Int) extends JSONValue:
        def stringify: String = value.toString
            

    final case class JSONArray(values: List[JSONValue]) extends JSONValue:
        def stringify: String = values.map(_.stringify).mkString("[", ",", "]")


    final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue:
        // This will be the intermediate object that looks like JSON
        def stringify: String = values.map {
            case (key, value) => "\"" + key + "\":" + value.stringify
        }.mkString("{",",","}")


    val data = JSONObject(Map(
        "user" -> JSONString("myfirstUser"),
        "posts" -> JSONArray(List(
            JSONString("Scala saved my life yesterday"),
            JSONNumber(123)
        ))
    ))

    println(data.stringify)


    // type class

    trait JSONConverter[T]:
        def convert(value: T): JSONValue

    // conversion class (enrichment)
    implicit class JSONOps[T](value: T):
        def toJSON(implicit converter: JSONConverter[T]): JSONValue =
            converter.convert(value)

    // Type Class Instances
    // existing data types
    implicit object StringConverter extends JSONConverter[String]:
        def convert(value: String): JSONValue = JSONString(value)

    implicit object NumberConverter extends JSONConverter[Int]:
        def convert(value: Int): JSONValue = JSONNumber(value)

    // custom data types
    implicit object UserConverter extends JSONConverter[User]:
        def convert(user: User): JSONValue = JSONObject(Map(
            "name" -> JSONString(user.name),
            "age" -> JSONNumber(user.age),
            "email" -> JSONString(user.email)
        ))

    implicit object PostConverter extends JSONConverter[Post]:
        def convert(post: Post): JSONValue = JSONObject(Map(
            "content" -> JSONString(post.content),
            "created" -> JSONString(post.createdAt.toString)
        ))

    implicit object FeedConverter extends JSONConverter[Feed]:
        def convert(feed: Feed): JSONValue = JSONObject(Map(
            "user" -> feed.user.toJSON, 
            "posts" -> JSONArray(feed.posts.map(_.toJSON))
        ))

    

    val now = new Date(System.currentTimeMillis())
    val john = User("John", 34, "john@example.com")
    val feed = Feed(john, List(
        Post("hello", now),
        Post("look at this cute puppy", now)
    ))

    println(feed.toJSON.stringify)

object MagnetPattern extends App:

    trait Actor:
        // lots of overloads
        def recieve(statusCode: Int): Int 
        def receive(request: P2PRequest): Int
        def receive(response: P2PResponse): Int
        //def receive[T](message: T)(implicit serializer: Serializer[T]): T
        // same ^v
        def receive[T : Serializer](message: T): Int 
        def receive[T : Serializer](message: T, statusCode: Int): Int
        def receive(future: Future[P2PRequest]): Int
        //def receive(future: Future[P2PResponse]): Int <- wouldn't compile
        // the generic types are erased at compile time so both of these
        // would be Future[]

        // also, lifting doesn't work 
        // val receiveFV = receive _   ??? which is it
        // also, code duplication
        // also, type inferance and default args

    trait MessageMagnet[Result]:
        def apply(): Result

    
    def receive[R](magnet: MessageMagnet[R]): R = magnet()

    implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int]:
        def apply(): Int = 
            // logic for handling P2PRequest
            println("Handling P2P request")
            42

    receive(new P2PRequest)   




    

    



    
    

    







    



    
    
    



  

