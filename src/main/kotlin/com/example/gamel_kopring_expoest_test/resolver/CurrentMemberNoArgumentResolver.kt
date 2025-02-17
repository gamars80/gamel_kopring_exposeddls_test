package com.example.gamel_kopring_expoest_test.resolver

import com.example.gamel_kopring_expoest_test.annotaion.CurrentMemberNo
import com.example.gamel_kopring_expoest_test.domain.auth.dto.AuthenticatedMember
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class CurrentMemberNoArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        // @CurrentMemberNo가 붙어 있고 파라미터 타입이 Long인지 확인
        return parameter.hasParameterAnnotation(CurrentMemberNo::class.java) &&
                (parameter.parameterType == Long::class.java || parameter.parameterType == java.lang.Long::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        return ReactiveSecurityContextHolder.getContext()
            .map { it.authentication }
            .flatMap { auth ->
                // 인증이 되어 있지 않으면 에러 처리
                if (auth == null || !auth.isAuthenticated) {
                    Mono.error(IllegalStateException("No authenticated user found"))
                } else {
                    // principal이 JwtUser로 설정되었다고 가정
                    val principal = auth.principal
                    if (principal is AuthenticatedMember) {
                        Mono.just(principal.memberNo)
                    } else {
                        Mono.error(IllegalStateException("Invalid authentication principal"))
                    }
                }
            }
    }
}
