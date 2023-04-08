package com.russiantochechen.checker


class WordTypeGuesser {
    companion object {
        fun getNomSingularFormForNoun(word: String): String {
            if (word.isBlank()) return ""
            return "${word.substring(0, word.lastIndex)}а"
        }

        fun guessVariant(word: String): String {
            return ""
        }
    }
}