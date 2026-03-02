#ifndef NEXUS_ADAPTER_H
#define NEXUS_ADAPTER_H

#include <jni.h>

// Initialize JVM and Nexus instance
JNIEnv* nexus_init(JavaVM **jvm, jobject *nexusInstance);

// Send a message to Nexus
void nexus_send(JNIEnv *env, jobject nexusInstance, const char *msg);

// Receive a message from Nexus
char* nexus_receive(JNIEnv *env, jobject nexusInstance);

#endif // NEXUS_ADAPTER_H
