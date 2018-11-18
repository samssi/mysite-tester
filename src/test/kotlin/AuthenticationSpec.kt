import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

internal class AuthenticationSpec : BehaviorSpec({
    given("foo") {
        `when` ("bar") {
            then("baz") {
                "foo" shouldBe "bar"
            }
        }
    }
})