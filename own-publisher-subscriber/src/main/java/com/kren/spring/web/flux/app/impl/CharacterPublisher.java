package com.kren.spring.web.flux.app.impl;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class CharacterPublisher implements Publisher<Character> {

    public static Publisher<Character> just(Character... letter) {
        return new CharacterPublisher(new LinkedList<>(List.of(letter)));
    }

    private final Queue<Character> letters; // should it be thread-safe?

    private CharacterPublisher(Queue<Character> letters) {
        this.letters = letters;
    }

    @Override
    public void subscribe(Subscriber<? super Character> subscriber) {
        log.info("onSubscribe");
        subscriber.onSubscribe(new CharacterSubscription(letters, subscriber));
    }

}