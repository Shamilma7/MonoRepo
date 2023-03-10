package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.Jsoup

class Dictionary {
    private val dictionary = mutableListOf<Entry>()

    init {
        initDictionary()
    }

    fun toChechen(phrase: String): String? =
        findEntriesWith(definitionText = phrase).firstOrNull()?.lexicalUnit
            ?: findEntriesWithNoteText(
                noteText = phrase
            ).firstOrNull()?.lexicalUnit


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
        val inputStream =
            ClassLoader.getSystemResourceAsStream("erwin/chechenrussian_dictionary.xml")
        val doc = Jsoup.parse(inputStream!!, "UTF-8", "")
        val entries = doc.select("entry")
        entries.forEach { entry -> dictionary.add(Entry.from(entry)) }
    }

}