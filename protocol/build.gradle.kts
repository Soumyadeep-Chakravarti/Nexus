// protocol/build.gradle.kts
plugins {
    id("com.google.protobuf")
}

// Ensure the protocol module can access FlatBuffers dependencies
dependencies {
    // For FlatBuffers code generation and runtime API
    implementation(libs.flatbuffers) 
    // Phase 1 (Temporary)
    implementation(libs.jackson.databind) 
}

// ----------------------------------------------------
// Custom task configuration for FlatBuffers generation
// ----------------------------------------------------

protobuf {
    protoc {
        // Define the path to the FlatBuffers Compiler executable
        // NOTE: This assumes 'flatc' is either installed globally 
        // or a local copy is managed, e.g., in a 'tools' directory.
        // For production, you may want to download and cache 'flatc'.
        path = "flatc" // Must be available on system PATH, or provide full path
    }

    // This block tells Gradle where the source files (.fbs) are
    generatedFilesBaseDir = "$projectDir/src/main/generated"

    // Define the source set for the FlatBuffers language
    sourceSets {
        main {
            proto {
                // Point to the schema directory (as defined in Section 1.5)
                srcDir("schema")
            }
        }
    }

    // Define a custom task to run 'flatc' instead of 'protoc'
    tasks {
        named("generateProto") {
            doLast {
                // Execute flatc for each schema file
                fileTree("src/main/proto").files.forEach { schemaFile ->
                    if (schemaFile.extension == "fbs") {
                        exec {
                            commandLine(
                                "flatc",
                                "--java", // Generate Java code
                                "-o", "src/main/generated/java", // Output directory
                                "-I", "schema", // Include path for imports
                                schemaFile.absolutePath
                            )
                        }
                    }
                }
            }
        }
    }
}
