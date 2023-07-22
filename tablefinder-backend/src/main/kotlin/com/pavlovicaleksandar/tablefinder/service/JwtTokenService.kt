package com.pavlovicaleksandar.tablefinder.service

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service

@Service
class JwtTokenService (
    @Value("\${jwt.secret}")
    private val secret: String,
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
) {
    fun createToken(user: User): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(user.username)
            .claim("userId", user.id)
            .claim("username", user.username)
            .claim("role", user.role.name)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String): UserInfo? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val userId = UUID.fromString(jwt.claims["userId"] as String)
            val username = jwt.claims["username"] as String
            val role = Role.valueOf(jwt.claims["role"] as String)
            UserInfo(userId, username, role)
        } catch (e: Exception) {
            null
        }
    }
}

data class UserInfo(val userId: UUID, val username: String, val role: Role)
