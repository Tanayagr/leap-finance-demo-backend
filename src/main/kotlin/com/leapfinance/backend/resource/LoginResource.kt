package com.leapfinance.backend.resource

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginResource {

    @PostMapping
    fun userLogin() {
        return "ok"
    }
}