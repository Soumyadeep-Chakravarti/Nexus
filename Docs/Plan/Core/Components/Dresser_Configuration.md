# Dresser — Configuration System

**Mnemonic Name:** Dresser  
**Component Type:** Core System Component  
**Purpose:** Stores and manages all system and module configuration settings, options, and preferences in a centralized, secure, and easily queryable way.

---

## Overview

Dresser is the **central configuration manager** of the system.  
Every module in **Toybox** can query Dresser to retrieve runtime configurations or update settings if authorized.  
It ensures that **system behavior is consistent**, **configurations are secure**, and **changes are propagated safely**.

---

## Responsibilities

1. **Centralized Configuration Storage**  
   - Maintains system-wide and module-specific configuration data.  
   - Supports hierarchical configuration: global, module, and user-level settings.

2. **Dynamic Query and Update**  
   - Modules can **read configurations at runtime**.  
   - Updates can propagate automatically to interested modules without restarting.

3. **Validation and Integrity**  
   - Checks new or updated configurations for **validity, type, and constraints**.  
   - Prevents corrupt or invalid settings from affecting the system.

4. **Persistence**  
   - Stores configurations securely on disk (via **Stash** or internal DB) for persistence.  
   - Supports export/import for backup or migration purposes.

5. **Security & Access Control**  
   - Works with **Gatekeeper** to enforce read/write permissions per module.  
   - Prevents unauthorized modules from changing sensitive settings.

---

## Core Concepts

| Concept          | Description |
|------------------|-------------|
| **Configuration Key**   | Unique identifier for a configuration setting (e.g., `logging.level`). |
| **Configuration Value** | The value associated with a key; can be string, number, boolean, or complex object. |
| **Scope**        | Level at which a configuration applies: global, module, or user-specific. |
| **Subscriber**   | Modules that want to receive notifications when a configuration changes. |
| **Persistence Layer** | Storage backend for saving configurations permanently. |

---

## Interaction with Other Core Components

- **Toybox (Module Manager)**  
  Modules query Dresser to retrieve and update configuration at runtime.  

- **Gatekeeper (Security / Access)**  
  Ensures modules have permission to read/write particular settings.  

- **Stash (Data Store Interface)**  
  Handles the **secure persistence** of configuration data.  

- **Dirty Talk (Event / Messaging)**  
  Broadcasts configuration changes to modules that are subscribed.

---

## Example Workflow

1. Module A starts and queries Dresser for its runtime configuration.  
2. Dresser retrieves values from the persistence layer (Stash) and returns them.  
3. Module A updates a setting `maxConnections = 10`.  
4. Dresser validates the new value, checks permissions via Gatekeeper, and saves it to Stash.  
5. Dresser notifies all subscribed modules of the change via Dirty Talk.  

---

## Design Notes

- Must allow **hot updates** without restarting the system.  
- Should be **thread-safe** to handle multiple concurrent read/write requests.  
- Should support **future extensions**, like dynamic configuration from external sources or cloud sync.  
- Must provide a **clear API** for modules of any language if multi-language support is later implemented.

