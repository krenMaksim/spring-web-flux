package com.kren.spring.web.flux.app.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

class ApiTest {

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        var httpClient = HttpClient.create()
            .proxy(spec -> spec.type(ProxyProvider.Proxy.HTTP)
                .host("localhost")
                .port(8888));

        webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Test
    @SneakyThrows
    void getLetters() {
        webClient.get()
            .uri("/letters")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
            .flatMapIterable(Function.identity())
            .map(str -> str.charAt(0))
            .log()
            .subscribe(new CharacterSubscriber());

        TimeUnit.SECONDS.sleep(1);
    }

}
