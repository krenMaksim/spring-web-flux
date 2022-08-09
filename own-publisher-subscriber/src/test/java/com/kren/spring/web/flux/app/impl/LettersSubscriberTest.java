package com.kren.spring.web.flux.app.impl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
class LettersSubscriberTest {

    // See link https://www.appsdeveloperblog.com/reactive-programming-creating-publishers-and-subscribers-in-java/
    /*
        We will implement a Subscriber Class ourselves.
        This Subscriber will receive the small letters from the Publisher 
        to which it subscribed, convert them to capital letters, and print them out. 
    */


    @Test
    @Disabled
    void convertLetters() {
        var publisher = Flux.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.log()
            .blockLast();
    }

    @Test
    @Disabled
    void convertLettersV2() {
        var publisher = Flux.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.log()
            .subscribe(new CharacterSubscriber());
    }

    @Test
    @Disabled
    void convertLettersV3() {
        var publisher = Flux.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.log()
            .doOnEach(signal -> log.info("{}", signal))
            .subscribe(new CharacterSubscriber());
    }
    
    @Test
    void convertLettersV4() {
        var publisher = CharacterPublisher.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.subscribe(new CharacterSubscriber());
    }

}
