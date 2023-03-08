package com.russiantochechen

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class TranslatorServiceTest {

    @Autowired
    lateinit var translatorService: TranslatorService

    @Test
    fun translateText() {
        val sentences = """
                        алфавитный.
                        глиняный шарик.
                        в детских играх.
                        алфавитный глиняный шарик в детских играх ящур.
                        ящур.
                        лошадь заболела ящуром.
                        ящурный.
                        козá.
                        клоп.
                        """.trimIndent()

        val message = translatorService.translate(text = sentences)
        println(message)
    }

    @Test
    fun `a description about Shamil`() {
        val actual = translatorService.translate(
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

        /**
         * 
         */

        /*
        *
        *

         */
        val expected = "жуьжап"
        println(expected)
        //assertThat(actual).isEqualTo(expected)
    }

}