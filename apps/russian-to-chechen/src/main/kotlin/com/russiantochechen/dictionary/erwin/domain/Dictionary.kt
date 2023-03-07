package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.Jsoup

class Dictionary {
    private val dictionary = mutableListOf<Entry>()

    init {
        initDictionary()
    }

    fun toChechen(phrase: String): String {
        // First try to find the exact word
        val exactMatch =
            findEntriesWith(definitionText = phrase).firstOrNull()?.lexicalUnit

        if (exactMatch != null) {
            return exactMatch
        }

        // If exact match not found, try to find longer phrases
        val words = phrase.split(" ")
        for (i in words.indices) {
            val phrase = words.subList(i, words.size).joinToString(" ")
            val match = findEntriesWith(definitionText = phrase).firstOrNull()?.lexicalUnit
            if (match != null) {
                return match
            }
        }

        // If no match found, return empty string
        return ""
    }

    // findEntriesWith(definitionText = russianWord).firstOrNull()?.lexicalUnit ?: ""


    fun findEntriesWith(definitionText: String): List<Entry> {
        return dictionary.filter { entry ->
            entry.senses.any { sense ->
                sense.definitions.any { d ->
                    d.text.equals(definitionText, ignoreCase = true)
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