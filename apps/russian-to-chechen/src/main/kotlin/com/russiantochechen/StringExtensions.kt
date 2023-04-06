package com.russiantochechen

fun String.splitIntoWords() = this.split("\\s+".toRegex())