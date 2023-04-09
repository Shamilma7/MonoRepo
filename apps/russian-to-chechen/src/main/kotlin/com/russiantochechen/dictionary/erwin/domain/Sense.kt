package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Sense(
    val id: String,
    val grammaticalInfo: String,
    val definitions: List<Definition>,
    val examples: List<Example>,
    val notes: List<Note>,

    ) {
    companion object {
        fun from(sense: Element) = Sense(
            id = sense.attr("id"),
            grammaticalInfo = sense.selectFirst("grammatical-info")?.attr("value") ?: "",
            definitions = sense.select("definition").map { definition -> Definition.from(definition) },
            notes = sense.select("note").map { note -> Note.from(note) },
            examples = sense.select("example").map { example -> Example.from(example) }
            )
    }
}