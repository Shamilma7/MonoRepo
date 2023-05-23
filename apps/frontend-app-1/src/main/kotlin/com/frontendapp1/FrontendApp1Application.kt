package com.frontendapp1

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FrontendApp1Application {
    @Value("\${backend-app-1-service.uri}")
    private val backendApp1ServiceUri: String? = null

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route("backend-app-1-service") { r ->
                r.path("/api/backend-app-1-service/v1/{segment}")
                    .filters { f ->
                        f.rewritePath(
                            "/api/backend-app-1-service/v1/(?<segment>.*)", "/api/v1/\${segment}"
                        )
                    }
                    .uri(backendApp1ServiceUri)
            }.build()
}

fun main(args: Array<String>) {
    runApplication<FrontendApp1Application>(*args)
}