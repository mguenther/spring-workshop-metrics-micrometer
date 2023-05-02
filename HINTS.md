# Hints

**Spoiler Alert**

We encourage you to work on the assignment yourself or together with your peers. However, situations may present themselves to you where you're stuck on a specific assignment. Thus, this document contains a couple of hints that ought to guide you through a specific task of the lab assignment.

In any case, don't hesitate to talk to us if you're stuck on a given problem!

## Task #1 - Task #3

Please refer to the JUnit Jupiter tests.

## Task #4

1. Simply inject the bean `MeterRegistry`, either via constructor-based or setter-based injection (prefer the former).
2. A `Timer` could be sufficient for very simple use cases. The `DistributionSummary` also allows us to get more complex statistics and is a suitable basis for exporting _cumulative distribution functions_ (CDFs), but at the cost of a higher demand for memory.
3. You've done this before. Refer to prior tasks if in doubt.
4. You've done this before. Refer to prior tasks if in doubt.
5. You've done this before. Refer to prior tasks if in doubt.

## Task #5

The instrument builder exposes a method `tags` which consumes key-value-pairs. Use this method in order to prepare the desired instrument with your tags.