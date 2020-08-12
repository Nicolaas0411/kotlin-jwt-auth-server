package com.challenge.jwt.auth.authserver.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*


@Service
class JwtUtil {
    private val secret = "u8aGkDdwVoiUvPOAMGPj2cIYezg7fj6MS8H7rvD5QWbrXj6rahzMN1pOHJ373Qu"

    fun extractUsername(token: String?): String {
        return extractAllClaims(token).subject
    }

    fun extractExpiration(token: String?): Date {
        return extractAllClaims(token).expiration
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String?): Boolean? {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(username: String): String? {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String? {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean? {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)!!
    }
}