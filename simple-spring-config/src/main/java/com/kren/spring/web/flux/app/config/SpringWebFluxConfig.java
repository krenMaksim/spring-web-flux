package com.kren.spring.web.flux.app.config;

import com.kren.spring.web.flux.app.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableWebFlux
@Configuration
@ComponentScan("com.kren.spring.web.flux.app.handler")
public class SpringWebFluxConfig { // in case it is needed can implement WebFluxConfigurer

    private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);

    @Bean
    RouterFunction<ServerResponse> routerFunction(PersonHandler personHandler) {
        return route()
            .GET("/person/{id}", ACCEPT_JSON, personHandler::getPerson)
            .GET("/person", ACCEPT_JSON, personHandler::listPeople)
            .POST("/person", ACCEPT_JSON, personHandler::createPerson)
            .build();
    }
}
