package com.nexus.core;

import java.util.Optional;

/** A bounded per-subscriber mailbox. Closing a subscription stops future delivery. */
public interface Subscription extends AutoCloseable {
    String topic();

    Optional<Message> poll();

    @Override
    void close();
}
