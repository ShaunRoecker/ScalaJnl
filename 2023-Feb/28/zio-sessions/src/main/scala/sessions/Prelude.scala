package sessions

import zio._
import zio.Console._
import zio.prelude._
import zio.prelude.newtypes._
import zio.prelude.Newtype
import zio.Chunk


object Preludez extends scala.App {

    object AccountNumber extends Newtype[Int]
    type AccountNumber = AccountNumber.Type


    object SequenceNumber extends Newtype[Int] {
        val initial: SequenceNumber =
            SequenceNumber(0)
    }
    type SequenceNumber = SequenceNumber.Type


    val accountNumbers: Chunk[AccountNumber] =
        AccountNumber.wrapAll(Chunk(3, 4, 5))

    println(accountNumbers)

    val acctNumList: List[AccountNumber] =
        AccountNumber.wrapAll(List(6754, 342543, 32434, 324525))

    println(acctNumList)


    val initial: SequenceNumber =
        SequenceNumber.initial

    val zero: Int =
        SequenceNumber.unwrap(initial)

    val intFromSequenceNumber: Int = 
        SequenceNumber.unwrap(SequenceNumber(100))

    println(intFromSequenceNumber)

    val sequenceNumbers: Chunk[SequenceNumber] =
        SequenceNumber.wrapAll(Chunk(1, 2, 3))

    val ints: Chunk[Int] =
        SequenceNumber.unwrapAll(sequenceNumbers)

    object MyString extends Newtype[String] {
        implicit class MyStringExt(private val self: MyString) extends AnyVal {
            def concatDeez: MyString =
                MyString.wrap("Deez " + MyString.unwrap(self))
        }
    }
    type MyString= MyString.Type

    val myChunkyStrings: Chunk[MyString] =
        MyString.wrapAll(Chunk("this", "chunky", "string").map(_.toUpperCase))

    println(myChunkyStrings)

    val stringsFromChunkyMyStrings: Chunk[String] =
        MyString.unwrapAll(myChunkyStrings)

    println(stringsFromChunkyMyStrings)

    val myStringInst: MyString = MyString("thing")

    val myStringDeez: MyString = myStringInst.concatDeez
    println(myStringDeez)


    object AccountNumber2 extends Newtype[Int] {
        implicit val AccountNumber2Equal: Equal[AccountNumber2] =
            Equal.default
    }
    type AccountNumber2 = AccountNumber2.Type

    println(AccountNumber2(1) === AccountNumber2(1)) //true
    println(AccountNumber2(1) === AccountNumber2(2)) //false

    //Attempting to compare two unrelated types results in a compilation error.
    // that's good
    //AccountNumber2(1) === 1








    println("Prelude")
}