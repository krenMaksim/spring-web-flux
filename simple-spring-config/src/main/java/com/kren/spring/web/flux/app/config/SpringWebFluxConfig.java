package com.kren.spring.web.flux.app.config;

import com.kren.spring.web.flux.app.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableWebFlux
@Configuration
@ComponentScan("com.kren.spring.web.flux.app.handler")
public class SpringWebFluxConfig { // in case it is needed can implement WebFluxConfigurer

    @Bean
    RouterFunction<ServerResponse> routerFunction(PersonHandler personHandler) {
        return route()
            .GET("/person/{id}", accept(APPLICATION_JSON), personHandler::getPerson)
            .GET("/person", accept(APPLICATION_JSON), personHandler::listPeople)
            .POST("/person", accept(APPLICATION_JSON), personHandler::createPerson)
            .build();
    }
}
