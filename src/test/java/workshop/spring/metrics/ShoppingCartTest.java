package workshop.spring.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import workshop.spring.metrics.Item;
import workshop.spring.metrics.ShoppingCart;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartTest {

    private ShoppingCart cart;

    private MeterRegistry meterRegistry;

    @BeforeEach
    void prepareTest() {
        this.meterRegistry = new SimpleMeterRegistry();
        this.cart = new ShoppingCart(meterRegistry);
    }

    @Test
    @DisplayName("should increment completed orders counter for every non-empty closed order")
    void shouldIncrementCompletedOrdersCounterForEveryNonEmptyClosedOrder() {

        var completedOrdersCounter = meterRegistry.counter("orders.completed");

        assertThat(completedOrdersCounter.count()).isEqualTo(0.0);

        cart.addItem(new Item("Wireless Computer Mouse"));
        cart.addItem(new Item("Wireless Mechanical Keyboard"));
        cart.closeOrder();

        assertThat(completedOrdersCounter.count()).isEqualTo(1.0);
    }

    @Test
    @DisplayName("closing an order on an empty shopping cart should have no effect on the closed orders counter")
    void closingAnOrderOnAnEmptyShoppingCartShouldHaveNoEffect() {

        var completedOrdersCounter = meterRegistry.counter("orders.completed");

        assertThat(completedOrdersCounter.count()).isEqualTo(0.0);
        assertThat(cart.getItems().getItems()).isEmpty();

        cart.closeOrder();

        assertThat(completedOrdersCounter.count()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("adding the first item should create a new shopping cart session")
    void addingTheFirstItemCreatesNewShoppingCartSession() {

        var cartSessionsCounter = meterRegistry.counter("cart.sessions");

        assertThat(cartSessionsCounter.count()).isEqualTo(0.0);

        cart.addItem(new Item("Wireless Computer Mouse"));

        assertThat(cartSessionsCounter.count()).isEqualTo(1.0);
    }

    @Test
    @DisplayName("adding an item after closing a previous order should create a new shopping cart session")
    void addingAnItemAfterClosingPreviousOrderCreatesNewShoppingCartSession() {

        cart.addItem(new Item("Wireless Computer Mouse"));
        cart.addItem(new Item("Wireless Mechanical Keyboard"));

        cart.closeOrder();

        cart.addItem(new Item("Videogame"));

        var cartSessionsCounter = meterRegistry.counter("cart.sessions");
        var completedOrdersCounter = meterRegistry.counter("orders.completed");

        assertThat(completedOrdersCounter.count()).isEqualTo(1.0);
        assertThat(cartSessionsCounter.count()).isEqualTo(2.0);
    }

    @Test
    @DisplayName("removing items until the shopping cart is empty and adding items again should not create a new session")
    void removingItemsUntilShoppingCartIsEmptyAndAddingItemsAgainShouldNotCreateNewSession() {

        var cartSessionsCounter = meterRegistry.counter("cart.sessions");

        cart.addItem(new Item("product-id", "Wireless Computer Mouse"));

        assertThat(cartSessionsCounter.count()).isEqualTo(1.0);

        cart.removeItem("product-id");

        assertThat(cart.getItems().getItems()).isEmpty();
        assertThat(cartSessionsCounter.count()).isEqualTo(1.0);

        cart.addItem(new Item("Wireless Mechanical Keyboard"));

        assertThat(cartSessionsCounter.count()).isEqualTo(1.0);
    }
}
