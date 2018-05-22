#include <jni.h>
#include <string>
#include <memory>

#include <android/asset_manager_jni.h>

#include "OboeSound.h"

static int32_t touchCounter = 0;
std::unique_ptr<OboeSound> oboeSound;

extern "C" {

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_oboetemplate_OpenGLTouchActivity_native_1onCreate(JNIEnv *env,
                                                                                   jobject instance,
                                                                                   jobject jAssetManager) {
    AAssetManager *assetManager = AAssetManager_fromJava(env, jAssetManager);
    oboeSound = std::make_unique<OboeSound>(assetManager);
    oboeSound->start();
}

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