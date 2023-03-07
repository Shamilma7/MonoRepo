package com.russiantochechen.dictionary.p95

import com.russiantochechen.dictionary.madeup.Dictionary
import org.springframework.stereotype.Service

@Service
class P95TranslatorService(
    private val russianToChechenDictionary: RussianToChechenDictionary,
    private val dictionary: Dictionary
) {

    fun translate(sentence: String): String {
        val words = sentence.split(" ", "\n")
        return words.joinToString(separator = " ") { russianWord ->

            val russianWordWithoutSuffix = russianWord.removeSuffix(".")
                .removeSuffix(",")
            var chechenWord = dictionary.toChechen(russianWordWithoutSuffix)

            if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
                return@joinToString chechenWord
            }

            chechenWord = russianToChechenDictionary
                .toChechenFromMappedDictionary(
                    russianWordWithoutSuffix
                )
                .removeSuffix("*")
            if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
                return@joinToString formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
            }

            chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
                "$russianWordWithoutSuffix."
            )
            if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
                return@joinToString formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
            }

            chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
                "$russianWordWithoutSuffix;"
            )
            if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
                return@joinToString formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
            }

            chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
                "$russianWordWithoutSuffix,"
            )
            if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
                return@joinToString formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
            }

            formatChechenWord(
                chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
                    russianWordWithoutSuffix
                ), russianWord = russianWord
            )
        }
    }

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

    private fun isChechenWord(chechenWord: String, russianWordWithoutSuffix: String) =
        !chechenWord.contains(russianWordWithoutSuffix)
}