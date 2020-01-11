package com.leapfinance.backend.security

import com.leapfinance.backend.services.JwtService
import io.jsonwebtoken.JwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter(urlPatterns = ["/api/*"])
class JwtFilter : Filter {
    @Autowired
    private lateinit var jwtTokenService: JwtService

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val authHeaderVal = httpRequest.getHeader(HttpHeaders.AUTHORIZATION)

        if (null == authHeaderVal) {
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }

        try {
            val jwtUser = jwtTokenService.getUser(authHeaderVal)
            httpRequest.setAttribute("user", jwtUser.userEmail)
        } catch (e: JwtException) {
            httpResponse.status = HttpServletResponse.SC_NOT_ACCEPTABLE
            return
        }
        chain?.doFilter(httpRequest, httpResponse)
    }

}