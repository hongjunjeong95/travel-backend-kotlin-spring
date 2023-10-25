package com.travel.travelapp.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "Travel App API 명세서", description = "여행 API 명세서", version = "v1"))
@Configuration
class SwaggerConfig {
    private val SECURITY_SCHEME_NAME = "Authorization"

    @Bean
    fun openAPI(): OpenAPI {
        val securityScheme: SecurityScheme = SecurityScheme()
            .name(SECURITY_SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
        val securityRequirement: SecurityRequirement = SecurityRequirement().addList("bearerAuth")
        return OpenAPI()
            .components(Components().addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme))
            .addSecurityItem(SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .security(listOf(securityRequirement))
    }
}
