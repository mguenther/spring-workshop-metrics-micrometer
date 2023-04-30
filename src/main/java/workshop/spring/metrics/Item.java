package workshop.spring.metrics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Item {

    private final String productId;

    private final String title;

    public Item(final String title) {
        this(randomProductId(), title);
    }

    @JsonCreator
    public Item(@JsonProperty("productId") String productId,
                @JsonProperty("title") String title) {
        this.productId = productId;
        this.title = title;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    private static String randomProductId() {
        return UUID.randomUUID().toString().substring(0, 7);
    }
}
