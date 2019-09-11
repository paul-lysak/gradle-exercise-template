package gradle.exercise

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class TrivialSpec : StringSpec() {
    init {
        "Should be successful" {
            2 + 2 shouldBe 4
        }
    }

}
