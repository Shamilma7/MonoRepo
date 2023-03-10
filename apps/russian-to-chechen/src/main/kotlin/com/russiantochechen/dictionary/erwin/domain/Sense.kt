package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Sense(
    val id: String,
    val grammaticalInfo: String,
    val definitions: List<Definition>,
    val notes: List<Note>,

    ) {
    companion object {
        fun from(sense: Element) = Sense(
            id = sense.attr("id"),
            grammaticalInfo = sense.selectFirst("grammatical-info")?.attr("value") ?: "",
            definitions = sense.select("definition").map { definition -> Definition.from(definition) },
            notes = sense.select("note > form").map { form -> Note.from(form) },
            )
    }
}