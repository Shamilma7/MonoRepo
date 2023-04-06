package com.russiantochechen.dictionary.erwin.domain

import com.russiantochechen.checker.WordTypeFinder
import com.russiantochechen.domain.Author
import com.russiantochechen.domain.Word
import org.jsoup.Jsoup

class Dictionary {
    private val dictionary = mutableListOf<Entry>()

    init {
        initDictionary()
    }

    fun toChechenWord(phrase: String): Word? {
        val chechen = toChechen(phrase)
        return if (chechen !== null) Word(value = chechen, author = Author.ERWIN) else null
    }


    fun toChechen(phrase: String): String? = findTranslation(phrase)
        ?: findTranslation(WordTypeFinder.getNomSingularFormForNoun(word = phrase))


    private fun findTranslation(phrase: String): String? =
        (findEntriesWith(definitionText = phrase).firstOrNull()?.lexicalUnit ?: findEntriesWithNoteText(
            noteText = phrase
        ).firstOrNull()?.lexicalUnit ?: findTranslationFromExampleDefinition(phrase))

    private fun findTranslationInPresentTense(phrase: String) =
        findTranslation(replaceLastCharWith(input = phrase, replacement = "?")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "???")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "??")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "??")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "???")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "??")
        )

    private fun findTranslationInPastTense(phrase: String) =
        findTranslation(replaceLastCharWith(input = phrase, replacement = "?")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "??")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "a")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "a")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "a")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "a")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "a"))

    private fun findTranslationInFutureTense(phrase: String) =
        findTranslation(replaceLastCharWith(input = phrase, replacement = "?")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "???")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "??")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "??")
        ) ?: findTranslation(replaceLastCharWith(input = phrase, replacement = "???")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "??")
        )


    private fun findTranslationInInfinitiveFormOfTheVerb(phrase: String) =
        findTranslation(replaceLastCharWith(input = phrase, replacement = "??")) ?: findTranslation(
            replaceLastCharWith(input = phrase, replacement = "??")
        ) ?: findTranslation(phrase = phrase + "??") ?: findTranslation(phrase = phrase + "??")

    private fun findTranslationFromExampleDefinition(phrase: String) = findEntriesWithExampleText(
        text = phrase
    ).firstOrNull()?.senses?.firstOrNull()?.examples?.firstOrNull()?.form?.text


    // findEntriesWith(definitionText = russianWord).firstOrNull()?.lexicalUnit ?: ""


    private fun findEntriesWith(definitionText: String): List<Entry> {
        return dictionary.filter { entry ->
            entry.senses.any { sense ->
                sense.definitions.any { d ->
                    d.forms.any { it.text.equals(definitionText, ignoreCase = true) }
                }
            }
        }
    }

    private fun findEntriesWithNoteText(noteText: String): List<Entry> {
        return dictionary.filter { entry ->
            entry.senses.any { sense ->
                sense.notes.any { d ->
                    d.text.equals(noteText, ignoreCase = true)
                }
            }
        }
    }

    private fun findEntriesWithExampleText(text: String): List<Entry> {
        return dictionary.filter { entry ->
            entry.senses.any { sense ->
                sense.examples.any { example ->
                    example.translations.any { it.forms.any { form -> form.text.equals(text, ignoreCase = true) } }
                }
            }
        }
    }

    private fun findEntriesWith(definition: Definition): List<Entry> {
        return dictionary.filter { entry ->
            entry.senses.any { sense ->
                sense.definitions.any { d ->
                    d == definition
                }
            }
        }
    }


    private fun initDictionary() {
        val inputStream = ClassLoader.getSystemResourceAsStream("erwin/chechenrussian_dictionary.xml")
        val doc = Jsoup.parse(inputStream!!, "UTF-8", "")
        val entries = doc.select("entry")
        entries.forEach { entry -> dictionary.add(Entry.from(entry)) }
    }

    fun replaceLastCharWith(input: String, replacement: String): String = input.dropLast(1) + replacement


}