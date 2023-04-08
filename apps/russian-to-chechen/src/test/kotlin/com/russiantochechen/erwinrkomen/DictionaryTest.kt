package com.russiantochechen.erwinrkomen

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import com.russiantochechen.domain.Paradigm
import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DictionaryTest {

    val dictionary: Dictionary = Dictionary()

    @Test
    fun `should return болх`() {
        val actual = Word.from(plainWord = "работа")
        val translation = dictionary.toChechenWord(phrase = actual)
        Assertions.assertThat(translation).isEqualTo(actual.copy(value = "болх", source = Source.ERWIN))
    }

    @Test
    fun `should return белхан of variant GEN`() {
        val actual = Word.from(plainWord = "работы")
        val translation = dictionary.toChechenWord(phrase = actual)
        Assertions.assertThat(translation)
            .isEqualTo(actual.copy(value = "белхан", source = Source.ERWIN, paradigm = Paradigm.GEN))
    }

    @Test
    fun `should return балхана of variant DAT`() {
        val actual = Word.from(plainWord = "работе")
        val translation = dictionary.toChechenWord(phrase = actual)
        Assertions.assertThat(translation)
            .isEqualTo(actual.copy(value = "балхана", source = Source.ERWIN, paradigm = Paradigm.DAT))
    }

    @Test
    fun `should return балхō of variant ERG`() {
        val actual = Word.from(plainWord = "работой")
        val translation = dictionary.toChechenWord(phrase = actual)
        Assertions.assertThat(translation)
            .isEqualTo(actual.copy(value = "балхō", source = Source.ERWIN, paradigm = Paradigm.ERG))
    }

    @Test
    fun `should return балхē of variant ALL`() {
        val actual = Word.from(plainWord = "работами")
        val translation = dictionary.toChechenWord(phrase = actual)
        Assertions.assertThat(translation)
            .isEqualTo(actual.copy(value = "балхē", source = Source.ERWIN, paradigm = Paradigm.ALL))
    }

    // todo
    @Test
    fun `should return белхаш of variant PLU`() {
        val actual = Word.from(plainWord = "работы")
        val translation = dictionary.toChechenWord(phrase = actual)
        // Assertions.assertThat(translation).isEqualTo(actual.copy(value = "белхаш", source = Source.ERWIN, paradigm = Paradigm.PLU))
    }

    @Test
    fun `return null for multiple spaces`() {
        val empty = Word.from(plainWord = "    ")
        val translation = dictionary.toChechenWord(phrase = empty)
        Assertions.assertThat(translation).isNull()
    }

    @Test
    fun `return null for single space`() {
        val empty = Word.from(plainWord = " ")

        val translation = dictionary.toChechenWord(phrase = empty)
        Assertions.assertThat(translation).isNull()
    }

    @Test
    fun `return null for empty string`() {
        val empty = Word.from(plainWord = "")

        val translation = dictionary.toChechenWord(phrase = empty)
        Assertions.assertThat(translation).isNull()
    }

    @Test
    fun `should convert one definition with multiple definitions to multiple definition (бēзам)`() {
        val entry = dictionary.getEntry("бēзам_1")
        Assertions.assertThat(entry!!.senses.first().definitions.first().forms.size).isEqualTo(6)
    }

    @Test
    fun `should translate люблю`() {
        val word = Word.from(plainWord = "люблю")
        Assertions.assertThat(dictionary.toChechenWord(word))
            .isEqualTo(word.copy(value = "бēзам", source = Source.ERWIN, paradigm = Paradigm.NOM))
    }
}