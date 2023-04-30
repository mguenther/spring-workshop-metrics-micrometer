package workshop.spring.metrics.management;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "businessMetrics")
public class BusinessMetricsEndpoint {

    public BusinessMetricsEndpoint(final MeterRegistry meterRegistry) {
    }

    @ReadOperation
    public BusinessMetrics snapshot() {
        return new BusinessMetrics(computeConversionRate());
    }

    private double computeConversionRate() {
        return 0.0;
    }
}
