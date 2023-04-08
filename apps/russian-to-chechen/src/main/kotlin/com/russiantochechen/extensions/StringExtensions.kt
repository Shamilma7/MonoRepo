package com.russiantochechen.extensions

import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word

fun String.splitIntoWords(source: Source): List<Word> =
    this.split("\\s+".toRegex()).map { Word(value = it, source = source) }

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