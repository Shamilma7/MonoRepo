package com.russiantochechen

import com.russiantochechen.dictionary.erwin.ErwinTranslator
import com.russiantochechen.dictionary.p95.P95Translator
import com.russiantochechen.formatter.Formatter
import org.springframework.stereotype.Service

@Service
class Translator(
    val psP95Translator: P95Translator,
    val erwinTranslator: ErwinTranslator,
) {

    private val formatter = Formatter(fromLanguage = "russian", toLanguage = "chechen")


    fun translate(text: String): String {
        var erwin = 0
        var p95 = 0
        var original = 0
        val sentences = erwinTranslator.splitTextIntoSentences(text)
        val translatedSentences = sentences.map { sentence ->
            val originalWords = sentence.split("\\s+".toRegex())
            val erwinSentence = erwinTranslator.translateSentence(sentence)
            val erwinWords = erwinSentence.split("\\s+".toRegex())

            erwinWords.map { word ->
                if (!originalWords.contains(word)) {
                    erwin++
                    return@map formatter.formatAuthor(author = "Erwin", text = word)
                }
                val p95translation = psP95Translator.translateWord(word)
                if (!originalWords.contains(p95translation)) {
                    p95++
                    return@map formatter.formatAuthor(author = "P95", text = p95translation)
                }
                original++
                formatter.formatAuthor(author = "Original", text = p95translation)
            }.joinToString(" ")
        }



        return formatter.formatFromToTranslation(from = text, to = TranslationResult(
            text = translatedSentences.joinToString("\n"),
            erwin = erwin, p95 = p95, original = original
        ))
    }
}

data class TranslationResult(
    val text: String,
    val erwin: Int,
    val p95: Int,
    val original: Int
)