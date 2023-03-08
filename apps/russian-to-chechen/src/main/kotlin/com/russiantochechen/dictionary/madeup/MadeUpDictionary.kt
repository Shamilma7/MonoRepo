package com.russiantochechen.dictionary.madeup

import org.springframework.stereotype.Component

@Component
class MadeUpDictionary {
    private final val mappedDictionary: HashMap<String, String> = hashMapOf(
        "зовут" to "це ю"
    )

    fun toChechen(russianWord: String): String = mappedDictionary[russianWord] ?: russianWord

}