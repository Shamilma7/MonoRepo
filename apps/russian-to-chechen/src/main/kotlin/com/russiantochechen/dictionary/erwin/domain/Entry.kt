package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Entry(
    val id: String,
    val lexicalUnit: String?,
    val pronunciation: String?,
    val senses: List<Sense>,
    val variants: List<Variant>,
    val source: String?,
    val status: String?,
) {
    companion object {
        fun from(entry: Element) = Entry(
            id = entry.attr("id"),
            lexicalUnit = entry.selectFirst("lexical-unit > form > text")?.text(),
            pronunciation = entry.selectFirst("pronunciation > form > text")?.text(),
            senses = entry.select("sense").map { sense -> Sense.from(sense) },
            variants = entry.select("variant").map { variant -> Variant.from(variant) },
            source = entry.selectFirst("note[type=source] > form > text")?.text(),
            status = entry.selectFirst("annotation[name=status] > form > text")?.text(),
        )
    }
}
