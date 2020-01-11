package com.leapfinance.backend.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthResource {


    @GetMapping
    fun checkHealth(): String {
        return "ok"
    }

}