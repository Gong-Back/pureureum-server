package gongback.pureureumserver.security

import gongback.pureureumserver.exception.JwtExpiredException
import gongback.pureureumserver.exception.JwtNotValidException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

private const val BEARER = "Bearer"

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
) {
    val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    fun createToken(payload: Long): String {
        val claims: Claims = Jwts.claims().setSubject(payload.toString())
        val now = Date()
        val expiration = Date(now.time + jwtProperties.expiredTimeMs)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(payload: Long): String {
        val claims: Claims = Jwts.claims().setSubject(payload.toString())
        val now = Date()
        val expiration = Date(now.time + jwtProperties.refreshExpiredTimeMs)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    fun getSubject(bearerToken: String): Long = getClaimsJws(extractToken(bearerToken))
        .body
        .subject
        .toLong()

    private fun extractToken(bearerToken: String): String {
        val (tokenType, token) = splitToTokenFormat(bearerToken)
        if (tokenType != BEARER || !isValidToken(token)) {
            throw JwtNotValidException()
        }
        return token
    }

    private fun isValidToken(token: String): Boolean = try {
        getClaimsJws(token)
        true
    } catch (e: ExpiredJwtException) {
        throw JwtExpiredException()
    } catch (e: JwtException) {
        false
    } catch (e: IllegalArgumentException) {
        false
    }

    private fun getClaimsJws(token: String) = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)

    private fun splitToTokenFormat(authorization: String): Pair<String, String> = try {
        val tokenFormat = authorization.split(" ")
        tokenFormat[0] to tokenFormat[1]
    } catch (e: IndexOutOfBoundsException) {
        throw JwtNotValidException()
    }
}
