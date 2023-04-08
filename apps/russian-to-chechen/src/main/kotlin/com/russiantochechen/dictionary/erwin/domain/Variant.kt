package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Variant(
    val trait: Trait?,
    val form: Form,
    val pronunciation: String?,
) {
    companion object {
        fun from(element: Element) = Variant(
            trait = element.selectFirst("trait")?.let { Trait.from(it) },
            form = Form.from(element.selectFirst("form")!!),
            pronunciation = element.selectFirst("pronunciation > form > text")?.text(),
        )

    }
}