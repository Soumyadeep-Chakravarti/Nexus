package com.nexus.core;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/** An immutable message accepted by the in-process Nexus router. */
public record Message(UUID id, String topic, String payload, Instant createdAt) {
    public static final int MAX_TOPIC_LENGTH = 128;

    public Message {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(topic, "topic must not be null");
        Objects.requireNonNull(payload, "payload must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        if (topic.isBlank() || topic.length() > MAX_TOPIC_LENGTH) {
            throw new IllegalArgumentException("topic must be non-blank and at most 128 characters");
        }
    }

    public static Message of(String topic, String payload) {
        return new Message(UUID.randomUUID(), topic, payload, Instant.now());
    }
}
