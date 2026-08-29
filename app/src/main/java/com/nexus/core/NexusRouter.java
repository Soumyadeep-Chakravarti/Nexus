package com.nexus.core;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread-safe, in-process topic router with bounded subscriber mailboxes.
 *
 * <p>Publishing never blocks: a full mailbox increments the report's dropped count instead.
 */
public final class NexusRouter {
    private final Map<String, CopyOnWriteArrayList<MailboxSubscription>> subscribers =
            new ConcurrentHashMap<>();

    public Subscription subscribe(String topic, int capacity) {
        validateTopic(topic);
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }

        var subscription = new MailboxSubscription(topic, capacity);
        subscribers.computeIfAbsent(topic, ignored -> new CopyOnWriteArrayList<>()).add(subscription);
        return subscription;
    }

    public DeliveryReport publish(Message message) {
        var topicSubscribers = subscribers.getOrDefault(message.topic(), new CopyOnWriteArrayList<>());
        int delivered = 0;
        int dropped = 0;
        for (var subscriber : topicSubscribers) {
            if (subscriber.offer(message)) {
                delivered++;
            } else {
                dropped++;
            }
        }
        return new DeliveryReport(delivered, dropped);
    }

    private static void validateTopic(String topic) {
        if (topic == null || topic.isBlank() || topic.length() > Message.MAX_TOPIC_LENGTH) {
            throw new IllegalArgumentException("topic must be non-blank and at most 128 characters");
        }
    }

    private final class MailboxSubscription implements Subscription {
        private final String topic;
        private final ArrayBlockingQueue<Message> mailbox;
        private final AtomicBoolean closed = new AtomicBoolean();

        private MailboxSubscription(String topic, int capacity) {
            this.topic = topic;
            mailbox = new ArrayBlockingQueue<>(capacity);
        }

        @Override
        public String topic() {
            return topic;
        }

        @Override
        public Optional<Message> poll() {
            return Optional.ofNullable(mailbox.poll());
        }

        @Override
        public void close() {
            if (closed.compareAndSet(false, true)) {
                subscribers.computeIfPresent(topic, (ignored, topicSubscribers) -> {
                    topicSubscribers.remove(this);
                    return topicSubscribers.isEmpty() ? null : topicSubscribers;
                });
                mailbox.clear();
            }
        }

        private boolean offer(Message message) {
            return !closed.get() && mailbox.offer(message);
        }
    }
}
