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
    fun `return empty for empty input`() {
        val empty = "    "
        val translation = erwinTranslator.tryTranslate(text = empty)
        Assertions.assertThat(translation).isEqualToIgnoringCase("")
    }


    @Test
    fun `can translate simple russian definitions to chechen`() {
        // todo add example (form) support for лошадь заболела ящуром
        val sentences = """
                        алфавитный.
                        ящур
                        ящурный
                        козá.
                        """.trimIndent()

        val translation = erwinTranslator.tryTranslate(text = sentences)
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
        val translation = erwinTranslator.tryTranslate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("авгол")
    }

    @Test
    fun `can translate multiple russian definitions to chechen`() {
        val sentences = "алфавитный глиняный шарик ящур"
        val translation = erwinTranslator.tryTranslate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("āбатан авгол авсал")
    }


    @Test
    fun `can translate russian to chechen from example element`() {
        val sentences = "лошадь заболела ящуром."
        val translation = erwinTranslator.tryTranslate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("говрана авсал дина.")
    }

    @Test
    fun `use first translation when multiple translations exist`() {
        // todo add example (form) support for лошадь заболела ящуром
        val sentences = "клоп"
        val translation = erwinTranslator.tryTranslate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("гІундалгІи")
    }

    @Test
    fun `translate the dative form of the noun Работе`() {
        val sentences = "работе"
        val translation = erwinTranslator.tryTranslate(text = sentences)
        Assertions.assertThat(translation).isEqualToIgnoringCase("балхана")
    }



    @Test
    fun `a description about Shamil`() {
        /* val actual = erwinTranslator.translate(
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






         val expected = "жуьжап"
         println(expected)       */
        //assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun `A random conversation`() {
        /*val actual = erwinTranslator.translate(
            """
                Добрый день.
                Как вас зовут?      
                Меня зовут Шамиль.
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
        Assertions.assertThat(actual).isEqualTo(expected)      */
    }
}