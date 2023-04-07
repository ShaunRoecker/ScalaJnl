package bench

import org.openjdk.jmh.annotations._
import kata.Kata.{howFastView, howFastNoView}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
class VectorAppendVsListPreppendAndReverse {
  val size = 1_000_000
  val input = 1 to size

  @Benchmark def vectorAppend: Vector[Int] = 
    input.foldLeft(Vector.empty[Int])({ case (acc, next) => acc.appended(next)})

  @Benchmark def listPrependAndReverse: List[Int] = 
    input.foldLeft(List.empty[Int])({ case (acc, next) => acc.prepended(next)}).reverse
}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
class ViewVsNoView {
    val xs = (0 to 1000).toList
    
    @Benchmark def view: List[Int] =
        howFastView(xs)

    @Benchmark def noView: List[Int] =
        howFastNoView(xs)
}


// to run:
    // sbt "jmh:run -i 10 -wi 10 -f 2 -t 1 bench.ClassToBenchmark"