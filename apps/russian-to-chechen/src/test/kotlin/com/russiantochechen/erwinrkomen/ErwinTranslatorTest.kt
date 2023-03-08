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
    fun translateText() {
        // todo add example (form) support for лошадь заболела ящуром
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

}