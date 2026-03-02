// File: app/src/main/java/com/nexus/core/Nexus.java
package com.nexus.core;

import java.util.concurrent.LinkedBlockingQueue;

public class Nexus {
    private final LinkedBlockingQueue<String> buffer;

    public Nexus() {
        buffer = new LinkedBlockingQueue<>();
    }

    // Called from C via JNI
    public void send(String msg) {
        buffer.offer(msg);
        System.out.println("Java received message: " + msg);
    }

    // Called from C via JNI
    public String receive() {
        String msg = buffer.poll();
        if (msg == null) {
            return ""; // avoid returning null
        }
        System.out.println("Java sending message: " + msg);
        return msg;
    }
}
