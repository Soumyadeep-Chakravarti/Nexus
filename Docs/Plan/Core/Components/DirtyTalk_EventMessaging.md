# Dirty Talk — Event / Messaging System

**Mnemonic Name:** Dirty Talk  
**Component Type:** Core System Component  
**Purpose:** Provides an internal messaging bus for all modules to communicate asynchronously, securely, and efficiently.

---

## Overview

Dirty Talk is the **communication backbone** of the system.  
Every module in **Toybox** (Module Manager) uses Dirty Talk to **send and receive messages or events** without directly calling each other. This ensures **loose coupling**, **security**, and **centralized control** over internal communications.

---

## Responsibilities

1. **Internal Messaging Bus**  
   - Manages message delivery between modules.  
   - Supports one-to-one, one-to-many, and broadcast messaging.

2. **Asynchronous Communication**  
   - Messages are queued and processed independently, preventing blocking or deadlocks.  
   - Ensures smooth module operation even under high load.

3. **Event Handling**  
   - Allows modules to **subscribe to specific events**.  
   - Handles **publishing, routing, and delivery** of events securely.

4. **Security & Access Control**  
   - Works with **Gatekeeper** to ensure only authorized modules can send or receive certain messages.  
   - Logs all communications for audit and debugging purposes.

5. **Reliability & Fault Tolerance**  
   - Retries or queues messages if the receiving module is temporarily unavailable.  
   - Prevents loss of messages or corruption of internal state.

---

## Core Concepts

| Concept      | Description |
|--------------|-------------|
| **Message**  | A structured payload containing the event type, source, destination(s), and optional data. |
| **Event**    | A type of message indicating that something happened, e.g., "ModuleLoaded", "ConfigChanged". |
| **Subscriber** | A module that registers interest in certain events. |
| **Publisher**  | A module that sends messages/events to the bus. |
| **Bus**        | The central routing system that handles all messages internally. |

---

## Interaction with Other Core Components

- **Toybox (Module Manager)**  
  Modules in Toybox use Dirty Talk to communicate without hard dependencies.  

- **Gatekeeper (Security / Access)**  
  Dirty Talk consults Gatekeeper before delivering messages to ensure permission compliance.  

- **Big Boss (Core Kernel)**  
  Provides system-wide oversight, monitoring bus health and message flow statistics.  

- **Dresser (Configuration)**  
  Dirty Talk uses Dresser for configurable message routing rules, throttling, and logging options.  

---

## Example Workflow

1. **Module A** wants to notify all modules that a new data resource is available.  
2. Module A **publishes** an event `NewResourceAvailable` to Dirty Talk.  
3. Dirty Talk **checks permissions** with Gatekeeper.  
4. Dirty Talk **routes the event** to all subscribed modules (e.g., Module B and Module C).  
5. Modules B and C **receive the event** and act accordingly, without knowing who else is listening.  

---

## Design Notes

- Should be **highly performant**, with minimal latency in message delivery.  
- Must support **future extensions**, like cross-language communication if the system expands beyond Java.  
- Should be **robust and fault-tolerant**, as communication failures can break system flow.

