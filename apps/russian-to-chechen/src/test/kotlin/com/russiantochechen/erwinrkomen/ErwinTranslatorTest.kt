package com.russiantochechen.erwinrkomen

import com.russiantochechen.dictionary.erwin.ErwinTranslatorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ErwinTranslatorTest {

    @Autowired
    lateinit var translatorService: ErwinTranslatorService

    @Test
    fun findChechenWord() {
        val sentences = """
                        алфавитный.
                        глиняный шарик.
                        в детских играх.
                        ящур.
                        лошадь заболела ящуром.
                        ящурный.
                        козá.
                        клоп.
                        """.trimIndent().split(". ")
        println(sentences)
        val translatedSentences = sentences.map { sentence ->
            val clean = sentence.removeSuffix(".")
                .removeSuffix(",")
            println(clean)
            translatorService.translate(russianSentence = clean)
        }

        println(translatedSentences)
    }
}