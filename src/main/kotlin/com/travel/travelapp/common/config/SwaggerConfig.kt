package com.travel.travelapp.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@OpenAPIDefinition(info = Info(title = "Travel App API 명세서", description = "여행 API 명세서", version = "v1"))
@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val securityScheme: SecurityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER).name("Authorization")
        val securityRequirement: SecurityRequirement = SecurityRequirement().addList("bearerAuth")
        return OpenAPI()
            .components(Components().addSecuritySchemes("bearerAuth", securityScheme))
            .security(Arrays.asList(securityRequirement))
    }
}