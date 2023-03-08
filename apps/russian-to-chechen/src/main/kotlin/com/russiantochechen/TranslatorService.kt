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
            val erwinTranslatedSentence = erwinTranslatorService.translateSentence(sentence)
            if (erwinTranslatedSentence == sentence) {
                return@map psP95TranslatorService.translate(sentence)
            }
            erwinTranslatedSentence
        }

        return translatedSentences.joinToString("\n")

    }
}