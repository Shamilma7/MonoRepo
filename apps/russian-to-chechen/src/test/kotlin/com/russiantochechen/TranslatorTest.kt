package com.russiantochechen

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class TranslatorTest {

    @Autowired
    lateinit var translator: Translator

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

        val message = translator.translate(text = sentences)
        println(message)
    }

    @Test
    fun `a description about Shamil`() {
        val actual = translator.translate(
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

    @Test
    fun `A random conversation`() {
        val actual = translator.translate(
            """
                Добрый день.
                Как вас зовут?      
                Я зовут Шамиль.
                Как поживаете? 
                Я хорошо.
                Сколько вам лет?  
                Мне двадцать восемь лет.
                О Боже. Вы старый!
                Эй, это некрасиво говорить.
                Прошу прощения.
                Извинения приняты, вы прощены.
                Что вы делали на работе сегодня?
                Я строил дом в городе.
                Что вам нравится делать?
                Мне нравится ездить на лошади в школу.
                Как у остальных в семье дела?
                У остальных в семье все хорошо.
                До свидания.
                Спокойной ночи.
            """.trimIndent()
        )
        val expected = "жуьжап"
        println(expected)
        //assertThat(actual).isEqualTo(expected)
    }

}