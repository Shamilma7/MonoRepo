package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Definition(
    val lang: String,
    val text: String
) {
    companion object {
        fun from(element: Element?) = Definition(
            lang = element?.attr("lang") ?: "",
            text = element?.selectFirst("text")?.text() ?: ""
        )
    }
}
