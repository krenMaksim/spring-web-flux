package com.kren.spring.web.flux.app.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Objects;
import java.util.Queue;

@Slf4j
@RequiredArgsConstructor
class CharacterSubscription implements Subscription {

    private final Queue<Character> letters; // should it be thread-safe?
    private final Subscriber<? super Character> subscriber;

    @Override
    public void request(long n) {
        try {
            processRequest(n);
        } catch (Throwable t) {
            log.error("onError", t);
            subscriber.onError(t);
        }
    }

    private void processRequest(long requestedElementsNumber) {
        log.info("request({})", requestedElementsNumber);
        for (long element = 1; element <= requestedElementsNumber; element++) {
            var letter = letters.poll();
            if (Objects.isNull(letter)) {
                log.info("onComplete()");
                subscriber.onComplete();
                return;
            }
            log.info("onNext({})", letter);
            subscriber.onNext(letter);
        }
    }

    @Override
    public void cancel() {
        log.info("cancel");
    }

}