import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
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
    @Disabled
    void convertLettersV3() {
        var publisher = Flux.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.log()
            .doOnEach(signal -> log.info("[{}] {}", Thread.currentThread().getName(), signal))
            .subscribe(new CharacterSubscriber());
    }
    
    @Test
    void convertLettersV4() {
        var publisher = CharacterPublisher.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.subscribe(new CharacterSubscriber());
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
                counter.set(0);
                subscription.request(BACK_PRESSURE);
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

    @Slf4j
    private static class CharacterPublisher implements Publisher<Character> {

        public static Publisher<Character> just(Character... letter) {
            return new CharacterPublisher(new LinkedList<>(List.of(letter)));
        }

        private final Queue<Character> letters; // should it be thread-safe?

        public CharacterPublisher(Queue<Character> letters) {
            this.letters = letters;
        }

        @Override
        public void subscribe(Subscriber<? super Character> subscriber) {
            log.info("onSubscribe");
            subscriber.onSubscribe(new CharacterSubscription(letters, subscriber));
        }

    }

    @Slf4j
    @RequiredArgsConstructor
    private static class CharacterSubscription implements Subscription {

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
            log.info("request {}", requestedElementsNumber);
            for (long element = 1; element <= requestedElementsNumber; element++) {
                var letter = letters.poll();
                if (Objects.isNull(letter)) {
                    subscriber.onComplete();
                    return;
                }
                subscriber.onNext(letter);
            }
        }

        @Override
        public void cancel() {
            log.info("cancel");
        }

    }

}
