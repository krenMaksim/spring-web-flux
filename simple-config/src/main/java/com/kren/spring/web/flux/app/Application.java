package com.kren.spring.web.flux.app;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class Application {

	public static void main(String[] args) {

        var handler = new HttpHandler() {
            @Override
            public Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
                return Mono.fromRunnable(() -> {
                    System.out.println("DO NOTHING");
                    System.out.println(request);
                    System.out.println(response);
                });
            }
        };

        var server = HttpServer.create()
            .host("localhost")
            .port(8080)
            .handle(new ReactorHttpHandlerAdapter(handler))
            .bindNow();

        server.onDispose()
            .block();
	}
}
