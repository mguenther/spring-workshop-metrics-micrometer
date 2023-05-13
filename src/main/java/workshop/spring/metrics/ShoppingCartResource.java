package workshop.spring.metrics;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import workshop.spring.metrics.util.StopWatch;

import java.util.concurrent.TimeUnit;

@RestController
public class ShoppingCartResource {

    private final ShoppingCart cart;

    private final DistributionSummary serviceTimes;

    @Autowired
    public ShoppingCartResource(final ShoppingCart cart, final MeterRegistry meterRegistry) {
        this.cart = cart;
        this.serviceTimes = meterRegistry.summary("service-times");
    }

    @GetMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> getItems() {
        final var timer = StopWatch.time();
        try {
            delay();
            return ResponseEntity.ok(cart.getItems());
        } finally {
            serviceTimes.record(timer.elapsedInMillis());
        }
    }

    @PutMapping(path = "/cart/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> addItem(@PathVariable("productId") final String productId, @RequestBody final Item item) {
        final var timer = StopWatch.time();
        try {
            delay();
            return ResponseEntity.ok(cart.addItem(item));
        } finally {
            serviceTimes.record(timer.elapsedInMillis());
        }
    }

    @DeleteMapping(path = "/cart/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> removeItem(@PathVariable("productId") final String productId) {
        final var timer = StopWatch.time();
        try {
            delay();
            return ResponseEntity.ok(cart.removeItem(productId));
        } finally {
            serviceTimes.record(timer.elapsedInMillis());
        }
    }

    @PostMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> completeOrder() {
        final var timer = StopWatch.time();
        try {
            cart.closeOrder();
            return ResponseEntity.status(HttpStatus.OK).build();
        } finally {
            serviceTimes.record(timer.elapsedInMillis());
        }
    }

    private void delay() {
        try {
            final int delay = (int) (Math.random() * 1000) + 500; // introduce a delay from [500;1500) milliseconds
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            // ignore
            e.printStackTrace();
        }
    }
}
