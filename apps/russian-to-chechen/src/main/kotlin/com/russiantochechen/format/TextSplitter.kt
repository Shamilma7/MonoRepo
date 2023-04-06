package com.russiantochechen.format

class TextSplitter {
    companion object {
        fun splitTextIntoSentences(text: String) = text.split("(?<=[.!?])\\s+|\n".toRegex())
    }
}