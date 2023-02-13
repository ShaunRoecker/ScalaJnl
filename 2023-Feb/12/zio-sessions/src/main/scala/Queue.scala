import zio._


object Queue1 extends ZIOAppDefault {
    val res: UIO[Int] = for {
        queue <- Queue.bounded[Int](100)
        _ <- queue.offer(1)
        v1 <- queue.take
    } yield v1

    val differentQueues = for {
        backPressure <- Queue.bounded[Int](100)
        dropping <- Queue.dropping[Int](100)
        sliding <- Queue.sliding[Int](100)
        unbounded <- Queue.unbounded[Int]
    } yield ()

    val addingItemsToQueue = for {
        queue <- Queue.bounded[Int](100)
        _ <- queue.offer(42)
    } yield ()

    val removingItemsFromQueue = for {
        queue <- Queue.bounded[Int](100)
        _ <- queue.offer(42)
        item <- queue.take
        _ <- Console.printLine(item)
    } yield ()

    val res2: UIO[Unit] = for {
        queue <- Queue.bounded[Int](1)
        _ <- queue.offer(1)
        f <- queue.offer(1).fork // will be suspended because the queue is full
        _ <- queue.take
        _ <- f.join
        
    } yield ()

    val res3: UIO[Unit] = for {
        queue <- Queue.bounded[Int](100)
        items = Range.inclusive(1, 10).toList
        _ <- queue.offerAll(items)  
        
    } yield ()

    val zio2 = ZIO.attempt(42/0).fold(_ => 0, x => x)

    val zio3 = ZIO.attempt(42/0).foldZIO(_ => ZIO.succeed(0), x => ZIO.succeed(x))

    def run = for {
        _ <- removingItemsFromQueue
        _ <- zio2.debug
        _ <- zio3.debug
    } yield ()



}
