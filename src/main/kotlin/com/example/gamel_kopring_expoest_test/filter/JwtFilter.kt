package com.example.gamel_kopring_expoest_test.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import com.example.gamel_kopring_expoest_test.domain.auth.dto.AuthenticatedMember;

class JwtFilter : WebFilter {

    // JwtUtil에서 사용한 SECRET_KEY와 동일하게 사용합니다.
    private val secretKey = "skdnd-backend-gamel-secret-key-test"

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            try {
                // 키 생성 및 토큰 파싱
                val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
                val claims: Claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .body

                // "memberNo" 클레임 추출 (Number 타입이므로 toLong() 변환)
                val memberNo = (claims["memberNo"] as? Number)?.toLong()
                val roleType = (claims["roleType"] as? String)
                if (memberNo != null) {
                    // AuthenticatedMember를 생성하여 Authentication의 principal로 설정
                    val authenticatedMember = AuthenticatedMember(memberNo, roleType.toString())
                    val auth = UsernamePasswordAuthenticationToken(
                        authenticatedMember,
                        null,
                        authenticatedMember.authorities
                    )
                    // SecurityContext에 Authentication을 설정하여 downstream에서 사용 가능하도록 함
                    return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                }
            } catch (e: JwtException) {
                // 토큰 검증 실패 시 로그를 남기거나 추가 처리가 필요하면 구현하세요.
                // 예: return Mono.error(UnauthorizedException("Invalid JWT token"))
            }
        }
        return chain.filter(exchange)
    }
}