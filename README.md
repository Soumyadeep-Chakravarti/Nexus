# Nexus

**A bounded in-process message router for polyglot systems.**

---

## Quick Overview

Nexus is a standalone product extracted from the COBALT architecture (Layer 3). It solves the N² complexity problem in polyglot projects by acting as a central message router where each language implements ONE adapter to connect.

### The Problem
- Compiled languages need matching ABIs
- Compiled ↔ non-compiled integration is painful
- Each language pair needs custom integration (N² complexity)
- No universal standard for connecting Python, Java, Rust, C++, Kotlin, etc.

### The Solution
Nexus acts as a **central message router/hub** where:
- Each language implements ONE adapter to Nexus (not N-1 adapters)
- Languages act as peer nodes, sending/receiving through the router
- Any language can join the network, even future languages
- Multiple messaging patterns: RPC, pub/sub, streaming, async

---

## Current Foundation

- Java 21 with a thread-safe, in-process topic router
- Immutable message envelopes with IDs and creation timestamps
- Explicit bounded subscriber mailboxes and non-blocking backpressure reports
- A small CLI demonstration and JUnit coverage of routing contracts

Nexus does not yet provide a network protocol, RPC, a schema registry, or
working language adapters. The existing C/JNI and Python examples are legacy
prototypes and are not part of the active build surface.

---

## Project Status

**Phase**: Foundation

The first released boundary is the in-process routing API. Local IPC and
language adapters will build on this contract rather than define their own
queues or message semantics.

---

## Development

The Nix shell supplies JDK 21, Gradle, direnv, and repository tooling.

```bash
direnv allow
./gradlew test
./gradlew run
```

---

## Key Features (Planned)

### Next Steps
- Local IPC transport with explicit framing and lifecycle rules
- Language adapters backed by the transport contract
- Schema/version registry for message payloads
- Observability and routing metrics

### Future Enhancements
- Service mesh integration (Kubernetes/Istio)
- WebAssembly support
- Smart routing with circuit breakers
- Streaming primitives
- Plugin architecture
- Multi-datacenter support
- Web Dashboard - Real-time message flow visualization

---

## Target Market

- DevOps teams managing polyglot microservice architectures
- Platform engineers building language-agnostic orchestration tools
- Systems integrators connecting heterogeneous technology stacks

---

## Relationship to COBALT

Nexus is the first product extracted from the [COBALT architecture](https://github.com/Soumyadeep-Chakravarti/Project-COBALT/), specifically Layer 3 (The Nexus Layer). Once mature as a standalone product, it will be re-integrated as the official Layer 3 implementation within the full COBALT system.

---

## License

Nexus is licensed under the [MIT License](LICENSE).
