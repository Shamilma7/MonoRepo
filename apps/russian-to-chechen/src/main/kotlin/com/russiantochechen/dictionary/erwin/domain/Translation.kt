package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Translation(
    val forms: List<Form>
) {
    companion object {
        fun from(translation: Element) = Translation(
            forms = translation.select("form").map { Form.from(it) }
        )
    }
}