package com.russiantochechen.dictionary.erwin.domain

import com.russiantochechen.checker.WordTypeGuesser
import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word
import com.russiantochechen.extensions.clean
import org.jsoup.Jsoup

class Dictionary {
    private val dictionary = mutableListOf<Entry>()

    init {
        initDictionary()
    }

    fun toChechenWord(phrase: Word): Word? {
        val cleanCopy = phrase.copy(value = phrase.value.clean())
        val chechen = translateToChechen(cleanCopy)

        return if (chechen !== null) cleanCopy.copy(
            value = chechen, source = Source.ERWIN
        ) else null
    }

    fun getEntry(id: String) = dictionary.find { it.id == id }

    private fun translateToChechen(word: Word): String? {
        val possibleNomWords = WordTypeGuesser.getPossibleNomSingularForms(word)
        val entry: Entry? =
            findFirstEntry(word.value) ?: possibleNomWords.firstNotNullOfOrNull { findFirstEntry(it.value) }

        return findVariantTranslation(entry, word)
            ?: entry?.lexicalUnit
            ?: findTranslationFromExampleDefinition(phrase = word.value)
    }

    private fun findVariantTranslation(
        entry: Entry?, cleanCopy: Word
    ) = entry?.variants?.firstOrNull { it.trait?.value == cleanCopy.paradigm.value && it.form.lang == "ce" }?.form?.text

    private fun findFirstEntry(phrase: String): Entry? =
        findEntriesWith(definitionText = phrase).firstOrNull()
            ?: findEntriesWithNoteText(noteText = phrase).firstOrNull()
    // todo example should be used ?: findEntriesWithExampleText(text = phrase).firstOrNull()


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
                sense.notes.any { n ->
                    n.forms.any { it.text.equals(noteText, ignoreCase = true) }
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
}