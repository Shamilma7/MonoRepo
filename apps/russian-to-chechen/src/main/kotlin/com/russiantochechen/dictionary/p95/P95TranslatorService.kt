package com.russiantochechen.dictionary.p95

import com.russiantochechen.dictionary.madeup.MadeUpDictionary
import org.springframework.stereotype.Service

@Service
class P95TranslatorService(
    private val russianToChechenDictionary: RussianToChechenDictionary,
    private val madeUpDictionary: MadeUpDictionary
) {

    fun translate(sentence: String): String {
        val words = sentence.split(" ", "\n")
        return words.joinToString(separator = " ") { russianWord -> translateWord(russianWord) }
    }

    fun translateWord(russianWord: String): String {
        val russianWordWithoutSuffix = russianWord.removeSuffix(".")
            .removeSuffix(",")
        var chechenWord = madeUpDictionary.toChechen(russianWordWithoutSuffix)

        if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
            return chechenWord
        }

        chechenWord = russianToChechenDictionary
            .toChechenFromMappedDictionary(
                russianWordWithoutSuffix
            )
            .removeSuffix("*")
        if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
            return formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
        }

        chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
            "$russianWordWithoutSuffix."
        )
        if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
            return formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
        }

        chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
            "$russianWordWithoutSuffix;"
        )
        if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
            return formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
        }

        chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
            "$russianWordWithoutSuffix,"
        )
        if (isChechenWord(chechenWord, russianWordWithoutSuffix)) {
            return formatChechenWord(chechenWord = chechenWord, russianWord = russianWord)
        }

        return formatChechenWord(
            chechenWord = russianToChechenDictionary.getFirstChechenWordIfSentenceContains(
                russianWordWithoutSuffix
            ), russianWord = russianWord
        )
    }

    private fun formatChechenWord(russianWord: String, chechenWord: String): String {
        var result = chechenWord
        if (russianWord.contains(".")) {
            result += "."
        }
        if (russianWord.contains(",")) {
            result += ","
        }
        return result
    }

    private fun isChechenWord(chechenWord: String, russianWordWithoutSuffix: String) =
        !chechenWord.contains(russianWordWithoutSuffix)
}