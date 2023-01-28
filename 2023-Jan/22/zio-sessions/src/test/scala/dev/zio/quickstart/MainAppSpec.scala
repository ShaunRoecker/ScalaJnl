import zio._
import zio.test._
import zio.test.Assertion._
import zio.test.TestAspect._


object ExampleSpec extends DefaultRunnableSpec {
    def spec = suite("ExampleSpec")( 
        test("addition works") {
            assert(1 + 1)(equalTo(2))
        }
    )
}

