# Domme — Lifecycle Manager

**Mnemonic Name:** Domme  
**Component Type:** Core System Component  
**Purpose:** Handles the start, stop, reload, and update cycle for all modules. Keeps modules “on a leash.”

---

## Overview

Domme is responsible for **managing the entire lifecycle of modules** in the system.  
It ensures that modules are **initialized correctly, run safely, and shut down gracefully**, maintaining system stability and preventing rogue behavior.

---

## Responsibilities

1. **Module Initialization**
   - Loads modules from Toybox.
   - Ensures that all dependencies are satisfied before starting a module.
   - Applies default configuration from Dresser during startup.

2. **Module Execution Control**
   - Starts, pauses, resumes, and stops modules as needed.
   - Coordinates with Gatekeeper to ensure only authorized modules execute certain actions.
   - Monitors module health and performance during runtime.

3. **Module Reload / Hot Swap**
   - Supports reloading modules without shutting down the entire system.
   - Ensures state preservation where applicable.
   - Handles errors gracefully to avoid system crashes.

4. **Update Management**
   - Checks for module updates and safely applies them.
   - Works with Stash to store module state before updates.
   - Ensures backward compatibility and rollback in case of failure.

5. **Dependency Management**
   - Ensures that modules are loaded in the correct order based on dependencies.
   - Notifies dependent modules of changes or reloads in other modules.

---

## Core Concepts

| Concept                 | Description |
|-------------------------|-------------|
| **Module State**        | Tracks whether a module is `initialized`, `running`, `paused`, `stopped`, or `error`. |
| **Lifecycle Events**    | Events such as `onStart`, `onStop`, `onReload`, `onUpdate` that modules can hook into. |
| **Health Monitor**      | Monitors module runtime behavior, memory usage, and execution anomalies. |
| **Hot Swap**            | The ability to replace or reload a module without shutting down the system. |
| **Dependency Graph**    | Maintains the relationship between modules to ensure proper load order. |

---

## Interaction with Other Core Components

- **Toybox (Module Manager)**
  - Domme relies on Toybox to retrieve and manage the list of available modules.
  
- **Gatekeeper (Security / Access)**
  - Verifies permissions before starting, stopping, or reloading modules.
  
- **Dresser (Configuration)**
  - Provides runtime configurations needed during initialization and reload.
  
- **Stash (Data Store Interface)**
  - Preserves module state before stopping or updating, allowing seamless continuation.
  
- **Dirty Talk (Event / Messaging)**
  - Lifecycle events are broadcasted via the messaging system to notify other modules.

---

## Example Workflow

1. Domme receives a command to start Module X.  
2. It checks Module X’s dependencies and retrieves configuration from Dresser.  
3. Gatekeeper confirms that Module X has permission to execute.  
4. Domme initializes the module, registers it with Dirty Talk for messaging, and marks its state as `running`.  
5. If a reload is requested, Domme stores the current state in Stash, stops the module, updates it, and restarts it while restoring the state.  
6. Any failure during the lifecycle is logged, and dependent modules are notified accordingly.

---

## Design Notes

- Must **ensure system stability** under all module operations.  
- Should provide **granular control** over each module’s lifecycle.  
- Must allow **hot swapping** without disrupting unrelated modules.  
- Should integrate tightly with **security, configuration, and messaging** systems for safe operation.

