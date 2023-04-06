package com.russiantochechen.domain

data class Word(var value: String, val source: Source, val paradigm: Paradigm = Paradigm.ALL) {
    fun formatWithAuthor() = "($source) $value"
}