
//> using scala "3.2.1"

import fplibrary.*
import scala.reflect.ClassTag
import scala.reflect.runtime.universe.*

sealed trait Either[+E, +A]
case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]

object Program:
  @main 
  def main() = 
    println("Hello, world")

    val io1 = IO.delay{ println("thunk delayed execution") }
    io1.unsafeRun()
    println("string".isInstanceOf[String]) // true

    def mkArray[T: ClassTag](elem: T*) = Array[T](elem: _*)

    println(mkArray(42, 13))

    class Animal
    val myMap: Map[String, Any] =
      Map("Number" -> 1, "String" -> "Hello World", "Animal" -> new Animal)

    // val number: Int = myMap("Number") <- doesn't compile (can't assign Any to Int)

    // but we can cast the value into Int like this...
    val number: Int = myMap("Number").asInstanceOf[Int]
    println(number) // 1

    // but you cant cast a type that isn't the type, if that makes sense? look...
    // val string: String = myMap("Number").asInstanceOf[String]
    // Exception in thread "main" java.lang.ClassCastException: 
        //  java.lang.Integer cannot be cast to java.lang.String

    // the same goes for using Option.get, we cant assign an Option[Any] to Option[Int]
    // val optInt: Option[Int] = myMap.get("Number")

    // instead of casting we could...

    def getValueFromMapInt(key: String, map1: Map[String, Any]): Option[Int] =
      map1.get(key) match
        case Some(value: Int) => Some(value)
        case _ => None

    val m1: Option[Int] = getValueFromMapInt("Number", myMap)
    println(m1) // Some(1)

    val m2: Option[Int] = getValueFromMapInt("String", myMap)
    println(m2) // None

    // But this is not scalable because we have to create a "getValueFromMapX" for
      // every type in the map to access that X type

    // we can turn getValueFromMapX into a polymorphic function to make it more
    // versatile

    def getTValueFromMap[T](key: String, map1: Map[String, Any]): Option[T] =
      map1.get(key) match
        case Some(value: T) => Some(value)
        case _ => None

    // The problem is this still works
    val m3: Option[Int] = getTValueFromMap[Int]("String", myMap)
    println(m3) //Some(Hello World)

    // This is the case because it is all happening at runtime and the compiler
    // doesn't know the type at that stage

    // This "Int" m3 is still a String, the compiler just cant tell, so when 
    // we later go to do some Int operation on m3...

    // val kaboom: Option[Int] = m3.map(x => x + 5)
    // println(kaboom)

    // Exception in thread "main" java.lang.ClassCastException: <-- get ClassCaseException
      // java.lang.String cannot be cast to java.lang.Integer

    // The problem is that the runtime looked to see that we had Some(value: T), but
    // it does not compare that value to the value we used as the type parameter

    // This is called "Type Erasure" (on the JVM) - The runtime does not see type information
    // Types are only present at compile time

    // So we need to make sure the type T we pass is also available at runtime

    // Thus, classTag

    // done with implicit- done in two possible ways, with the second being more elegant
    def getTValueFromMap2[T](key: String, map1: Map[String, Any])(implicit t: ClassTag[T]): Option[T] =
      map1.get(key) match
        case Some(value: T) => Some(value)
        case _ => None


    def getTValueFromMap3[T: ClassTag](key: String, map1: Map[String, Any]): Option[T] =
      map1.get(key) match
        case Some(value: T) => Some(value)
        case _ => None

    // now we can do what we did before without kaboom, and we have a scalable method

    // should get None here now...
    val m5: Option[Int] = getTValueFromMap3[Int]("String", myMap)
    println(m5) // None

    val m5Transform = m5.map((x) => x + 5)
    println(m5Transform) // None

    // Conclusion:
      // ClassTag helps the runtime get information about the type parameters


    // article I worked through for this:
      // https://dzone.com/articles/scala-classtag-a-simple-use-case


     




      

    


  