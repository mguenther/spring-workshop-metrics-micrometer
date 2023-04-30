package workshop.spring.metrics.management;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessMetricsEndpointTest {

    private BusinessMetricsEndpoint endpoint;

    private MeterRegistry meterRegistry;

    @BeforeEach
    void prepareTest() {
        this.meterRegistry = new SimpleMeterRegistry();
        this.endpoint = new BusinessMetricsEndpoint(meterRegistry);
    }

    @Test
    @DisplayName("conversion rate should take completed orders and active cart sessions into account")
    void conversionRateShouldTakeCompletedOrdersAndActiveCartSessionsIntoAccount() {

        var cartSessionsCounter = meterRegistry.counter("cart.sessions");
        var completedOrdersCounter = meterRegistry.counter("orders.completed");

        cartSessionsCounter.increment(60.0);
        completedOrdersCounter.increment(4.0);

        var businessMetrics = endpoint.snapshot();

        assertThat(businessMetrics.getConversionRate()).isCloseTo(4.0 / 60.0, Percentage.withPercentage(1));
    }
}

