/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#ifndef ECHO_MAIN_H
#define ECHO_MAIN_H

#include <jni.h>

void initialize();

extern "C" {

JNIEXPORT  jlong JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_createStream(
        JNIEnv *env,
        jobject instance);

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_destroyStream(
        JNIEnv *env,
        jobject instance,
        jlong streamId);

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_startEcho(
        JNIEnv *env,
        jobject instance,
        jlong stream,
        jboolean start);

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_sendEchoAttenuation(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jfloat attenuation);

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_sendEchoDelay(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jint delay);

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_sendEchoWetDryMix(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jfloat wetDryMix);

}

#endif //ECHO_MAIN_H
