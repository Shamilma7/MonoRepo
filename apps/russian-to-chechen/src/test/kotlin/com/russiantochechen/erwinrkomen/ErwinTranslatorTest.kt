package com.russiantochechen.erwinrkomen

import com.russiantochechen.dictionary.erwin.ErwinTranslator
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ErwinTranslatorTest {

    @Autowired
    lateinit var erwinTranslator: ErwinTranslator

    @Test
    fun `can translate simple russian definitions to chechen`() {
        // todo add example (form) support for лошадь заболела ящуром
        val sentences = """
                        алфавитный.
                        ящур
                        ящурный
                        козá.
                        """.trimIndent()

        val translation = erwinTranslator.translate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase(
            """
            āбатан.
            авсал
            авсалан
            авст.
            """.trimIndent()
        )
    }

    @Test
    fun `can translate russian definition with multiple words to chechen`() {
        val sentences = "глиняный шарик"
        val translation = erwinTranslator.translate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("авгол")
    }

    @Test
    fun `can translate multiple russian definitions to chechen`() {
        val sentences = "алфавитный глиняный шарик ящур"
        val translation = erwinTranslator.translate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("āбатан авгол авсал")
    }


    @Test
    fun `can translate russian to chechen from example element`() {
        val sentences = "лошадь заболела ящуром."
        val translation = erwinTranslator.translate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("говрана авсал дина.")
    }

    @Test
    fun `use first translation when multiple translations exist`() {
        // todo add example (form) support for лошадь заболела ящуром
        val sentences = "клоп"
        val translation = erwinTranslator.translate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("гІундалгІи")
    }

    @Test
    fun `a description about Shamil`() {
        val actual = erwinTranslator.translate(
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