package com.example.gamel_kopring_expoest_test.domain.common.dto

import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class PagingRequestArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        // 파라미터 타입이 PagingRequest인 경우 지원
        return parameter.parameterType == Pageable::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        // query parameter로부터 page와 size를 읽어옴 (없으면 기본값 사용)
        val queryParams = exchange.request.queryParams
        val page = queryParams.getFirst("page")?.toLongOrNull() ?: 1
        val size = queryParams.getFirst("size")?.toIntOrNull() ?: 10
        return Mono.just(Pageable(page = page, size = size))
    }
}
