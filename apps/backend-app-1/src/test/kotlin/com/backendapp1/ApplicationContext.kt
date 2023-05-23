package com.backendapp1

import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ActiveProfiles("test")
class ApplicationContext {
    @Test
    fun contextLoads(){}
}