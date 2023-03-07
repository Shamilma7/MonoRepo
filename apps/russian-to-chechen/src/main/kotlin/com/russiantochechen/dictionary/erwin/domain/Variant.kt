package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Variant(
    val trait: String,
    val form: String,
    val pronunciation: String?,
) {
    companion object {
        fun from(element: Element) = Variant(
            trait = element.selectFirst("trait")?.attr("value") ?: "",
            form = element.selectFirst("form > text")?.text() ?: "",
            pronunciation = element.selectFirst("pronunciation > form > text")?.text(),
        )

    }
}
