package com.russiantochechen.dictionary.erwin

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import org.springframework.stereotype.Service

@Service
class ErwinTranslatorService(
    private val dictionary: Dictionary = Dictionary()
) {
    fun translate(text: String): String {
        val sentences = text.split("(?<=[.!?])\\s+".toRegex())

        val translatedSentences = mutableListOf<List<String>>()

        for (sentence in sentences) {
            val words = sentence.split("\\s+".toRegex())

        }

        return translatedSentences.joinToString(" ") { words ->
            words.joinToString(" ")
        }
    }


    fun translateClean(text: String): String {
        val sentences = text.split("(?<=[.!?])\\s+".toRegex())


        val translatedSentences = sentences.map { sentence ->
            sentence.split("\\s+".toRegex()).mapIndexedNotNull { i, word ->
                var translation: String? = null
                var j = i + 1
                while (j <= sentence.split("\\s+".toRegex()).size - 1 && translation == null) {
                    val nextWords = (i..j).map { sentence.split("\\s+".toRegex())[it] }.joinToString(" ")
                    translation = dictionary.toChechen(nextWords)
                    j++
                }
                translation
            }
        }


        return translatedSentences.joinToString(" ") { words ->
            words.joinToString(" ")
        }
    }

    fun translateEvenCleaner(text: String): String {
        val sentences = text.split("(?<=[.!?])\\s+".toRegex())
        val translatedSentences = sentences.map { sentence ->
            val words = sentence.split("\\s+".toRegex())
            words.flatMap { word ->
                val cleanWord = getCleaned(word)
                generateSequence(cleanWord) { nextWord ->
                    if (nextWord == getCleaned(words.last())) null
                    else "$nextWord ${getCleaned(words[words.indexOf(nextWord) + 1])}"
                }.mapNotNull { phrase ->
                    val punctuation = getPunctuation(word)
                    dictionary.toChechen(phrase)?.let { "$it$punctuation" }
                }.toList()
            }
        }


        return translatedSentences.joinToString(" ") { words ->
            words.joinToString(" ")
        }
    }

    fun translateFinal(text: String): String {
        val sentences = text.split("(?<=[.!?])\\s+".toRegex())
        val translatedSentences = sentences.map { sentence ->
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

                translatedWords.add(translation)
                i++
            }

            return@map translatedWords.joinToString(" ")
        }

        return translatedSentences.joinToString("\n")
    }

    fun getCleaned(word: String) = word.replace(Regex("[.,?]+"), "")

    fun getPunctuation(word: String) = word.replace(Regex("[\\w\\d]+"), "")

    /*
    * // If exact match not found, try to find longer phrases
        val words = phrase.split(" ")
        for (i in words.indices) {
            val phrase = words.subList(i, words.size).joinToString(" ")
            val match = findEntriesWith(definitionText = phrase).firstOrNull()?.lexicalUnit
            if (match != null) {
                return match
            }
        }*/

    private fun clean(word: String) = word.removeSuffix(".")
        .removeSuffix(",")


    private fun formatChechenWord(russianWord: String, chechenWord: String): String {
        var result = chechenWord
        if (russianWord.contains(".")) {
            result += ".\n"
        }
        if (russianWord.contains(",")) {
            result += ","
        }
        return result
    }
}