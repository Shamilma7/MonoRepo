package com.russiantochechen.checker

/**
 * The dictionary this class follows has the following rules:
 * 1. The first bold word is the chechen word, the second word is russian word.
 * 2. The first word and words followed by comma or in a [] is different ways of saying the chechen word and [] is different pronouns (past tense, present tense, future tense)
 * 3. The russian translated word(s) is generally the word that ends with a dot or separated with dots, and comes after 2.
 * 4. After a semicolon comes an example with the use of the word (first bold chechen, then russian ending with dot.
 * 5. Sometimes there are one- or twoletter words й; мн. см. in a line. (ignore them for now...)
 *
 *  Words with suffix dot is the russian translated word. Usually the last russian dot word is the translation. The first dot words can be for adding explanations or other variants.
 *
 * Take into account that one long line that belongs together can be separeted into two lines for example:
 * This is a long line
 *              seperated like this.
 *
 */
class SentenceChecker(val sentence: String, val delimiter: String = " ") {

    private val words = sentence.trim().split(delimiter)

    fun isOnlyTwoWords(): Boolean = words.count() == 2 && words[0].isNotEmpty() && words[1].isNotEmpty()

    fun isFirstWordFollowedBySquareBrackets(): Boolean =
        words.isNotEmpty() && (words[1].first() == '[') && sentence.contains("]")

    fun isFirstWordFollowedByComma(): Boolean = words.isNotEmpty() && words[0].last() == ','

    companion object {
        fun isSuffixDot(word: String): Boolean = !word.contains(" ") && word.last() == '.'

        fun isSuffixSemicolon(word: String) = !word.contains(" ") && word.last() == ';'
    }
}