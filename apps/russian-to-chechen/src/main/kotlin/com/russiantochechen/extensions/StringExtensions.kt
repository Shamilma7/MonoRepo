package com.russiantochechen.extensions

fun String.splitIntoWords() = this.split("\\s+".toRegex())