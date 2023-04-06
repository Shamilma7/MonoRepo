package com.russiantochechen.dictionary.erwin

import com.russiantochechen.TextSplitter
import com.russiantochechen.dictionary.erwin.domain.Dictionary
import org.springframework.stereotype.Service

@Service
class ErwinTranslator(
    private val dictionary: Dictionary = Dictionary()
) {
    fun translate(text: String): String {
        val sentences = TextSplitter.splitTextIntoSentences(text)
        val translatedSentences = sentences.map { sentence -> translateSentence(sentence) }

        return translatedSentences.joinToString("\n")
    }

    fun translateSentence(sentence: String): String {
        val words = sentence.split("\\s+".toRegex())
        val translatedWords = mutableListOf<String>()
        var i = 0
        var lastTranslation: String?
        while (i < words.size) {
            val word = words[i]
            var phrase = getCleaned(word)
            var translation = dictionary.toChechen(phrase)
            var lastOriginalTranslation = word
            lastTranslation = translation

            var j = i + 1
            while (j < words.size) {
                val nextWord = words[j]
                phrase += " ${getCleaned(nextWord)}"
                translation = dictionary.toChechen(phrase)

                if (translation != null) {
                    i = j // skip next words already translated
                    lastTranslation = translation
                    lastOriginalTranslation += " $nextWord"
                }
                j++
            }

            if (translation == null) {
                translation = lastTranslation
            }

            if (lastTranslation == null) {
                translation = word     // if not found, use original word
            }

            var result = "$translation${getPunctuation(lastOriginalTranslation)}${getNewline(lastOriginalTranslation)}"
            result = replaceMultipleDotsAndQuestionMarks(result)
            translatedWords.add(result)
            i++
        }

        return translatedWords.joinToString(" ")
    }

    fun getCleaned(word: String) = word.replace(Regex("[.,?]+"), "")

    fun getPunctuation(input: String): String? =
        Regex("\\p{Punct}").find(input = input)?.value ?: ""

    fun getNewline(input: String): String? =
        Regex("\n$").find(input = input)?.value ?: ""

    fun replaceMultipleDotsAndQuestionMarks(input: String): String {
        return input.replace(Regex("[.?,!]+"), { matchResult ->
            when (matchResult.value.first()) {
                '.' -> "."
                '?' -> "?"
                ',' -> ","
                else -> matchResult.value
            }
        })
    }
}