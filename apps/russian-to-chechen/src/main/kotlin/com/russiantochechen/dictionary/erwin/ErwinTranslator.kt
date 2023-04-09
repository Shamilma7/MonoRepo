package com.russiantochechen.dictionary.erwin

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word
import com.russiantochechen.extensions.clean
import com.russiantochechen.extensions.removeDoubleSpaces
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
        val words = sentence.removeDoubleSpaces().splitIntoWords(source = Source.ORIGINAL)
        return tryTranslatingWords(words)
    }

    private fun tryTranslatingWords(words: List<Word>): List<Word> {
        val wordsCopy = words.toList()
        val translatedWords = mutableListOf<Word>()
        var i = 0
        var lastErwin: Word?
        while (i < wordsCopy.size) {
            val original: Word = wordsCopy[i]
            var phrase: Word = original.copy()
            var nextErwin: Word? = dictionary.toChechenWord(phrase)
            var lastOriginal = original.copy().value
            lastErwin = nextErwin

            var j = i + 1
            while (j < wordsCopy.size) {
                val nextOriginal: Word = wordsCopy[j]
                phrase += " ${nextOriginal.value.clean()}"
                nextErwin = dictionary.toChechenPhrase(phrase)
                if (nextErwin != null) {
                    i = j // skip next words already translated
                    lastErwin = nextErwin
                    lastOriginal += " ${nextOriginal.value}"
                }
                j++
            }

            var result = original.copy()
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
            if (result.isNotBlank()) {
                translatedWords.add(result)
            }
            i++
        }

        return translatedWords.toList()
    }


    fun getPunctuation(input: String): String? = Regex("\\p{Punct}").find(input = input)?.value ?: ""

    fun getNewline(input: String): String? = Regex("\n$").find(input = input)?.value ?: ""
}