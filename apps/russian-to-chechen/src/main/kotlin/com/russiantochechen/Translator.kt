package com.russiantochechen

import com.russiantochechen.dictionary.erwin.ErwinTranslator
import com.russiantochechen.dictionary.p95.P95Translator
import com.russiantochechen.domain.Author
import com.russiantochechen.domain.Word
import com.russiantochechen.extensions.splitIntoWords
import com.russiantochechen.format.Formatter
import com.russiantochechen.format.TextSplitter
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
        val sentences = TextSplitter.splitTextIntoSentences(text)

        val translatedSentences = sentences.map { sentence ->
            val originalWords = sentence.splitIntoWords(author = Author.ORIGINAL)
            val words: List<Word> = erwinTranslator.tryTranslatingSentence(sentence)
            words.map { word ->
                val result = translateWord(word = word, originalWords = originalWords)

                when (result.author) {
                    Author.ERWIN -> {
                        erwin++
                    }

                    Author.P95 -> {
                        p95++
                    }

                    else -> {
                        original++
                    }
                }

                "${result.value} "
            }
        }

        return formatter.formatFromToTranslation(
            from = text, to = TranslationCounterResult(
                text = translatedSentences.joinToString("\n"), erwin = erwin, p95 = p95, original = original
            )
        )
    }

    fun translateWord(word: Word, originalWords: List<Word>): Word {
        if (word.author == Author.ERWIN) {
            return word
        }

        val p95Word = psP95Translator.translateWord(word.value)

        if (!originalWords.any { it.value.contains(p95Word) }) {
            return Word(value = p95Word, author = Author.P95)
        }

        return word
    }
}

data class TranslationAuthor(
    val word: String, val author: String
)

data class TranslationCounterResult(
    val text: String, val erwin: Int, val p95: Int, val original: Int
)