# Lab Assignment

## Task #1: Calculate conversion rate for orders

Examine the `ShoppingCart` class, which is central to the shopping cart service. Its constructor is already designed to work with the default `MeterRegistry` configured by Spring Boot. Your task is to utilize this `MeterRegistry` to establish the necessary Micrometer instruments for calculating a conversion rate for orders.

Calculate the conversion rate using this formula:

```plain
conversion rate = number of completed orders / number of active shopping cart sessions
```

The `ShoppingCart` class functions simply, and counts new shopping cart sessions in the following scenarios:

* Count as a new session when there is no prior interaction with the shopping cart and a user adds an item.
* Count as a new session when a user adds a new item to the shopping cart after completing a previous order.
* Adding and removing an item, leaving the shopping cart empty, and then adding another item counts as the same session.

If you have integrated the appropriate Micrometer instruments, all test cases in the `ShoppingCartTest` class should pass.

## Task #2: Use the Spring Boot Actuator to fetch metrics

All instruments registered with the default `MeterRegistry` are automatically exposed via the Actuator endpoint. To familiarize yourself with this endpoint, use the predefined HTTP requests found in the `actuator.http` file.

## Task #3: Expose business metrics via a custom Actuator endpoint

Business metrics often stem from simpler building blocks, such as counters used when calculating conversion rates. These derived metrics do not appear at the standard Actuator endpoint for metrics. As a result, we will implement a custom Actuator endpoint to expose the conversion metric.

The scaffolding for this task is already set up. Examine the BusinessMetricsEndpoint class and complete the missing components.

Note: To verify all available active Actuator endpoints and ensure the custom endpoint is properly connected, use http://localhost:9000/actuator.

If everything is correctly integrated, all test cases in the `BusinessMetricsEndpointTest` class should pass.

## Task #4: Compute service times for the `ShoppingCartResource`

The service time represents the duration between the start and completion of a specific operation. We are interested in analyzing our system's behavior concerning the time required to complete particular operations. Any Micrometer instruments utilized should be registered with the default `MeterRegistry`.

1. Examine the `ShoppingCartResource` class. What steps are necessary to make the default `MeterRegistry` accessible for this class?
2. Can you identify suitable Micrometer instruments for measuring service times?
3. Register a Micrometer instrument with the ID `service-times` and collect service times for all operations implemented by `ShoppingCartResource`.
4. Utilize the request definitions in the `shopping-cart.http` file to send multiple requests.
5. Use Spring Boot Actuator to confirm that the `service-times` metric has been properly integrated.

Note: Introducing artificial delays can make the service-times metric more engaging. To add a short, randomized delay to each operation, use the following implementation:

```java
private void delay() {
    try {
        final int delay = (int) (Math.random() * 1000) + 500; // introduce a delay from [500;1500) milliseconds
        TimeUnit.MILLISECONDS.sleep(delay);
    } catch (InterruptedException e) {
        // ignore
        e.printStackTrace();
    }
}
```

## Task #5: Differentiate service times on operation using tags

Micrometer instruments support tags for further categorization of metric data points. In the previous task, we implemented a single `service-times` metric without additional dimensions. We now want to categorize service times into use cases, where a use case is identified by the operation targeted by the HTTP requests to the shopping cart resource.

1. Apply the `use-case` tag to the Micrometer instrument defined in the previous task. Assign unique use case values for the operations you wish to measure service times for.
2. Use the request definitions in the `shopping-cart.http` file to send multiple requests.
3. Use the request definitions in the `actuator.http` file to verify that the service-times metric is properly integrated and that you can access service times for a specific operation.

Note: Tags are key-value pairs. For example, to use a tag that differentiates between HTTP methods, you could use the 2-tuple `(method, GET)`. Be aware that enabling tagging on any Micrometer instrument creates a dedicated object of that instrument for every specific (ID, tag) combination. For instance, if you define an HTTP request counter using the method tag to differentiate between HTTP GET and HTTP POST requests, the following two counters would be instantiated as separate Java objects serving a common counter metric identified by its ID `http.requests.total`.

```java
Counter getCounter =  registry.counter(
  "http.requests.total",    // ID
  "method",                 // key of the tag
  "GET");                   // value of the tag
  
Counter postCounter = registry.counter(
  "http.requests.total",    // ID
  "method",                 // key of the tag
  "POST");                  // value of the tag
```

## That's it! You've done great!

You have completed all assignments. If you have any further questions or need clarification, please don't hesitate to reach out to us. We're here to help.