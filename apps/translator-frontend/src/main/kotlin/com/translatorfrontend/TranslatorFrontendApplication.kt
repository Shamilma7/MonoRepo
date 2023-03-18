package com.translatorfrontend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TranslatorFrontendApplication

fun main(args: Array<String>) {
    runApplication<TranslatorFrontendApplication>(*args)
}
