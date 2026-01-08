# Nexus

**Enterprise-Grade Language-Universal Message Router**

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

## Technical Foundation

- **Java 21 LTS** - Virtual threads, modern features, 8 years support
- **Custom binary protocol** - JSON for development → FlatBuffers for production
- **Production targets**: Sub-millisecond latency, 100k+ messages/second
- **Zero single point of failure** - True peer network model

---

## Project Status

**Phase**: Design & Planning  
**Started**: 2025-11-03  
**Current Sprint**: Architecture design and tooling setup

---

## Documentation

- **[Design-Discussion.md](Design-Discussion.md)** - Design decisions, feature roadmap, and planning discussions
- **[Technical-Spec.md](Technical-Spec.md)** - Detailed technical specification and architecture
- **[Project-Structure.md](Project-Structure.md)** - Code structure, tooling choices, and naming conventions

---

## Key Features (Planned)

### Phase 1 Priorities
- ⭐ Web Dashboard - Real-time message flow visualization
- ⭐ Nexus CLI - Command-line tools for testing and tracing
- ⭐ Schema Registry - Centralized type definitions
- ⭐ OpenTelemetry Integration - Built-in observability

### Future Enhancements
- Service mesh integration (Kubernetes/Istio)
- WebAssembly support
- Smart routing with circuit breakers
- Streaming primitives
- Plugin architecture
- Multi-datacenter support

---

## Target Market

- DevOps teams managing polyglot microservice architectures
- Platform engineers building language-agnostic orchestration tools
- Systems integrators connecting heterogeneous technology stacks

---

## Relationship to COBALT

Nexus is the first product extracted from the [COBALT architecture](../COBALT/README.md), specifically Layer 3 (The Nexus Layer). Once mature as a standalone product, it will be re-integrated as the official Layer 3 implementation within the full COBALT system.

---

## Quick Links

- **Repository**: (TBD)
- **Documentation Site**: (TBD)
- **Issue Tracker**: (TBD)

---

**Last Updated**: 2026-01-08
