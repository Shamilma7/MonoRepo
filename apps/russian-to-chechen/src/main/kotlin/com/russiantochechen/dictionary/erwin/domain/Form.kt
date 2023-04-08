package com.russiantochechen.dictionary.erwin.domain

import com.russiantochechen.extensions.textElseNull
import org.jsoup.nodes.Element

data class Form(
    val lang: String, val text: String?
) {
    companion object {
        fun from(form: Element) = Form(
            lang = form.attr("lang"),
            text = form.selectFirst("text")?.textElseNull()
        )
    }
}