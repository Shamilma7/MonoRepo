package com.russiantochechen.dictionary.erwin

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import org.springframework.stereotype.Service

@Service
class ErwinTranslatorService(
    private val dictionary: Dictionary = Dictionary()
) {
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
}