# Toybox (Module Manager)

## Overview

**Toybox** is the system’s **module manager**, responsible for loading, supervising, and interacting with all modules.  
It ensures that each module behaves properly, doesn’t conflict with others, and integrates seamlessly into the system orchestrated by **Big Boss (Core Kernel)**.

Think of Toybox as a **playground supervisor** — modules are the kids, and Toybox makes sure everyone plays nicely, follows rules, and can’t break the system.

---

## Responsibilities

1. **Module Loading**
   - Loads modules at startup or on demand.
   - Supports dynamic module discovery (e.g., scanning a modules directory or a registry).
   - Handles language-agnostic module loading (Java, C, Rust, Python, etc.) via well-defined interfaces.

2. **Module Supervision**
   - Monitors module health and status.
   - Reports module crashes or misbehavior to Big Boss and Gatekeeper.
   - Supports hot-reloading of modules for updates without restarting the entire system.

3. **Dependency Management**
   - Tracks module dependencies and load order.
   - Prevents cyclic dependencies or conflicts between modules.
   - Ensures that dependent modules are available before activating a module.

4. **Module Communication Interface**
   - Provides modules with access to **Dirty Talk (Event / Messaging)**.
   - Ensures modules cannot bypass Toybox for messaging or data access (security and stability).

5. **Lifecycle Hooks**
   - Registers modules with **Domme (Lifecycle Manager)**.
   - Ensures proper start, stop, reload, and shutdown sequences.
   - Supports pre/post hooks for module initialization and cleanup.

6. **Configuration Integration**
   - Provides modules with access to **Dresser (Configuration)** for runtime settings.
   - Ensures that modules can update preferences only if authorized by Gatekeeper.

---

## Interfaces

Toybox exposes APIs for module interaction and management:

- `loadModule(modulePath)`  
  Loads a module from a path or registry.

- `unloadModule(moduleID)`  
  Gracefully stops and unloads a module.

- `restartModule(moduleID)`  
  Restarts a module without affecting others.

- `getModuleStatus(moduleID)`  
  Returns the runtime health, dependencies, and state of a module.

- `listModules()`  
  Returns all loaded modules, their states, and metadata.

- `registerModuleHook(hookType, moduleID, callback)`  
  Registers lifecycle hooks for a module (startup, shutdown, reload, etc.).

---

## Notes for Multi-Language Support

- Modules may be written in different languages, so Toybox should provide:
  - Language-agnostic loading (shared library, JNI, FFI, gRPC, or similar).
  - Standardized module interface specification (init, shutdown, hooks).
  - Safe cross-language communication via Dirty Talk or structured messaging.

---

## Next Steps

- Define the **module interface specification** (init, run, shutdown, hooks).
- Decide how dynamic module loading will work per language (Java `.jar`, C `.so`, Rust `.rlib`, Python `.py`).
- Draft error-handling strategies for module crashes and rogue behavior.

---

> Toybox ensures the system runs smoothly by supervising modules, managing dependencies, and providing a stable execution environment.  
> Without Toybox, modules could break each other or compromise system integrity.

