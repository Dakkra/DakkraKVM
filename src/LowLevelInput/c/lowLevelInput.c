#include <jni.h>
#include <stdio.h>

JNIEXPORT void JNICALL Java_com_dakkra_kvm_input_InputProcessor_print(JNIEnv *env, jobject obj) {
    printf("Hello from the native C land!\n");
}