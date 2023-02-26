package com.russiantochechen

import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.regex.Pattern

private val pattern = Pattern.compile("\\s{2,}")

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
        val lines: List<String> = reader.readLines()
        val listIterator = lines.listIterator()
        val sentences = mutableListOf<String>()
        var sentenceBuilder: String = ""
        var currentLine: String? = null
        while (listIterator.hasNext()) {
            if (canIterateToNextLine(currentLine)) {
                currentLine = listIterator.next()
            }

            if (isInvalidSentence(currentLine!!)) {
                currentLine = null
                continue
            }

            if (sentenceBuilder.isNotEmpty() && isNewSentence(currentLine)) {
                addCompletedSentence(sentences, sentenceBuilder)
                sentenceBuilder = ""
            }

            sentenceBuilder += currentLine

            if (isLastSentence(listIterator)) {
                addCompletedSentence(sentences, sentenceBuilder)
                break
            }

            val nextLine = listIterator.next()

            if (isNewSentence(nextLine)) {
                addCompletedSentence(sentences, sentenceBuilder)
                sentenceBuilder = ""
                currentLine = nextLine
                continue
            }

            if (isInvalidSentence(nextLine)) {
                addCompletedSentence(sentences, sentenceBuilder)
                sentenceBuilder = ""
                continue
            }

            sentenceBuilder += formatPartOfSentence(nextLine)
            currentLine = listIterator.next()
            continue
        }

        for (sentence in sentences) {
            val words = sentence.split(" ")
            val chechenWord = words.getOrNull(0) ?: continue
            if (words.size < 2) {
                continue
            }
            var russianWord = getLastWordInSentenceWithSuffixDot(words) ?: continue
            russianWord = removeSurroundingDirt(russianWord)

            // todo "Allow multiple keys"
            if (!mappedDictionary.containsKey(russianWord)) {
                mappedDictionary[russianWord] = chechenWord
            }
        }
        reader.close()
    }

    private fun isLastSentence(listIterator: ListIterator<String>) = !listIterator.hasNext()

    private fun canIterateToNextLine(currentLine: String?) = currentLine == null

    private fun formatPartOfSentence(nextLine: String) = " ${nextLine.trim()}"

    private fun addCompletedSentence(
        sentences: MutableList<String>, sentence: String
    ) {
        sentences += pattern.matcher(sentence.trim()).replaceAll(" ")
    }

    private fun isInvalidSentence(nextLine: String) = "^\\d".toRegex().matches(nextLine.trim())

    private fun isNewSentence(nextLine: String) = !nextLine.startsWith(" ")

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