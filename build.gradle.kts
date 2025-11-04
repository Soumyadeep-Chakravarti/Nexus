// build.gradle.kts
plugins {
    // Apply essential toolchain/wrapper plugins here

    // Use the protobuf plugin framework to manage FlatBuffers generation
    id("com.google.protobuf") version "0.9.4" apply false
}

allprojects {
    group = "io.nexus"
    version = "0.1.0-SNAPSHOT" // Following Semantic Versioning 2.0.0

    repositories {
        mavenCentral()
    }
}

subprojects {
    // Apply core Java plugins to all modules
    apply(plugin = "java")
    apply(plugin = "java-library")
    
    java {
        // Enforce Java 21 LTS across the project
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
    
    // Configure common testing dependencies (from libs.versions.toml)
    dependencies {
        testImplementation(libs.bundles.testing)
        testImplementation(libs.logback) // For logs during test runs
    }
    
    tasks.test {
        useJUnitPlatform()
        // Run tests in parallel to maximize speed
        maxParallelForks = Runtime.getRuntime().availableProcessors() 
    }
}
