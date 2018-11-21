#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_orego_battlecrane_BActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
