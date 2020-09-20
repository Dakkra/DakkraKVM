#include <iostream>
#include <jni.h>

#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__) || defined(__NT__)

#include <Windows.h>

#define USE_OS_WIN
//https://stackoverflow.com/questions/29734263/creating-global-keyboard-hook
#elif __APPLE__
#include <ApplicationServices/ApplicationServices.h>
#define USE_OS_MAC
//https://github.com/mjolnirapp/mjolnir/issues/9
#endif

extern "C" {
#include "lowLevelInput.h"
}

class JInputProcessor {
public:
    JInputProcessor(JNIEnv *env, jclass classtype, jobject objectid, jmethodID key_down_mthid, jmethodID key_up_mthid)
            : env(env), classtype(classtype), objectid(objectid), key_down_mthid(key_down_mthid),
              key_up_mthid(key_up_mthid) {}

    void key_down(jint code) {
        (*env).CallVoidMethod(objectid, key_down_mthid, code);
    }

    void key_up(jint code) {
        (*env).CallVoidMethod(objectid, key_up_mthid, code);
    }

private:
    JNIEnv *env;
    jclass classtype;
    jobject objectid;
    jmethodID key_down_mthid;
    jmethodID key_up_mthid;
};

JInputProcessor *input_processor;

//WINDOWS HOOK
#ifdef USE_OS_WIN
__declspec(dllexport) LRESULT CALLBACK HookProcedure(int code, WPARAM wParam, LPARAM lParam) {
    DWORD SHIFT_key = 0;
    DWORD CTRL_key = 0;
    DWORD ALT_key = 0;

    if ((code == HC_ACTION) && ((wParam == WM_SYSKEYDOWN) || (wParam == WM_KEYDOWN))) {
        KBDLLHOOKSTRUCT hooked_key = *((KBDLLHOOKSTRUCT *) lParam);
        DWORD dwMsg = 1;
        dwMsg += hooked_key.scanCode << 16;
        dwMsg += hooked_key.flags << 24;
        char lpszKeyName[1024] = {0};

        int i = GetKeyNameText(dwMsg, (lpszKeyName + 1), 0xFF) + 1;

        int key = hooked_key.vkCode;

        SHIFT_key = GetAsyncKeyState(VK_SHIFT);
        CTRL_key = GetAsyncKeyState(VK_CONTROL);
        ALT_key = GetAsyncKeyState(VK_MENU);

        printf("Keycode = %c\n", key);
        if (input_processor) input_processor->key_down(key);
    }

    return CallNextHookEx(0, code, wParam, lParam);
}

void MessageLoop() {
    MSG message;
    while (GetMessage(&message, nullptr, 0, 0)) {
        TranslateMessage(&message);
        DispatchMessage(&message);
    }
}

DWORD WINAPI windows_keyprocess(LPVOID lpParm) {
    HINSTANCE hInstance = GetModuleHandle(nullptr);
    HHOOK hKeyboardHook = SetWindowsHookEx(WH_KEYBOARD_LL, (HOOKPROC) HookProcedure, hInstance, NULL);
    MessageLoop();
    UnhookWindowsHookEx(hKeyboardHook);
    return 0;
}
#endif

//Build our pointer to the input processor
JNIEXPORT void JNICALL Java_com_dakkra_kvm_input_InputProcessor_register_key_listener(JNIEnv *env, jobject obj) {
    jclass thisclass = (*env).GetObjectClass(obj);
    jmethodID cbid0 = (*env).GetMethodID(thisclass, "native_key_down", "(I)V");
    jmethodID cbid1 = (*env).GetMethodID(thisclass, "native_key_up", "(I)V");
    input_processor = new JInputProcessor(env, thisclass, obj, cbid0, cbid1);

#ifdef USE_OS_WIN
    HANDLE hThread;
    DWORD dwThread;

    hThread = CreateThread(NULL, NULL, (LPTHREAD_START_ROUTINE) windows_keyprocess, 0, NULL, &dwThread);
    if (hThread) WaitForSingleObject(hThread, INFINITE);
#endif
}
