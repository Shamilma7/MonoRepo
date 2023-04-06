package com.russiantochechen.controller

import com.russiantochechen.Translator
import com.russiantochechen.dto.TranslationDto
import com.russiantochechen.dto.TranslationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1")
class TranslationController {

    @Autowired
    lateinit var translator: Translator

    @GetMapping("/hello", produces = ["application/json", "text/plain"])
    fun getHello(): ResponseEntity<String> = ResponseEntity.ok("translation")


    @PostMapping("/translation", produces = ["application/json", "text/plain"])
    fun translateToChechen(
        @RequestBody request: TranslationRequest
    ): ResponseEntity<TranslationDto> {
        val result = translator.translate(request.input)
        return ResponseEntity.ok(TranslationDto(result = result))
    }
}