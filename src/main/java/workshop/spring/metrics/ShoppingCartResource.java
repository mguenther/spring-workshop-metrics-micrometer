package workshop.spring.metrics;

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

@RestController
public class ShoppingCartResource {

    private final ShoppingCart cart;

    @Autowired
    public ShoppingCartResource(final ShoppingCart cart) {
        this.cart = cart;
    }

    @GetMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> getItems() {
        return ResponseEntity.ok(cart.getItems());
    }

    @PutMapping(path = "/cart/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> addItem(@PathVariable("productId") final String productId, @RequestBody final Item item) {
        return ResponseEntity.ok(cart.addItem(item));
    }

    @DeleteMapping(path = "/cart/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> removeItem(@PathVariable("productId") final String productId) {
        return ResponseEntity.ok(cart.removeItem(productId));
    }

    @PostMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Items> completeOrder() {
        cart.closeOrder();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
