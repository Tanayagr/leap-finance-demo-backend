package com.leapfinance.backend.resource

import com.leapfinance.backend.data.JwtUser
import com.leapfinance.backend.data.UserDTO
import com.leapfinance.backend.services.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.ServletException


@RestController
@RequestMapping("/login")
class LoginResource {

    @Autowired
    lateinit var jwtService: JwtService

    @PostMapping
    @Throws(ServletException::class)
    fun login(@RequestBody user: UserDTO): String {

        var jwtToken = ""
        val email = user.email
        val password = user.password
        if (email == null || password == null) {
            throw ServletException("Please fill in username and password")
        }

//        val user = userService.findByEmail(email) ?: throw ServletException("User email not found.")
//
//        val pwd = user.getPassword()

//        if (password != pwd) {
//            throw ServletException("Invalid login. Please check your email and password.")
//        }

        return jwtService.getToken(JwtUser(email))
    }
}