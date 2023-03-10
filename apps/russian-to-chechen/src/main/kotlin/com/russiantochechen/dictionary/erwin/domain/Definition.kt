package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Definition(
    val forms: List<Form>
) {
    companion object {
        fun from(definition: Element?) = Definition(
            forms = definition!!.select("form").map { Form.from(it) }
        )
    }
}