package workshop.spring.metrics.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessMetrics {

    @JsonProperty("conversionRate")
    private final double conversionRate;

    @JsonCreator
    public BusinessMetrics(final double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public double getConversionRate() {
        return conversionRate;
    }
}
