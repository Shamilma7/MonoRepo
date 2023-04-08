package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Definition(
    val forms: List<Form>
) {
    companion object {
        fun from(definition: Element?): Definition {
            var forms = definition!!.select("form").map { Form.from(it) }
            forms = forms.flatMap { it.text?.split(", ")?.map { text -> it.copy(text = text) } ?: listOf(it) }

            return Definition(
                forms = forms
            )
        }
    }
}