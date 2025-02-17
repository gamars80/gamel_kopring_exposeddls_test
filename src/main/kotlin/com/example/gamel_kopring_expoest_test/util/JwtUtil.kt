package com.example.gamel_kopring_expoest_test.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.Date

object JwtUtil {
    // 실제 환경에서는 외부 설정(예: application.yml)이나 환경변수로 관리하세요.
    private const val SECRET_KEY = "skdnd-backend-gamel-secret-key-test"
    private const val EXPIRATION_TIME = 86400000L  // 1일 (밀리초)


    fun generateToken(memberNo: Long, loginId: String): String {
        val key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .setSubject(loginId)
            .claim("memberNo", memberNo)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
