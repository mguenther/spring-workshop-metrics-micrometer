package workshop.spring.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SessionScope
public class ShoppingCart {

    private final Counter cartSessionsCounter;

    private final Counter completedOrdersCounter;

    private List<Item> items;

    private boolean isNewSession;

    public ShoppingCart(final MeterRegistry meterRegistry) {
        items = new ArrayList<>();
        isNewSession = true;
        cartSessionsCounter = meterRegistry.counter("cart.sessions");
        completedOrdersCounter = meterRegistry.counter("orders.completed");
    }

    public Items addItem(final Item item) {
        if (isNewSession) {
            isNewSession = false;
            cartSessionsCounter.increment();
        }
        items.add(item);
        return new Items(items);
    }

    public Items removeItem(final String productId) {
        items = items.stream().filter(item -> !item.getProductId().equals(productId)).collect(Collectors.toList());
        return new Items(items);
    }

    public Items getItems() {
        return new Items(items);
    }

    public void closeOrder() {
        if (items.isEmpty()) {
            return;
        }
        items.clear();
        isNewSession = true;
        completedOrdersCounter.increment();
    }
}
