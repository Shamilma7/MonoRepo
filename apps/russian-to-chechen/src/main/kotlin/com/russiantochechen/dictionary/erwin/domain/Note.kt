package com.russiantochechen.dictionary.erwin.domain

import com.russiantochechen.extensions.textElseNull
import org.jsoup.nodes.Element

data class Note(
    val lang: String,
    val text: String?
) {
    companion object {
        fun from(element: Element) = Note(
            lang = element.attr("lang"),
            text = element.selectFirst("text")?.textElseNull()
        )
    }
}