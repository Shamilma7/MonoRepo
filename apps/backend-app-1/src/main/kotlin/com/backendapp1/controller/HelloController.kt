package com.backendapp1.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1")
class HelloController {
    @GetMapping("/hello", produces = ["application/json", "text/plain"])
    fun getHello(): ResponseEntity<String> = ResponseEntity.ok("Hello from backend-app-1")
}