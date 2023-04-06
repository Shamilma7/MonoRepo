package com.russiantochechen.dictionary.erwin

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word
import com.russiantochechen.extensions.clean
import com.russiantochechen.extensions.replaceMultipleDotsAndQuestionMarks
import com.russiantochechen.extensions.splitIntoWords
import com.russiantochechen.format.TextSplitter
import org.springframework.stereotype.Service

@Service
class ErwinTranslator(
    private val dictionary: Dictionary = Dictionary()
) {
    fun tryTranslate(text: String): String {
        val sentences = TextSplitter.splitTextIntoSentences(text)
        val translatedSentences = sentences.map { sentence ->
            tryTranslatingSentence(sentence).joinToString(" ") { it.value }
        }

        return translatedSentences.joinToString("\n")
    }

    fun tryTranslatingSentence(sentence: String): List<Word> {
        val originalWords = sentence.splitIntoWords(source = Source.ORIGINAL)
        val translatedWords = mutableListOf<Word>()

        var i = 0
        var lastErwin: Word?
        while (i < originalWords.size) {
            val original: Word = originalWords[i]
            var result = original
            var phrase = getCleaned(original.value)
            var nextErwin: Word? = dictionary.toChechenWord(phrase)
            var lastOriginal = original.value
            lastErwin = nextErwin

            var j = i + 1
            while (j < originalWords.size) {
                val nextOriginal: Word = originalWords[j]
                phrase += " ${getCleaned(nextOriginal.value)}"
                nextErwin = dictionary.toChechenWord(phrase)
                if (nextErwin != null) {
                    i = j // skip next words already translated
                    lastErwin = nextErwin
                    lastOriginal += " ${nextOriginal.value}"
                }
                j++
            }

            if (nextErwin != null) {
                result = nextErwin
            } else if (lastErwin != null) {
                result = lastErwin
            }

            if (result.source == Source.ERWIN) {
                result.value = "${result.value}${getPunctuation(lastOriginal)}${
                    getNewline(
                        lastOriginal
                    )
                }".replaceMultipleDotsAndQuestionMarks()
            }

            translatedWords.add(result)
            i++
        }

        return translatedWords.toList()
    }

    fun getCleaned(word: String) = word.clean()

    fun getPunctuation(input: String): String? = Regex("\\p{Punct}").find(input = input)?.value ?: ""

    fun getNewline(input: String): String? = Regex("\n$").find(input = input)?.value ?: ""
}