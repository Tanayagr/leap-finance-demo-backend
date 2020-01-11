package com.leapfinance.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication

@ServletComponentScan("com.leapfinance.backend.security")
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
