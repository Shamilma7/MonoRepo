package com.russiantochechen.checker


class WordTypeFinder {
    companion object {
        fun getNomSingularFormForNoun(word: String) = "${word.substring(0, word.lastIndex)}а"

    }
}