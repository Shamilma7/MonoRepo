package com.russiantochechen.dictionary.erwin

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import org.springframework.stereotype.Service

@Service
class ErwinTranslator(
    private val dictionary: Dictionary = Dictionary()
) {
    fun translate(text: String): String {
        val sentences = splitTextIntoSentences(text)
        val translatedSentences = sentences.map { sentence -> translateSentence(sentence) }

        return translatedSentences.joinToString("\n")
    }

    fun splitTextIntoSentences(text: String) = text.split("(?<=[.!?])\\s+|\n".toRegex())

    fun translateSentence(sentence: String): String {
        val words = sentence.split("\\s+".toRegex())
        val translatedWords = mutableListOf<String>()
        var i = 0
        while (i < words.size) {
            val word = words[i]
            var phrase = getCleaned(word)
            var translation = dictionary.toChechen(phrase)

            if (translation == null) {
                var j = i + 1
                while (j < words.size) {
                    val nextWord = words[j]
                    phrase += " ${getCleaned(nextWord)}"
                    translation = dictionary.toChechen(phrase)

                    if (translation != null) {
                        i = j // skip next words already translated
                        break
                    }
                    j++
                }
            }

            if (translation == null) {
                translation = word // if not found, use original word
            }

            translatedWords.add("$translation${getPunctuation(word)}${getNewline(word)}")
            i++
        }

        return translatedWords.joinToString(" ")
    }

    fun getCleaned(word: String) = word.replace(Regex("[.,?]+"), "")

    fun getPunctuation(input: String): String? =
        Regex("\\p{Punct}").find(input = input)?.value ?: ""

    fun getNewline(input: String): String? =
        Regex("\n$").find(input = input)?.value ?: ""
}