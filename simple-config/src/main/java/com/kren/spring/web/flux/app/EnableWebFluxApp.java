package com.kren.spring.web.flux.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

class EnableWebFluxApp {

	public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        var handler = WebHttpHandlerBuilder.applicationContext(context)
            .build();

        var server = HttpServer.create()
            .host("localhost")
            .port(8080)
            .handle(new ReactorHttpHandlerAdapter(handler))
            .bindNow();

        server.onDispose()
            .block();
	}

    @Configuration
    @EnableWebFlux
    class WebConfig implements WebFluxConfigurer {

    }
}
