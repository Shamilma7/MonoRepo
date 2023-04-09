package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Note(
    val forms: List<Form>
) {
    companion object {
        fun from(note: Element): Note {
            var forms = note.select("form").map { Form.from(it) }
            forms = forms.flatMap { it.text?.split(", ")?.map { text -> it.copy(text = text) } ?: listOf(it) }

            return Note(
                forms = forms
            )
        }
    }
}