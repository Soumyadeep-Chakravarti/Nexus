package com.nexus.cli;

import com.nexus.core.Message;
import com.nexus.core.NexusRouter;

/** Minimal runnable demonstration of the in-process routing contract. */
public final class NexusCli {
    private NexusCli() {}

    public static void main(String[] args) {
        var router = new NexusRouter();
        try (var subscription = router.subscribe("demo", 8)) {
            var report = router.publish(Message.of("demo", "Nexus is ready"));
            var message = subscription.poll().orElseThrow();
            System.out.printf("%s: %s (%d delivered, %d dropped)%n", message.topic(), message.payload(), report.delivered(), report.dropped());
        }
    }
}
