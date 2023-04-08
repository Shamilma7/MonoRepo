package com.russiantochechen.extensions

import com.russiantochechen.domain.Paradigm
import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word

fun String.splitIntoWords(source: Source): List<Word> =
    this.split("\\s+".toRegex()).map { Word.from(plainWord = it, source = source) }

fun String.clean() = this.replace(Regex("[.,?]+"), "")

fun String.replaceMultipleDotsAndQuestionMarks(): String = this.replace(Regex("[.?,!]+")) { matchResult ->
    when (matchResult.value.first()) {
        '.' -> "."
        '?' -> "?"
        ',' -> ","
        else -> matchResult.value
    }
}

fun String.removeDoubleSpaces(): String = this.replace(Regex("\\s{2,}"), " ")

fun String.hasParadigmEndings(paradigm: Paradigm) = paradigm.hasEndings(this)

fun String.replaceLast(match: String, replacement: String): String {
    if (match.isBlank()) {
        return "${this}${replacement}"
    }
    val lastIndex = this.lastIndexOf(match, ignoreCase = true)
    if (lastIndex == -1) {
        return this
    }
    return this.substring(0, lastIndex) + replacement + this.substring(lastIndex + match.length)
}