#include "nexus_adapter.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Initialize JVM and Nexus instance safely
JNIEnv* nexus_init(JavaVM **jvm, jobject *nexusInstance) {
    JNIEnv *env = NULL;
    JavaVMInitArgs vm_args;
    JavaVMOption options[1];

    // Set the classpath to where your compiled Nexus class is
    char classpathOption[512];
    snprintf(classpathOption, sizeof(classpathOption),
         "-Djava.class.path=%s", getenv("CLASSPATH"));
    options[0].optionString = classpathOption;
    vm_args.version = JNI_VERSION_21; // or JNI_VERSION_20 if your JDK is older
    vm_args.nOptions = 1;
    vm_args.options = options;
    vm_args.ignoreUnrecognized = 0;

    // Create JVM
    if (JNI_CreateJavaVM(jvm, (void**)&env, &vm_args) != 0) {
        fprintf(stderr, "Failed to create JVM\n");
        exit(1);
    }

    if (!env) {
        fprintf(stderr, "JNIEnv is NULL after JVM creation\n");
        (**jvm)->DestroyJavaVM(*jvm);
        exit(1);
    }

    // Find Nexus class
    jclass cls = (*env)->FindClass(env, "com/nexus/core/Nexus");
    if (!cls) {
        fprintf(stderr, "Failed to find class com/nexus/core/Nexus\n");
        (**jvm)->DestroyJavaVM(*jvm);
        exit(1);
    }

    // Get constructor
    jmethodID constructor = (*env)->GetMethodID(env, cls, "<init>", "()V");
    if (!constructor) {
        fprintf(stderr, "Failed to get Nexus constructor\n");
        (**jvm)->DestroyJavaVM(*jvm);
        exit(1);
    }

    // Create Nexus object
    *nexusInstance = (*env)->NewObject(env, cls, constructor);
    if (!*nexusInstance) {
        fprintf(stderr, "Failed to instantiate Nexus object\n");
        (**jvm)->DestroyJavaVM(*jvm);
        exit(1);
    }

    return env;
}

// Send a message to Nexus
void nexus_send(JNIEnv *env, jobject nexusInstance, const char *msg) {
    if (!env || !nexusInstance || !msg) return;

    jclass cls = (*env)->GetObjectClass(env, nexusInstance);
    jmethodID sendMethod = (*env)->GetMethodID(env, cls, "send", "(Ljava/lang/String;)V");
    if (!sendMethod) {
        fprintf(stderr, "Failed to find send method\n");
        return;
    }

    jstring jmsg = (*env)->NewStringUTF(env, msg);
    (*env)->CallVoidMethod(env, nexusInstance, sendMethod, jmsg);
    (*env)->DeleteLocalRef(env, jmsg); // clean up local ref
}

// Receive a message from Nexus
char* nexus_receive(JNIEnv *env, jobject nexusInstance) {
    if (!env || !nexusInstance) return NULL;

    jclass cls = (*env)->GetObjectClass(env, nexusInstance);
    jmethodID receiveMethod = (*env)->GetMethodID(env, cls, "receive", "()Ljava/lang/String;");
    if (!receiveMethod) {
        fprintf(stderr, "Failed to find receive method\n");
        return NULL;
    }

    jstring received = (jstring)(*env)->CallObjectMethod(env, nexusInstance, receiveMethod);
    if (!received) return NULL;

    const char *str = (*env)->GetStringUTFChars(env, received, 0);
    char *copy = strdup(str);
    (*env)->ReleaseStringUTFChars(env, received, str);
    (*env)->DeleteLocalRef(env, received);

    return copy;
}
