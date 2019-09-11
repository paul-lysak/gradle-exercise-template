package gradle.exercise

import io.kotlintest.specs.StringSpec
import io.kotlintest.shouldBe

class FundamentalsSpec: StringSpec() {
    init {
        "ListCalculations must calculate squares" {
            ListCalculations.squares(listOf(2, 3)) shouldBe listOf(4, 9)
        }
    }
}