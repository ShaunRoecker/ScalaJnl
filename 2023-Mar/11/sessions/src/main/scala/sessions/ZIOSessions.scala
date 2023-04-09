import zio._
import zio.parser._
import zio.json._


object JsonLibrary extends scala.App {

    case class Banana2(curvature: Double)

    object Banana2 {
        implicit val decoder: JsonDecoder[Banana2] = DeriveJsonDecoder.gen[Banana2]

        implicit val encoder: JsonEncoder[Banana2] = DeriveJsonEncoder.gen[Banana2]
    }

    val bananaFromJson = """{"curvature":0.5}""".fromJson[Banana2]
    println(bananaFromJson) //Right(Banana2(0.5))

    def bananaTree(jsonString: String): Either[String, Banana2] =
        jsonString.fromJson[Banana2]

    
    // Say we extend our data model to include more data types
    sealed trait Fruit
    case class Banana(curvature: Double) extends Fruit
    case class Apple (poison: Boolean)   extends Fruit
    // we can generate the encoder and decoder for the entire sealed family
    object Fruit {
        implicit val decoder: JsonDecoder[Fruit] = 
            DeriveJsonDecoder.gen[Fruit]

        implicit val encoder: JsonEncoder[Fruit] = 
            DeriveJsonEncoder.gen[Fruit]
    }

    val banaNa = """{"Banana":{"curvature":0.5}}""".fromJson[Fruit]
    val apple = """{"Apple":{"poison":false}}""".fromJson[Fruit]
    println(banaNa)
    println(apple)

    val zioBanana = ZIO.attempt(banaNa)
    
    // val toJsonPretty = (List(Banana(0.6d), Banana(0.55d))).toJsonPretty
    // println(toJsonPretty)

    val apple2: Fruit = Apple(poison = false)
    val apple3 = apple2.toJson
    val apple4 = apple2.toJsonPretty

    println(apple3) //{"Apple":{"poison":false}}

    println(apple4)
    // {
    //   "Apple" : {
    //     "poison" : false
    //   }
    // }

    



}
