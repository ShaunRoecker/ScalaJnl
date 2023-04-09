package fplibrary

import fplibrary._

//import fplibrary.Description
// import fplibrary.Description.{create, brokenCreate}

object PointFreeProgram {
  

  private lazy val ignoreArgs: Array[String] => Unit = _ => 
    ()

  private lazy val hyphens: Any => String = _ => 
    "-" * 50

  private lazy val question: Any => String = _ => 
    "How much money would you like to deposit?"

  private lazy val displayKleisli: Any => Description[Unit] = input => Description.create {
    println(input)
  }

  private lazy val promptKleisli: Any => Description[String] = _ => Description.create("5")

  private lazy val convertStringToIntegerKleisli: String => Description[Int] = input =>
    Description.create {
      input.toInt
    }

  private lazy val ensureAmountIsPositiveKleisli: Int => Description[Int] = amount =>
    Description.create {
      if (amount < 1) 1
      else amount
    }

  private lazy val round: Int => Int = amount =>
    if(isDivisibleByHundred(amount)) amount
    else round(amount + 1)

  private lazy val isDivisibleByHundred: Int => Boolean = amount =>
    if (amount % 100 == 0) true
    else false

  private lazy val createMessage: Int => String = balance =>
    s"Congratulations, you now have $$${balance}."


    
  // lazy val run: Array[String] => Description[Unit] = 
  //   args =>
  //     Description.create(
  //                   display(
  //                     hyphens(
  //                       display(
  //                         createMessage(
  //                           round(
  //                             ensureAmountIsPositive(
  //                               convertStringToInteger(
  //                                 prompt(
  //                                   display(
  //                                     question(
  //                                       display(
  //                                         hyphens(
  //                                           args
  //                                         )
  //                                       )
  //                                     )
  //                                   )
  //                                 )
  //                               )
  //                             )
  //                           )
  //                         )
  //                       )
  //                     )
  //                   )
  //     )
 

  lazy val run: Array[String] => Description[Unit] =
    ignoreArgs -->
    hyphens -->
    displayKleisli >->
    question -->
    displayKleisli >->
    promptKleisli >->
    convertStringToIntegerKleisli >->
    ensureAmountIsPositiveKleisli >-> 
    round -->
    createMessage -->
    displayKleisli >->
    hyphens --> 
    displayKleisli 

  

}