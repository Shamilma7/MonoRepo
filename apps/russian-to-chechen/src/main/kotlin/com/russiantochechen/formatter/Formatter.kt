package com.russiantochechen.formatter

import com.russiantochechen.TranslationResult


class Formatter(val fromLanguage: String, val toLanguage: String) {

    fun formatFromToTranslation(from: String, to: TranslationResult) = """
        
        $fromLanguage
        $from
        $toLanguage
        ${to.text}
        erwin: ${to.erwin} | p95: ${to.p95} | original: ${to.original}
        
    """.trimIndent()

    fun formatAuthor(author: String, text: String) = text //"($author) $text"
}