package com.russiantochechen

class RussianToChechenTranslator(
    private val russianToChechenDictionary: RussianToChechenDictionary = RussianToChechenDictionary()
) {

    fun translate(string: String): String {
        val words = string.split(" ")
        return words.joinToString(separator = " ") {
            russianToChechenDictionary
                .toChechen(
                    it.removeSuffix(".")
                        .removeSuffix(",")
                )
                .removeSuffix("*")
        }
    }
}