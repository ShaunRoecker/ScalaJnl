import cats.effect.IO

object IOMonad extends App:
  val helloEffect = IO { println("Hello, World!") } // FP IO wrapper Type: IO[Unit]
  // val result = println(" Hello, World!") // No FP Wrapper
  val inputEffect = IO { scala.io.StdIn.readLine() } 

  helloEffect.unsafeRunSync()
  inputEffect.unsafeRunSync()


object IOApp extends App:
  val program: IO[Unit] = for {
      _       <- IO { println("Welcome to Scala! What's your name?") }
      name    <- IO { scala.io.StdIn.readLine() }
      nameUC  = name.toUpperCase
      _       <- IO { println(s"Well hello, $nameUC") }
    } yield ()
  
  program.unsafeRunSync()

  def putStrLn(s: String): IO[Unit] = IO { println(s) }

  // def readTextFileAsTry(filename: String): Try[List[String]] =
    //   Try {
    //     val lines = using(io.Source.fromFile(filename)) { source =>
    //       (for (line <- source.getLines) yield line).toList
    //     }
    //     lines
    //   }
    
    //   println(readTextFileAsTry("test.txt"))

    // val passwdFile = readTextFileAsTry("/etc/passwd-foo")
    // passwdFile match
    //   case Success(lines) => lines.foreach(println)
    //   case Failure(s)     => println(s"Failed, message is: ${s.getMessage}")


object GolfingOne extends App:

  case class GolfState(strokes: List[Int])

  def nextStroke(gs: GolfState, distanceOfNextHit: Int): GolfState =
    GolfState(distanceOfNextHit :: gs.strokes)

  // val state1 = GolfState(20)
  // val state2 = nextStroke(state1, 15)
  // val state3 = nextStroke(state2, 0)

  // println(state3)

  def push[A](xs: List[A], a: A): List[A] = a :: xs

  def pop[A](xs: List[A]): (A, List[A]) = (xs.head, xs.tail)

  


object Golfing3 extends App:
      case class State[S, A](run: S => (S, A)) {
        def flatMap[B](g: A => State[S, B]): State[S, B] = State { (s0: S) =>
            val (s1, a) = run(s0)
            g(a).run(s1)
        }

        def map[B](f: A => B): State[S, B] = flatMap(a => State.point(f(a)))
      }

      object State {
          def point[S, A](v: A): State[S, A] = State(run = s => (s, v))
      }

      case class GolfState(dist: Int)

      def swing(dist: Int): State[GolfState, Int] = State { (s: GolfState) => 
        val newAmount = s.dist + dist
        (GolfState(newAmount), newAmount)  
      }

      val stateWithNewDistance: State[GolfState, Int] = for {
          _         <- swing(20)
          _         <- swing(15)
          totalDist <- swing(0)
      } yield totalDist

      val beginningState = GolfState(0)

      val result: (GolfState, Int) = stateWithNewDistance.run(beginningState)
      println(result._1) //GolfState(35)
      println(result._2) //35

      




  
    
    
    
    

    


    

