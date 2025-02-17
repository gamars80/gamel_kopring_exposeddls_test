package com.example.gamel_kopring_expoest_test.config

import com.example.gamel_kopring_expoest_test.filter.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            // CORS 설정 활성화 (아래에서 정의한 corsConfigurationSource() 적용)
            // CORS 설정: lambda 내부에서 configurationSource 설정
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            // CSRF 설정: lambda 내부에서 disable 호출
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeExchange { exchanges ->
                exchanges
                    // Swagger 관련 엔드포인트는 인증 없이 접근 허용
                    .pathMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()
                    .pathMatchers("/api/auth/login").permitAll()
                    .pathMatchers("/api/member/signUp").permitAll()
                    // 그 외 모든 요청은 인증 필요 (상황에 따라 permitAll() 등으로 변경)
                    .anyExchange().authenticated()
            }
            .addFilterAt(JwtFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            // 허용할 Origin (실제 사용하는 도메인으로 수정)
            allowedOrigins = listOf("http://localhost:8080", "https://example.com")
            // 허용할 HTTP 메서드
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            // 모든 Header 허용
            allowedHeaders = listOf("*")
            // 쿠키나 인증 정보를 허용할 경우 true
            allowCredentials = true
            // pre-flight 요청 캐싱 시간 (초)
            maxAge = 3600
        }
        val source = UrlBasedCorsConfigurationSource()
        // 모든 경로에 대해 위 CORS 설정 적용
        source.registerCorsConfiguration("/api/**", configuration)
        return source
    }
}
