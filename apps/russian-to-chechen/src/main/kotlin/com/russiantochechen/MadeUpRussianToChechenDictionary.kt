package com.russiantochechen

import org.springframework.stereotype.Component

@Component
class MadeUpRussianToChechenDictionary {
    private final val mappedDictionary: HashMap<String, String> = hashMapOf(
        "зовут" to "це ю"
    )

    fun toChechen(russianWord: String): String = mappedDictionary[russianWord] ?: russianWord

}