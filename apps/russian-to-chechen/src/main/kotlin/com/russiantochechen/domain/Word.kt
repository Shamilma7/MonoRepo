package com.russiantochechen.domain

data class Word(var value: String, val author: Author)   {
    fun formatWithAuthor() = "($author) $value"
}