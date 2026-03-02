#include "../adapters/c_adapter/nexus_adapter.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
    JavaVM *jvm;
    jobject nexusInstance;
    JNIEnv *env = nexus_init(&jvm, &nexusInstance);

    nexus_send(env, nexusInstance, "Hello from C Adapter");
    char *msg = nexus_receive(env, nexusInstance);
    printf("C Adapter received: %s\n", msg);
    free(msg);

    (*jvm)->DestroyJavaVM(jvm);
    return 0;
}
