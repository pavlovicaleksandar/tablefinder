package com.pavlovicaleksandar.tablefinder.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtTokenService (
    @Value("\${jwt.secret}")
    private val secret: String,
) {

    private val expiration: Long = 24*60*60*60

    fun generateToken(username: String, role: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .setClaims(mapOf(
                "username" to username,
                "role" to role
            ))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims: Claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
            !claims.expiration.before(Date())
        } catch (ex: Exception) {
            false
        }
    }

    fun getUserInfo(token: String): UserInfo? {
        return try {
            val claims: Claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body

            val username = claims["username"] as String
            val role = claims["role"] as String

            return UserInfo(username, role)
        } catch (ex: Exception) {
            null
        }
    }
}

data class UserInfo(val username: String, val role: String)
