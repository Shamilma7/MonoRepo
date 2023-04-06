package com.russiantochechen.extensions

import com.russiantochechen.domain.Author
import com.russiantochechen.domain.Word

fun String.splitIntoWords(author: Author): List<Word> =
    this.split("\\s+".toRegex()).map { Word(value = it, author = author) }

fun String.clean() = this.replace(Regex("[.,?]+"), "")

fun String.replaceMultipleDotsAndQuestionMarks(): String = this.replace(Regex("[.?,!]+")) { matchResult ->
    when (matchResult.value.first()) {
        '.' -> "."
        '?' -> "?"
        ',' -> ","
        else -> matchResult.value
    }
}