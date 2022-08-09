import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

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
    void convertLettersV3() {
        var publisher = Flux.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.log()
            .doOnEach(signal -> log.info("[{}] {}", Thread.currentThread().getName(), signal))
            .subscribe(new CharacterSubscriber());
    }

    @Slf4j
    private static class CharacterSubscriber implements Subscriber<Character> {
        
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
            log.info("[{}] {}", currentThread(), capitalLetter);
            requestMoreLettersIfNeeded();
        }

        private void requestMoreLettersIfNeeded() {
            if (counter.incrementAndGet() == BACK_PRESSURE) {
                subscription.request(BACK_PRESSURE);
                counter.set(0);
            }
        }

        @Override
        public void onError(Throwable t) {
            log.error(String.format("[%s]", currentThread()), t);
        }

        @Override
        public void onComplete() {
            log.info("[{}] All letters have been recieved", currentThread());
        }

        private static String currentThread() {
            return Thread.currentThread().getName();
        }

    }

}
