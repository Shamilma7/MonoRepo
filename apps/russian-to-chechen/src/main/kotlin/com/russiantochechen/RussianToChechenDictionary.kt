package com.russiantochechen

import org.springframework.stereotype.Component
import java.io.*
import java.lang.Exception
import java.util.NoSuchElementException

/**
 * The dictionary this class follows has the following rules:
 * 1. The first bold word is the chechen word, the second word is russian word.
 * 2. The first word and words followed by comma or in a [] is different ways of saying the chechen word and [] is different pronouns (past tense, present tense, future tense)
 * 3. The russian translated word(s) is generally the word that ends with a dot or separated with dots, and comes after 2.
 * 4. After a semicolon comes an example with the use of the word (first bold chechen, then russian ending with dot.
 * 5. Sometimes there are one- or twoletter words й; мн. см. in a line. (ignore them for now...)
 *
 * Take into account that one long line that belongs together can be separeted into two lines for example:
 * This is a long line
 *              seperated like this.
 *
 */
@Component
class RussianToChechenDictionary : Dictionary {
    private final val mappedDictionary: HashMap<String, String> = hashMapOf()

    init {
        val file = File("src/main/resources/chechenrussian.txt")
        val reader = BufferedReader(FileReader(file, Charsets.UTF_8))
        var line: String? = reader.readLine()
        // todo support multi-lines
        while (line != null) {
            line = reader.readLine()
            if (line == null) {
                break
            }
            val words = line.trim().split(" ")
            val chechenWord = words.getOrNull(0) ?: continue
            if (words.size < 2) {
                continue
            }
            val sentenceChecker = SentenceChecker(sentence = line)
            var russianWord = getLastWordInSentenceWithSuffixDot(words) ?: continue
            russianWord = removeSurroundingDirt(russianWord)

            // "Allow multiple keys"
            if (!mappedDictionary.containsKey(russianWord)) {
                mappedDictionary[russianWord] = chechenWord
            }
        }
        reader.close()
    }

    private fun removeSurroundingDirt(word: String) = word
        .removePrefix(".")
        .removeSuffix(".")
        .removePrefix(",")
        .removeSuffix(",")

    private fun getLastWordInSentenceWithSuffixDot(
        words: List<String>
    ): String? = try {
        val lastWordWithSuffixDot = getLastWordWithSuffixDot(words) ?: throw NoSuchElementException()
        if (lastWordWithSuffixDot == words.last()) {
            lastWordWithSuffixDot
        } else {
            throw NoSuchElementException()
        }
    } catch (e: NoSuchElementException) {
        null
    }

    private fun getLastWordWithSuffixDot(
        words: List<String>
    ): String? = try {
        words.last { it.isNotEmpty() && SentenceChecker.isSuffixDot(it) }
    } catch (e: NoSuchElementException) {
        null
    }

    fun toChechen(russianWord: String): String {
        return mappedDictionary[russianWord] ?: russianWord
    }

}