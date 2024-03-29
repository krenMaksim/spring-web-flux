package com.kren.spring.web.flux.app;

import com.kren.spring.web.flux.app.impl.CharacterPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
class SpringApp {

	public static void main(String[] args) {
        // Activating Debug Mode. See doc https://projectreactor.io/docs/core/release/reference/#debug-activate
        Hooks.onOperatorDebug();

        SpringApplication.run(SpringApp.class, args);
	}

    @Bean
    RouterFunction<ServerResponse> routerFunction(LettersHandler lettersHandler) {
        return route().GET("/letters", lettersHandler::handle)
            .build();
    }

    @Service
    static class LettersHandler {

        public Mono<ServerResponse> handle(ServerRequest request) {
            return ok().body(CharacterPublisher.just('a', 'b', 'c', 'd', 'e', 'f', 'g'), Character.class);
        }
    }

    @Configuration
    static class LoggingConf implements WebFluxConfigurer {

        @Override
        public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
            configurer.defaultCodecs().enableLoggingRequestDetails(true);
        }

    }

}
