#include <jni.h>
#include <cstdio>

extern "C" void print(void);

JNIEXPORT void JNICALL Java_com_dakkra_kvm_input_InputProcessor_print(JNIEnv *env, jobject obj) {
    printf("Hello from the native C land!\n");
}