import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class LettersSubscriberTest {

    // See link https://www.appsdeveloperblog.com/reactive-programming-creating-publishers-and-subscribers-in-java/
    /*
        We will implement a Subscriber Class ourselves.
        This Subscriber will receive the small letters from the Publisher 
        to which it subscribed, convert them to capital letters, and print them out. 
    */


    @Test
    void doSomething() {
        var publisher = Flux.just('a', 'b', 'c', 'd', 'e', 'f', 'g');

        publisher.log()
            .blockLast();
    }

}
