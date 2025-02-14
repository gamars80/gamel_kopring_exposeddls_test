package com.example.gamel_kopring_expoest_test.config

import org.springframework.context.annotation.Bean
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.slf4j.LoggerFactory
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.WebSession

@Configuration
class SpringDocConfig() {

    private val logger = LoggerFactory.getLogger(SpringDocConfig::class.java)

    init {
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(
            WebSession::class.java,
        )
    }

    @Bean
    fun openApi(): OpenAPI {
        logger.debug("Starting Swagger")

        return OpenAPI()
            .info(
                Info()
                    .title("Gamel Test rest api")
                    .version("v0.0.1")
                    .description("Cafe REST API")
            )
    }
}