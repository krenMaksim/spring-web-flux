package com.kren.spring.web.flux.app.impl;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
class CharacterSubscriber implements Subscriber<Character> {
    
    private static final Long BACK_PRESSURE = 2L;

    private final AtomicLong counter = new AtomicLong();

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(BACK_PRESSURE);
    }

    @Override
    public void onNext(Character character) {
        var capitalLetter = Character.toUpperCase(character);
        log.info("{}", capitalLetter);
        requestMoreLettersIfNeeded();
    }

    private void requestMoreLettersIfNeeded() {
        if (counter.incrementAndGet() == BACK_PRESSURE) {
            counter.set(0);
            subscription.request(BACK_PRESSURE);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("", t);
    }

    @Override
    public void onComplete() {
        log.info("All letters have been recieved");
    }

}