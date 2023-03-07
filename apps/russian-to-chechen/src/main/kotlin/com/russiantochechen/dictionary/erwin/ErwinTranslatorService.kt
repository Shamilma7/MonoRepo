package com.russiantochechen.dictionary.erwin

import com.russiantochechen.dictionary.erwin.domain.Dictionary
import org.springframework.stereotype.Service

@Service
class ErwinTranslatorService(
    private val dictionary: Dictionary = Dictionary()
) {
    fun translate(russianSentence: String) = dictionary.toChechen(phrase = russianSentence)

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