package com.russiantochechen.extensions

import org.jsoup.nodes.Element

fun Element.textElseNull(): String? {
    val text = this.text()
    if(text.isBlank()) return null
    return text
}