import zio._
import zio.Console._
import zio.Random._



object Queues extends ZIOAppDefault {

    val res1: UIO[Unit] = for {
        queue <- Queue.bounded[Int](100)
        _ <- queue.offer(1)
        v1 <- queue.take
        _ <- ZIO.debug(v1)
    } yield ()

    val res2: UIO[Iterable[Int]] = for {
        queue <- Queue.unbounded[Int]
        _ <- ZIO.foreach(List(1, 2, 3))(queue.offer) 
        value <- ZIO.collectAll(ZIO.replicate(3)(queue.take))
        _ <- ZIO.debug(queue)
    } yield value

    val res3: UIO[Unit] = for {
        queue <- Queue.unbounded[Int] 
        _ <- queue.take
             .tap(n => Console.printLine(s"Got $n!"))
             .forever
             .fork
        _ <- queue.offer(1)
        _ <- queue.offer(2)
    } yield ()

    import zio.Clock._ 
    import zio.Console._

    def work(id: String)(n: Int): URIO[Clock with Console, Unit] = 
        Console.printLine(s"fiber $id starting work $n").orDie *>
        ZIO.sleep(1.second) *>
        Console.printLine(s"fiber $id finished with work $n").orDie
    
    val unbounded: URIO[Clock with Console, Unit] = for {
        queue <- Queue.unbounded[Int]
        _     <- queue.take.flatMap(work("left")).forever.fork
        _     <- queue.take.flatMap(work("right")).forever.fork
        _     <- ZIO.foreachDiscard(1 to 10)(queue.offer)
    } yield ()

    // back-pressure is default type of queue
    val backPressure: UIO[Unit] = for {
        queue <- Queue.bounded[String](10)
        _ <- queue
                .offer("ping")
                .tap(_ => Console.printLine("ping"))
                .forever
                .fork
    } yield ()

    // sliding queue
    val sliding: UIO[(Int, Int)] = for {
        queue <- Queue.sliding[Int](2)
        _ <- ZIO.foreach(List(1, 2, 3))(queue.offer)
        a     <- queue.take
        b     <- queue.take
        _ <- ZIO.debug((a, b))
        _ <- ZIO.debug((queue.capacity, queue.size))
    } yield (a, b) //(2,3)

    // dropping queue
    val dropping: UIO[(Int, Int)] = for {
        queue <- Queue.dropping[Int](2)
        _ <- ZIO.foreach(List(1, 2, 3))(queue.offer)
        a     <- queue.take
        b     <- queue.take
        _ <- ZIO.debug((a, b))
    } yield (a, b) //(1, 2)



    def run = sliding
}