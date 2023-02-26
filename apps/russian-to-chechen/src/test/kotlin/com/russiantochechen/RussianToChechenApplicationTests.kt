package com.russiantochechen

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class RussianToChechenApplicationTests {

    @Test
    fun contextLoads() {}

    @Nested
    inner class FromChechenToRussianTranslations {
        @Test
        fun `автобиография should translate to автобиографи`() {
            val actual = RussianToChechenTranslator().translate("автобиография")
            val expected = "автобиографи"
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `угнетатель автобиография should translate to адамдацорхо автобиографи`() {
            val actual = RussianToChechenTranslator().translate("угнетатель автобиография")
            val expected = "адамдацорхо автобиографи"
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `a poem about Shamil`() {
            val actual = RussianToChechenTranslator().translate("""
                Шамиль - наш герой, Он страстно любит свою речь. Он борется за существование чеченского, И не отступит ни на шаг.

                Он работает над переводчиком,
                От русского до чеченского языка.
                Он упорно трудится и не устает,
                Чтобы сохранить свою культуру и историю.

                Шамиль - ценитель своей национальности,
                Он не позволит ей забыться.
                Он держит чеченскую речь в сердце,
                И никогда не отступит.

                Шамиль - наш герой,
                Он борется за существование чеченского языка.
                Он неутомимый работник,
                И мы всегда будем за него.
            """.trimIndent())
            val expected = "жуьжап"
            println(expected)
            //assertThat(actual).isEqualTo(expected)
        }
    }


}
