#include <jni.h>
#include <string>
#include <memory>

#include <android/asset_manager_jni.h>

#include "OboeSound.h"
#include "StreamIn.h"

static int32_t touchCounter = 0;
std::unique_ptr<OboeSound> oboeSound;
std::unique_ptr<StreamIn> streamIn;

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Touch interactions: " + std::to_string(touchCounter);
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLSurfaceView_native_1onTouchInput(JNIEnv *env,
                                                                                     jclass type,
                                                                                     jint eventType,
                                                                                     jlong timeSinceBootMs,
                                                                                     jint pixel_x,
                                                                                     jint pixel_y) {
    touchCounter++;
    oboeSound->tap(timeSinceBootMs);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLSurfaceView_native_1surfaceDestroyed(
        JNIEnv *env, jclass type) {

    oboeSound->onSurfaceDestroyed();
}

JNIEXPORT jboolean JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLTouchActivity_native_1isOboeSoundExclusive(
        JNIEnv *env, jobject instance) {
    return static_cast<jboolean>(oboeSound->isExclusive());
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLTouchActivity_native_1createOboeTouchClap(
        JNIEnv *env,
        jobject instance,
        jobject jAssetManager) {
    AAssetManager *assetManager = AAssetManager_fromJava(env, jAssetManager);
    oboeSound = std::make_unique<OboeSound>(assetManager);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLTouchActivity_native_1startOboeSound(
        JNIEnv *env,
        jobject instance) {
    oboeSound->start();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLTouchActivity_native_1stopOboeSound(
        JNIEnv *env,
        jobject instance) {
    oboeSound->stop();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLTouchActivity_native_1closeOboeSound(
        JNIEnv *env,
        jobject instance) {
    oboeSound->close();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_StreamInActivity_native_1createStreamIn(
        JNIEnv *env,
        jobject instance) {
    streamIn = std::make_unique<StreamIn>();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_StreamInActivity_native_1startStreamIn(
        JNIEnv *env,
        jobject instance,
        jint deviceId) {
    streamIn->start(deviceId);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_StreamInActivity_native_1stopStreamIn(
        JNIEnv *env,
        jobject instance) {
    streamIn->stop();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_StreamInActivity_native_1closeStreamIn(
        JNIEnv *env,
        jobject instance) {
    streamIn->close();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_RendererWrapper_native_1onSurfaceCreated(JNIEnv *env,
                                                                                       jclass type) {

    oboeSound->onSurfaceCreated();
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_RendererWrapper_native_1onSurfaceChanged(JNIEnv *env,
                                                                                       jclass type,
                                                                                       jint widthInPixels,
                                                                                       jint heightInPixels) {

    oboeSound->onSurfaceChanged(widthInPixels, heightInPixels);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_RendererWrapper_native_1onDrawFrame(JNIEnv *env,
                                                                                  jclass type) {

    oboeSound->tick();
}
}