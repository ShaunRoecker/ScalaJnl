object SCALA_1_16_2023 extends App:
    
    enum Rank:
        case Bad, Good, Best

    import Rank.*

    case class Email(value: String)
    case class Subscriber(email: Email, recCount: Int)

    case class Coupon(code: String, rank: Rank):
        override def toString: String = s"Coupon Code: $code, Rank: $rank"

    case class Message(from: Email, to: Email, subject: String, body: String)

    object CouponService:
        def subCouponRank(subscriber: Subscriber): Rank =
            if (subscriber.recCount >= 10) Best else Good
    
        def selectCouponsByRank(coupons: List[Coupon], rank: Rank): List[Coupon] =
        coupons.filter(c => c.rank == rank)

        def emailForSubscriber(subscriber: Subscriber, couponList: List[Coupon]): Message =
            val goodCoupons = selectCouponsByRank(couponList, Good)
            val bestCoupons = selectCouponsByRank(couponList, Best)
            val rank = subCouponRank(subscriber)
            if (rank == Best)
                Message(
                    Email("newsletter@coupondog.com"),
                    Email(subscriber.email.value),
                    subject = s"Your best weekly couplons inside",
                    body = s"Here are the best coupons: ${bestCoupons.mkString("[","|","]")}"
                )
            else
                Message(
                    Email("newsletter@coupondog.com"),
                    Email(subscriber.email.value),
                    subject = "Your good weekly couplons inside",
                    body = s"Here are the good coupons: ${goodCoupons.mkString("[",",","]")}"
                )

        
        
    
    import CouponService.*

        

    
    val sub1 = Subscriber(Email("sub1@gmail.com"), 11)
    val sub2 = Subscriber(Email("sub2@gmail.com"), 5)

    val subList = List(sub1, sub2)

    println(subCouponRank(sub1))
    println(subCouponRank(sub2))

    val coupon1 = Coupon("10PERCENT", Bad)

    
    val couponList = List(
        Coupon("10PERCENT", Bad),
        Coupon("MAYDISCOUNT", Good),
        Coupon("PROMOTION45", Best),
        Coupon("IHEARYOU", Bad),
        Coupon("GETADEAL", Best)
    )

    val badCouponList = selectCouponsByRank(couponList, Bad)
    println(badCouponList) //List(Coupon(10PERCENT,Bad), Coupon(IHEARYOU,Bad))


    val message1 = Message(
        Email("sub1@gmail.com"), 
        Email("sub2@gmail.com"),
        "Subject Line",
        "Body of the message"
    )

    println(emailForSubscriber(sub1, couponList))
    println("\n")

    val emailList = for
        sub <- subList
    yield emailForSubscriber(sub, couponList)

    println(emailList)

    