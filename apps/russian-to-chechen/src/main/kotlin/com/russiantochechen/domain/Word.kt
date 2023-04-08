package com.russiantochechen.domain

import com.russiantochechen.checker.WordTypeGuesser
import com.russiantochechen.extensions.clean

data class Word private constructor(var value: String, val source: Source = Source.ORIGINAL, val paradigm: Paradigm = Paradigm.ALL) {
    fun isNotBlank() = this.value.isNotBlank()
    fun isBlank() = this.value.isBlank()

    operator fun plusAssign(value: String) {
        this.value += value
    }

    companion object {
        fun from(plainWord: String, source: Source = Source.ORIGINAL, paradigm: Paradigm? = null): Word =
            Word(
                value = plainWord,
                source = source,
                paradigm = paradigm ?: WordTypeGuesser.guessParadigm(plainWord.clean()).firstOrNull() ?: Paradigm.UNSPECIFIED
            )
    }
}