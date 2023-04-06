package com.russiantochechen.domain

data class Word(var value: String, val source: Source)   {
    fun formatWithAuthor() = "($source) $value"
}