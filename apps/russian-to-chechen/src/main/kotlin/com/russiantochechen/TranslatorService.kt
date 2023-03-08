package com.russiantochechen

import com.russiantochechen.dictionary.erwin.ErwinTranslatorService
import com.russiantochechen.dictionary.p95.P95TranslatorService
import org.springframework.stereotype.Service

@Service
class TranslatorService(
    val psP95TranslatorService: P95TranslatorService,
    val erwinTranslatorService: ErwinTranslatorService,
) {

    fun translate(text: String): String {
        val sentences = erwinTranslatorService.splitTextIntoSentences(text)
        val translatedSentences = sentences.map { sentence ->
            val originalWords = sentence.split("\\s+".toRegex())
            val erwinTranslatedSentence = erwinTranslatorService.translateSentence(sentence)
            val erwinTranslatedWords = erwinTranslatedSentence.split("\\s+".toRegex())
            erwinTranslatedWords.map { w ->
                if(originalWords.contains(w)) {
                    return@map psP95TranslatorService.translateWord(w)
                }
                w
            }.joinToString(" ")
        }

        return translatedSentences.joinToString("\n")

    }
}