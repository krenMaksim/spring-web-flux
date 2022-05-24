package com.kren.spring.web.flux.app;

import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

class WebHandlerApp {

	public static void main(String[] args) {

        var handler = WebHttpHandlerBuilder.webHandler(new DispatcherHandler())
            .filter(new CustomWebFilter())
            .build();

        var server = HttpServer.create()
            .host("localhost")
            .port(8080)
            .handle(new ReactorHttpHandlerAdapter(handler))
            .bindNow();

        server.onDispose()
            .block();
	}

    static class CustomWebFilter implements WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            return Mono.fromRunnable(() -> {
                System.out.println("Invoked: " + this.getClass().getSimpleName());
            });
        }

    }
}
