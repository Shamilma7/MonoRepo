package com.russiantochechen.erwinrkomen

import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DictionaryTest {
    data class LiftEntry(
        val id: String,
        val lexicalUnit: String?,
        val pronunciation: String?,
        val senses: List<LiftSense>,
        val variants: List<LiftVariant>,
        val source: String?,
        val status: String?,
    )

    data class LiftSense(
        val id: String,
        val grammaticalInfo: String,
        val definitions: List<Definition>,
    )

    data class LiftVariant(
        val trait: String,
        val form: String,
        val pronunciation: String?,
    )

    data class Definition(
        val lang: String,
        val text: String
    )

    @Test
    fun loadDictionary() {
        val inputStream = ClassLoader.getSystemResourceAsStream("chechenrussian_dictionary.xml")
        val doc = Jsoup.parse(inputStream!!, "UTF-8", "")
        val entries = doc.select("entry")
        val dictionary = mutableListOf<LiftEntry>()

        entries.forEach { entry ->
            val id = entry.attr("id")
            val lexicalUnit = entry.selectFirst("lexical-unit > form > text")?.text()
            val pronunciation = entry.selectFirst("pronunciation > form > text")?.text()
            val senses = entry.select("sense").map { sense ->
                val senseId = sense.attr("id")
                val grammaticalInfo = sense.selectFirst("grammatical-info")?.attr("value") ?: ""
                //val definitions = sense.select("definition > form > text").map { it.text() }
                val definitions = sense.select("definition > form").map { form ->
                    val lang = form?.attr("lang") ?: ""
                    val text = form.selectFirst("text")?.text() ?: ""
                    Definition(lang = lang, text = text)
                }
                LiftSense(
                    id = senseId,
                    grammaticalInfo = grammaticalInfo,
                    definitions = definitions,
                )
            }
            val variants = entry.select("variant").map { variant ->
                val trait = variant.selectFirst("trait")?.attr("value") ?: ""
                val form = variant.selectFirst("form > text")?.text() ?: ""
                val pronunciation = variant.selectFirst("pronunciation > form > text")?.text()

                LiftVariant(
                    trait = trait,
                    form = form,
                    pronunciation = pronunciation,
                )
            }
            val source = entry.selectFirst("note[type=source] > form > text")?.text()
            val status = entry.selectFirst("annotation[name=status] > form > text")?.text()

            val liftEntry = LiftEntry(
                id = id,
                lexicalUnit = lexicalUnit,
                pronunciation = pronunciation,
                senses = senses,
                variants = variants,
                source = source,
                status = status,
            )

            dictionary.add(liftEntry)
        }

        dictionary.forEach { entry ->
            println(entry)
        }
    }
}