package com.kren.spring.web.flux.app.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class PersonHandler {

    public Mono<ServerResponse> listPeople(ServerRequest request) {
        return Mono.fromRunnable(() -> System.out.println("Requested listPeople"));
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        return Mono.fromRunnable(() -> System.out.println("Requested createPerson"));
    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        return Mono.fromRunnable(() -> System.out.println("Requested getPerson"));
    }
}
