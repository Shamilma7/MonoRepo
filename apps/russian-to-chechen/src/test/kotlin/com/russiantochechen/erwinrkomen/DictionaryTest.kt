package com.russiantochechen.erwinrkomen

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DictionaryTest {

    val dictionary: Dictionary = Dictionary()

    @Test
    fun `return null for multiple spaces`() {
        val empty = "    "

        val translation = dictionary.toChechenWord(phrase = empty)
        Assertions.assertThat(translation).isNull()
    }

    @Test
    fun `return null for single space`() {
        val empty = " "

        val translation = dictionary.toChechenWord(phrase = empty)
        Assertions.assertThat(translation).isNull()
    }

    @Test
    fun `return null for empty string`() {
        val empty = ""

        val translation = dictionary.toChechenWord(phrase = empty)
        Assertions.assertThat(translation).isNull()
    }
}