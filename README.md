# Spring Workshop - Metrics with Micrometer - Lab

This repository includes a lab assignment focused on collecting, aggregating, and exposing technical and business metrics using Spring (Boot), Micrometer, and Spring Boot Actuator.

## Getting Started

In this exercise, we will focus on a simple and well-known domain: a shopping cart system with basic functionality. Users can add items to their shopping cart, remove items, and place an order for all items currently in their cart. An HTTP interface (refer to ShoppingCartResource) enables interaction with the shopping cart. We aim to collect metrics to gain insights into:

* Business performance
* Technical soundness of the system

We will use Micrometer for collecting these metrics and configure Spring Boot Actuator to expose them.

This repository includes request definitions compatible with IntelliJ IDEA's HTTP Client, located in the http directory. Use them to familiarize yourself with the HTTP endpoints that this service exposes.

Additionally, the repository contains several (currently failing) JUnit Jupiter test cases. Your task is to implement the missing components and ensure these test cases pass.

The service is configured to run on port 9000.
