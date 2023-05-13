package workshop.spring.metrics.management;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "businessMetrics")
public class BusinessMetricsEndpoint {

    private final Counter cartSessionsCounter;

    private final Counter completedOrdersCounter;

    public BusinessMetricsEndpoint(final MeterRegistry meterRegistry) {
        cartSessionsCounter = meterRegistry.counter("cart.sessions");
        completedOrdersCounter = meterRegistry.counter("orders.completed");
    }

    @ReadOperation
    public BusinessMetrics snapshot() {
        return new BusinessMetrics(computeConversionRate());
    }

    private double computeConversionRate() {
        double completedOrders = completedOrdersCounter.count();
        double openSessions = cartSessionsCounter.count();
        return (openSessions > 0) ? completedOrders / openSessions : 0.0;
    }
}
