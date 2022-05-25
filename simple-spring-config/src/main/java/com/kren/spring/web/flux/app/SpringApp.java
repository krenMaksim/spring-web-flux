package com.kren.spring.web.flux.app;

import com.kren.spring.web.flux.app.config.SpringWebFluxConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

class SpringApp {

	public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(SpringWebFluxConfig.class);

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

}
