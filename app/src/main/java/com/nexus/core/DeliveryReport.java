package com.nexus.core;

/** The outcome of routing one message to a topic's active subscribers. */
public record DeliveryReport(int delivered, int dropped) {
    public DeliveryReport {
        if (delivered < 0 || dropped < 0) {
            throw new IllegalArgumentException("delivery counts must not be negative");
        }
    }
}
