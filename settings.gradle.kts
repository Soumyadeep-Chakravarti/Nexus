// settings.gradle.kts
rootProject.name = "nexus"

// Core Modules
include("router")        // Main routing engine
include("protocol")      // Wire protocol and serialization definition
include("shared")        // Shared utilities, models, and base configuration

// Client Bridges
include("bridge:java")   // Java client adapter
include("bridge:python") // Python client adapter (generation/packaging)
include("bridge:rust")   // Rust client adapter (generation/packaging)

// Tooling & Development
include("cli")           // Command-line tool
include("dashboard")     // Web UI backend/frontend integration
include("bench")         // Performance benchmarks (JMH)
include("examples")      // Example applications
