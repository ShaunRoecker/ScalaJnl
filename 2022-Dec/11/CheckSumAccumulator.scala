
package logic



def main(): Unit = 
    println("checkSumAccumulator")
    val person = Person("daniel", 40)
    println(person) //Person(Daniel,40)
    person.appendToName(" Smith")
    person.appendToName(", Jr.")
    println(person.name)



class CheckSumAccumulator:
            private var sum = 0

            def add(b: Byte): Unit =
                sum += b

            def checkSum(): Int = ~(sum & 0xFF) + 1


import scala.collection.mutable

object CheckSumAccumulator:
        private val cache = mutable.Map.empty[String, Int]
        def calculate(s: String): Int =
            if cache.contains(s) then
                cache(s)
            else
                val acc = new CheckSumAccumulator
                for c <- s do
                    acc.add((c >> 8).toByte)
                    acc.add(c.toByte)
                val cs = acc.checkSum()
                cache += (s -> cs)
                cs

case class Person(name: String, age: Int):
        def appendToName(suffix: String): Unit =
            val newName = s"$name$suffix"
            println(this.copy(name=newName))

object Person:
        // ensure non-empty name is capitalized
        def apply(name: String, age: Int): Person =
            val capitalizedName =
                if !name.isEmpty then 
                    val firstChar = name.charAt(0).toUpper
                    val restOfName = name.substring(1)
                    s"$firstChar$restOfName"
                else throw new IllegalArgumentException("Empty name")
            new Person(capitalizedName, age)



