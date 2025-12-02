# Big Boss (Core Kernel)

## Overview

**Big Boss** is the heart of the system. It acts as the **central orchestrator**, controlling the flow of execution, managing the system state, and coordinating all other components. Think of it as the “CEO” of the system — it doesn’t do everything itself but ensures everyone else knows what to do and when.

---

## Responsibilities

1. **Execution Orchestration**
   - Determines the order in which modules run.
   - Handles scheduling of module tasks, including synchronous and asynchronous execution.
   - Coordinates with **Domme (Lifecycle Manager)** to start, stop, reload, and update modules.

2. **System State Management**
   - Maintains the global system state.
   - Tracks module health, resource usage, and runtime metrics.
   - Provides state information to authorized modules and monitoring tools.

3. **Error Handling & Recovery**
   - Detects module failures or unexpected behavior.
   - Decides corrective action: restart module, notify Gatekeeper, or escalate.
   - Works closely with **Gatekeeper (Security / Access)** to isolate rogue modules.

4. **Inter-Module Communication**
   - Interfaces with **Dirty Talk (Event / Messaging)** for internal messaging.
   - Ensures messages between modules are delivered reliably and securely.

5. **Configuration Access**
   - Queries **Dresser (Configuration)** for runtime settings.
   - Provides modules with the configurations they need to function correctly.

6. **Resource Management**
   - Monitors and allocates system resources (CPU, memory, I/O).
   - Enforces resource limits on modules to prevent runaway processes.

---

## Interfaces

Big Boss exposes clean interfaces for other modules and system extensions:

- `startSystem()`  
  Initializes the system, loads Toybox modules, and sets initial state.

- `stopSystem()`  
  Gracefully stops all modules and cleans up resources.

- `restartModule(moduleID)`  
  Stops and restarts a specific module without affecting others.

- `broadcastEvent(event)`  
  Sends system-level events to modules via Dirty Talk.

- `queryState(key)`  
  Returns specific system state information to authorized modules.

---

## Notes for Multi-Language Support

- Big Boss should provide language-agnostic interfaces (e.g., C ABI, gRPC, or JNI) for modules in different languages.  
- All internal data structures should have well-defined serialization formats to allow cross-language communication.  

---

## Next Steps

- Define the **internal architecture** of Big Boss:
  - Core event loop
  - Module registry and lifecycle hooks
  - Error handling policies
  - Resource monitoring
- Draft example pseudo-code for startup and module orchestration.

---

> Big Boss is intentionally simple in its interface but complex in its internal orchestration. Every other module relies on it, so getting this right is crucial for system stability.

