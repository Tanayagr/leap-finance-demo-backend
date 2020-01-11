package com.leapfinance.backend.services

import com.leapfinance.backend.data.JwtUser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct


@Service
class JwtService(
        @Value("\${app.jwt.expire.hours}") val expireHours: Long,
        @Value("\${app.jwt.token.secret}") val plainSecret: String
) {
    private var encodedSecret: String? = null

    @PostConstruct
    protected fun init() {
        this.encodedSecret = generateEncodedSecret(this.plainSecret)
    }

    protected fun generateEncodedSecret(plainSecret: String): String {
        if (StringUtils.isEmpty(plainSecret)) {
            throw IllegalArgumentException("JWT secret cannot be null or empty.")
        }
        return Base64
                .getEncoder()
                .encodeToString(plainSecret.toByteArray())
    }

    protected fun getExpirationTime(): Date {
        val now = Date()
        val expireInMilis = TimeUnit.HOURS.toMillis(expireHours)
        return Date(expireInMilis + now.time)
    }

    protected fun getUser(encodedSecret: String?, token: String): JwtUser {
        val claims = Jwts.parser()
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .body
        val userName = claims.subject
        return JwtUser(userName)
    }

    fun getUser(token: String): JwtUser {
        return getUser(encodedSecret, token)
    }

    protected fun getToken(encodedSecret: String?, jwtUser: JwtUser): String {
        val now = Date()
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(jwtUser.userEmail)
                .claim("user", jwtUser.userEmail)
                .setIssuedAt(now)
                .setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact()
    }

    fun getToken(jwtUser: JwtUser): String {
        return getToken(encodedSecret, jwtUser)
    }
}