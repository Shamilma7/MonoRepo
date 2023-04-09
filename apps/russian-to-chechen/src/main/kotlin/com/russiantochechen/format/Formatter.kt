package com.russiantochechen.format

import com.russiantochechen.TranslationCounterResult


class Formatter(val fromLanguage: String, val toLanguage: String) {

    fun formatFromToTranslation(from: String, to: TranslationCounterResult) = """
        
        $fromLanguage
        $from
        $toLanguage
        ${to.text}
        erwin: ${to.erwin} | p95: ${to.p95} | original: ${to.original}
    """.trimIndent()

    fun formatToTranslation(to: TranslationCounterResult) = "${to.text}\nerwin: ${to.erwin} | p95: ${to.p95} | original: ${to.original}"

}