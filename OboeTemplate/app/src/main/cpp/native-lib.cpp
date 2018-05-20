#include <jni.h>
#include <string>

static int32_t touchCounter = 0;

extern "C" JNIEXPORT jstring JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Touch interactions: " + std::to_string(touchCounter);
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLSurfaceView_native_1onTouchInput(JNIEnv *env,
                                                                                     jclass type,
                                                                                     jint eventType,
                                                                                     jlong timeSinceBootMs,
                                                                                     jint pixel_x,
                                                                                     jint pixel_y) {
    touchCounter++;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLSurfaceView_native_1surfaceDestroyed(
        JNIEnv *env, jclass type) {

    // TODO

}

extern "C"
JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_RendererWrapper_native_1onSurfaceCreated(JNIEnv *env,
                                                                                       jclass type) {

    // TODO

}

extern "C"
JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_RendererWrapper_native_1onSurfaceChanged(JNIEnv *env,
                                                                                       jclass type,
                                                                                       jint widthInPixels,
                                                                                       jint heightInPixels) {

    // TODO

}

extern "C"
JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_RendererWrapper_native_1onDrawFrame(JNIEnv *env,
                                                                                  jclass type) {

    // TODO

}