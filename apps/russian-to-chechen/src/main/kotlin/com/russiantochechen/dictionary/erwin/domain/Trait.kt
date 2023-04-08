package com.russiantochechen.dictionary.erwin.domain

import org.jsoup.nodes.Element

data class Trait(
    val name: String, val value: String?
) {
    companion object {
        fun from(trait: Element) = Trait(
            name = trait.attr("name"),
            value = trait.attr("value")
        )
    }
}