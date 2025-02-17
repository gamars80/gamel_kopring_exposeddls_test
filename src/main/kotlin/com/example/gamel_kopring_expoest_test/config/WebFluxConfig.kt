package com.example.gamel_kopring_expoest_test.config

import com.example.gamel_kopring_expoest_test.resolver.CurrentMemberNoArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

@Configuration
class WebFluxConfig : WebFluxConfigurer {
    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(CurrentMemberNoArgumentResolver())
    }
}