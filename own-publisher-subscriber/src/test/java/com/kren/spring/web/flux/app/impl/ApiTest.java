package com.kren.spring.web.flux.app.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.util.List;

@Slf4j
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
    void getLetters() {
        var responseBody = webClient.get()
            .uri("/letters")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
            .block();

        log.info("responseBody {}", responseBody);
    }

    @Test
    @Disabled
    void getLettersV2() {
        var responseBody = webClient.get()
            .uri("/letters")
            .retrieve()
            .bodyToFlux(String.class)
            .collectList()
            .block();

        log.info("responseBody {}", responseBody);
    }

}
