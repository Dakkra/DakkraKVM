#include <iostream>
#include <jni.h>

extern "C"{
#include "lowLevelInput.h"
}

JNIEXPORT void JNICALL Java_com_dakkra_kvm_input_InputProcessor_print(JNIEnv *env, jobject obj) {
    std::cout << "Now we're using c++ instead of c! Much better" << std::endl;
}