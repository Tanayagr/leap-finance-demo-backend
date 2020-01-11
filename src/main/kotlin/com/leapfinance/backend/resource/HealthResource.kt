package com.leapfinance.backend.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthResource {


    @GetMapping
    fun checkHealth(@RequestAttribute("user") user: String): String {
        return "pehli fursat me nikal.... $user"
    }

}