package com.russiantochechen.p95

import com.russiantochechen.dictionary.p95.P95TranslatorService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class P95ErwinTranslatorServiceTest {

    @Autowired
    lateinit var russianToChechenTranslator: P95TranslatorService

    @Nested
    inner class FromChechenToRussianTranslations {
        @Test
        fun `автобиография should translate to автобиографи`() {
            val actual = russianToChechenTranslator.translate("автобиография")
            val expected = "автобиографи"
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `угнетатель автобиография should translate to адамдацорхо автобиографи`() {
            val actual = russianToChechenTranslator.translate("угнетатель автобиография")
            val expected = "адамдацорхо автобиографи"
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `a poem about Shamil`() {
            val actual = russianToChechenTranslator.translate(
                """
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
            """.trimIndent()
            )
            val expected = "жуьжап"
            println(expected)
            //assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `a description about Shamil`() {
            val actual = russianToChechenTranslator.translate(
                """
                Привет.
                Меня зовут Шамиль.
                Мне двадцать шесть лет.
                Я люблю играть в футбол, и я люблю играть в теннис.
                Но больше всего на данный момент мне нравится играть в теннис.
                Мне больше всего нравится теннис, потому что это весело.
                Я работаю консультантом в консалтинговой фирме.
                В настоящее время я работаю в Норвежском управлении по безопасности пищевых продуктов и работаю старшим разработчиком в группе аквакультуры. Наша миссия — разработать решение, которое облегчит повседневную жизнь инспекторов. Решение должно привести к улучшению здоровья и благополучия рыб в Норвегии.
            """.trimIndent()
            )

            /*
            *
            *

             */
            val expected = "жуьжап"
            println(expected)
            //assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `a poem about a lady `() {
            val actual = russianToChechenTranslator.translate(
                """
                    Я собираюсь сломать тебя. 
                    Уходи. 
                    Эй. 
                    Остановите этих ребят. 
                    Это неловко. 
                    Мы этого не делаем. 
                    Мы не такие. 
                    Мы любим заниматься борьбой.
            """.trimIndent()
            )


            val expected = "жуьжап"
            println(expected)
        }
    }


}