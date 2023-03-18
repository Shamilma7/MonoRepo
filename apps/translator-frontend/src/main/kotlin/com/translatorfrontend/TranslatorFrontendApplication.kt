package com.translatorfrontend

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TranslatorFrontendApplication {
    @Value("\${russian-to-chechen-service.uri}")
    private val russianToChechenServiceUri: String? = null

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route("russian-to-chechen-service") { r ->
                r.path("/api/russian-to-chechen-service/v1/{segment}")
                    .filters { f ->
                        f.rewritePath(
                            "/api/russian-to-chechen-service/v1/(?<segment>.*)", "/api/v1/\${segment}"
                        )
                    }
                    .uri(russianToChechenServiceUri)
            }.build()

}

fun main(args: Array<String>) {
    runApplication<TranslatorFrontendApplication>(*args)
}