package com.kren.spring.web.flux.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class EnableWebFluxApp {

	public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        var handler = WebHttpHandlerBuilder.webHandler(context.getBean(WebHandler.class))
            .build();

        var server = HttpServer.create()
            .host("localhost")
            .port(8080)
            .handle(new ReactorHttpHandlerAdapter(handler))
            .bindNow();

        server.onDispose()
            .block();
	}


    @Component
    static class CustomWebFilter implements WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            return Mono.fromRunnable(() -> {
                System.out.println("Invoked: " + this.getClass()
                    .getSimpleName());
            });
        }

    }
}
