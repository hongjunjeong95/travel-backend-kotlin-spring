package com.travel.travelapp.common.interceptors

import com.travel.travelapp.security.AuthUserHandlerArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
): WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(*ALLOWED_METHOD_NAMES.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .exposedHeaders(HttpHeaders.LOCATION)
            .maxAge(5000)
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }

    companion object {
        private const val ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH"
    }
}

