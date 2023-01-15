object SCALA_1_14_2023 extends App:
    // Monoids
    trait Monoid[T]:
        def zero: T
        def operation(a: T, b: T): T

    object Monoids:
    // This is actually implemented as an abstract class
        // Scala 3
        given intAdditionMonoid: Monoid[Int] with 
            val zero = 0
            def operation(a: Int, b: Int): Int = a + b
    
        // Scala 2
        // implicit val intAdditionMonoidImpl: Monoid[Int] = new Monoid[Int] {
        //     val zero = 0
        //     def operation(a: Int, b: Int): Int = a + b
        // }

        
        

    // side note- type classes
    trait Searchable[T]:
        def uri(obj: T): String

    def searchWithImplicit[T](obj: T)(using searchable: Searchable[T]): String = 
        searchable.uri(obj)

    case class Customer(taxCode: String, name: String, surname: String)
    case class Policy(policyId: String, description: String)

    // type class instances
    given searchableCustomer: Searchable[Customer] with
        override def uri(customer: Customer): String = s"/customers/${customer.taxCode}" 


    given searchablePolicy: Searchable[Policy] with
        override def uri(policy: Policy): String = s"/policies/${policy.policyId}"


    
    val customer = Customer("1234", "John", "Doe")
    val policy = Policy("PN026754", "This is a policy")

    println(searchWithImplicit(customer)) ///customers/1234
    println(searchWithImplicit(policy)) ///policies/PN026754


    // context bounds
    def searchWithContextBound[S: Searchable](obj: S): String =
        val searchable = implicitly[Searchable[S]]
        searchable.uri(obj)

    //As we can see, the signature of the searchWithContextBound function 
    // is now cleaner than the signature of the searchWithImplicit function 
    // since it exposes only the business parameters, completely hiding the 
    // type class and the implicit resolution mechanism.
    //https://www.baeldung.com/scala/implicitly

    trait Serializer[T]:
        def serialize(value: T): String
  
    given intSerializer: Serializer[Int] with 
        override def serialize(value: Int): String = value.toString

    def serializeIntWithContextBound[T: Serializer](value: T): String =
        val serializer = implicitly[Serializer[T]]
        serializer.serialize(value)

    
    println(serializeIntWithContextBound(199)) // 199

    println(implicitly[Serializer[Int]])

    // How to create a function from a method

    def add2(n: Int): Int = n + 2

    val add2Func = add2 _
    println(add2Func) //SCALA_1_14_2023$$$Lambda$1447/0x00000008014b2a50@1b9c716f

    def double(i: Int) = i * 2
    def triple(i: Int) = i * 3  

    // You can't do this:
    // val functions = Map(
    //     "2x" -> double,
    //     "3x" -> triple
    // )

    // convert the methods to functions to allow this:
    val functions2 = Map(
        "2x" -> double _,
        "3x" -> triple _
    )






    

    

        

    
