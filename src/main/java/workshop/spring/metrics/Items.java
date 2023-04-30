package workshop.spring.metrics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Items {

    private final List<Item> items;

    @JsonCreator
    public Items(@JsonProperty("items") List<Item> items) {
        if (items != null && items.size() > 0) {
            this.items = new ArrayList<>(items.size());
            this.items.addAll(items);
        } else {
            this.items = List.of();
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
