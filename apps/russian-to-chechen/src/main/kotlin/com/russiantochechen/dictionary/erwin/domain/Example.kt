package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Example(
    val form: Form,
    val translations: List<Translation>,
) {
    companion object {
        fun from(example: Element) = Example(
            form = Form.from(example.selectFirst("form")!!),
            translations = example.select("translation").map { Translation.from(it) }
        )
    }
}