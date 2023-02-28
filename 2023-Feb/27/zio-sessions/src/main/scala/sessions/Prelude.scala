package sessions

import zio._
import zio.Console._
import zio.prelude._
import zio.prelude.newtypes._
import zio.prelude.Newtype



  /**
    * Key Points:
        1. Data Type Validation
        2. Newtypes and Subtypes
        3. 
    */

// # Hierarchies of Prelude:
// Hierarchy #1 - ways of combining concrete values
    // Associative   => (a + b) + c == a + (b + c)   (A, A) => A
    // Identity      => a + identity == a
    // Commutative   => a + b + c == c + b + a

// Hierarchy #2 - ways of consuming concrete values
    // Equal
    // Hash
    // Order

// Hierarchy #3 - ways of combining parameterized types
    // Associative   => (a + b) + c == a + (b + c)   (F[A], F[A]) => F[A]
    // Identity      => a + identity == a
    // Commutative   => a + b + c == c + b + a

object Zym extends scala.App {
    println("Prelude")

    object Topic extends Subtype[String]
    type Topic = Topic.Type

    object Votes extends Subtype[Int]{
        implicit val VotesAssociative: Associative[Votes] =
            new Associative[Votes] {
                def combine(l: => Zym.Votes, r: => Zym.Votes): Zym.Votes = Votes(l + r)
            }
    }
    type Votes = Votes.Type

    final case class VoteState(map: Map[Topic, Votes]) { self =>
        // def combine(that: VoteState): VoteState =
        //     VoteState {
        //        self.map.foldLeft(that.map) { case (map, (topic, votes)) =>
        //             map.get(topic) match {
        //                 case Some(newVotes) => map + (topic -> (votes + newVotes))
        //                 case None => map + (topic -> votes)
        //             }
        //         }
        //     }
        
        def combine(that: VoteState):  VoteState =
            VoteState(Sum.wrapAll(self.map) <> that.map)
    }


    val leftVotes = VoteState(Map("zio-http" -> 4, "uzi-http" -> 2))
    val rightVotes = VoteState(Map("zio-http" -> 2, "zio-tls-htt" -> 3))

    def combineMaps(map: Map[String, Int], acc: Map[String, Int]): Map[String, Int] =
        map.foldLeft(acc) { case (acc, (key, value)) =>
            acc.get(key) match {
                case Some(newValue) => acc + (key -> (value + newValue))
                case None => acc + (key -> value)
            }    
        }

                                //map                          //acc
    println(combineMaps(Map("a"->1, "b"->2, "c"->3), Map("a"->1, "b"->1, "c"->1)))
    //Map(a -> 2, b -> 3, c -> 4)

    val combinedVotes = leftVotes combine rightVotes
    println(combinedVotes)



}

object Zym2 extends scala.App {
    import zio.prelude.Newtype
    import zio.Chunk

    println("Prelude New Types")

    object AccountNumber extends Newtype[Int]
    type AccountNumber = AccountNumber.Type

    object SequenceNumber extends Newtype[Int]
    type SequenceNumber = SequenceNumber.Type

    val accountNumber: AccountNumber = AccountNumber(1)

    

    val accountNumbers: Chunk[AccountNumber] =
        AccountNumber.wrapAll(Chunk(3, 4, 5))

    val acctNumList: List[AccountNumber] = AccountNumber.wrapAll(List(1, 2, 3))



}

