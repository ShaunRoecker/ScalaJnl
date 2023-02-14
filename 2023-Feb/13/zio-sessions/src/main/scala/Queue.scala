import zio._


object Queue1 extends ZIOAppDefault {
  val list1 = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
  val process1 = for {
    queue <- Queue.bounded[Int](100)
    _ <- queue.offerAll(list1)
    s <- queue.size
    _ <- ZIO.debug(s) //12
    _ <- queue.poll.debug //Some(1)
    qVal <- queue.takeAll
    _ <- queue.offerAll(qVal.map(_ * 2))
    _ <- ZIO.debug(queue.capacity) //100
    
    items = Range.inclusive(1, 10).toList
    _ <- queue.offerAll(items)
    smQ <- Queue.bounded[Int](1)
    _ <- smQ.offer(10)
    l <- (smQ.offer(20) *> Console.printLine("offered 20")).fork
    _ <- Console.printLine("next")
    _ <- smQ.poll.debug //Some(10)
    i <- queue.take
    _ <- smQ.poll.debug //Some(20)
    _ <- l.join
//     next
//     Some(10)
//     Some(20)
//     offered 20 
    queuec <- Queue.bounded[Int](100)
    _ <- queuec.offer(10)
    _ <- queuec.offer(20)
    chunk  <- queuec.takeUpTo(5)
    _ <- ZIO.debug(chunk) //Chunk(10,20)
  } yield ()

  def run = process1
}

object Queue2 extends ZIOAppDefault {
  def producer(queue: Queue[Int]): ZIO[Any, Nothing, Unit] =
    ZIO.foreachDiscard(0 to 4) { i =>
      queue.offer(i) *> ZIO.sleep(500.milliseconds)  
    }

  def consumer(id: Int)(queue: Queue[Int]): ZIO[Any, Nothing, Nothing] =
    queue.take.flatMap { i =>
      Console.printLine(s"Consumer $id got $i").!
    }.forever


  def run = for {
    queue <- Queue.bounded[Int](16)
    producerFiber <- producer(queue).fork
    consumer1Fiber <- consumer(1)(queue).fork
    consumer2Fiber <- consumer(2)(queue).fork
    _ <- producerFiber.join
    _ <- ZIO.sleep(1.second) 
    _ <- consumer1Fiber.interrupt
    cap <- ZIO.succeed(queue.capacity)
    _ <- Console.printLine(cap)

  } yield ()

}