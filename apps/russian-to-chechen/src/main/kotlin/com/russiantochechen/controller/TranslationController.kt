package com.russiantochechen.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1")
class TranslationController {

    @GetMapping("/translation", produces = ["application/json", "text/plain"])
    fun getHello(): ResponseEntity<String> = ResponseEntity.ok("translation")

}