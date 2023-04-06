package com.russiantochechen.checker


class WordTypeGuesser {
    companion object {
        fun getNomSingularFormForNoun(word: String) = "${word.substring(0, word.lastIndex)}а"

        fun guessVariant(word: String): String {
            return ""
        }
    }
}