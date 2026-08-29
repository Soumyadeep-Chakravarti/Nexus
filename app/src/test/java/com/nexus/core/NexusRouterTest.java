package com.nexus.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NexusRouterTest {
    @Test
    void routes_messages_only_to_matching_topics() {
        var router = new NexusRouter();
        try (var orders = router.subscribe("orders.created", 2); var billing = router.subscribe("billing.paid", 2)) {
            var message = Message.of("orders.created", "order-42");

            assertEquals(new DeliveryReport(1, 0), router.publish(message));
            assertEquals(message, orders.poll().orElseThrow());
            assertFalse(billing.poll().isPresent());
        }
    }

    @Test
    void reports_backpressure_without_blocking() {
        var router = new NexusRouter();
        try (var subscription = router.subscribe("telemetry", 1)) {
            assertEquals(new DeliveryReport(1, 0), router.publish(Message.of("telemetry", "first")));
            assertEquals(new DeliveryReport(0, 1), router.publish(Message.of("telemetry", "second")));
            assertEquals("first", subscription.poll().orElseThrow().payload());
        }
    }

    @Test
    void closed_subscriptions_stop_receiving_messages() {
        var router = new NexusRouter();
        var subscription = router.subscribe("events", 1);
        subscription.close();

        assertEquals(new DeliveryReport(0, 0), router.publish(Message.of("events", "ignored")));
        assertFalse(subscription.poll().isPresent());
    }

    @Test
    void rejects_invalid_topics_and_capacities() {
        var router = new NexusRouter();

        assertThrows(IllegalArgumentException.class, () -> router.subscribe(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> router.subscribe("valid", 0));
        assertThrows(IllegalArgumentException.class, () -> Message.of("", "payload"));
    }
}
