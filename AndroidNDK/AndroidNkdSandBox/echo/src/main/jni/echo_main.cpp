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
#include <android/log.h>
#include "echo_main.h"

#include <cstring>
#include <howie.h>
#include <queue>

const char * MODULE_NAME="ECHO";
const int MAX_ECHO_DELAY = 1000;
struct EchoState {
    std::queue<char*> *echoQueue_;
    char*             buf_;
};

struct EchoParms {
    int delaySamples_;
    float attenuation_;
    float wetDryMix_;
};

EchoParms echoParameters;

int sampleRate;
int channelCount;
int framesPerPeriod;

int bytesPerSample;
int readPosition = 0;

int writePosition = 0;
int delay = 1000;
int bufferSize;
int bufferSamples;

short *echoBuffer;

HowieError OnDeviceChangedCallback(
        const HowieDeviceCharacteristics *characteristics,
        const HowieBuffer *state,
        const HowieBuffer *params) {

    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "OnDeviceChangedCallback: %s",
                        __FUNCTION__);

    EchoState *echoState = reinterpret_cast<EchoState*>(state->data);
    echoState->echoQueue_ = new std::queue<char*>;
    EchoParms *echoParms;
    echoParms = reinterpret_cast<EchoParms*>(params->data);
    echoParms->attenuation_ = 0.5f;
    echoParms->wetDryMix_ = 0.5f;
    delay = 200;

    echoParameters.attenuation_ = 0.5f;
    echoParameters.wetDryMix_ = 0.5f;
    echoParameters.delaySamples_ = (sampleRate * delay) / 1000;;

    sampleRate = characteristics->sampleRate;
    framesPerPeriod = characteristics->framesPerPeriod;
    bytesPerSample = characteristics->bytesPerSample;
    channelCount = characteristics->channelCount;

    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "Sample rate: %i, Frames per period: %i, Bytes per sample: %i, Channel count: %i, delay: %i",
                        sampleRate,
                        framesPerPeriod,
                        bytesPerSample,
                        channelCount,
                        delay);

    bufferSamples  = (sampleRate * MAX_ECHO_DELAY) / 1000;
    bufferSamples -= bufferSamples % framesPerPeriod;
    bufferSize = bufferSamples * bytesPerSample * channelCount;

    initialize();

    int bytesPerBuf = bytesPerSample *
                      channelCount *
                      framesPerPeriod;

    // Allocate one contiguous memory chunk
    echoState->buf_ = new char [bufferSize];
    echoBuffer = new short[bufferSamples];

    // init memory with silent audio
    memset(echoState->buf_, 0, (size_t) bufferSize);
    memset(echoBuffer, 0, (size_t) bufferSamples);

    // use memory as small buffers
    int offset = 0;
    while (offset < bufferSize) {
        echoState->echoQueue_->push(&echoState->buf_[offset]);
        offset += bytesPerBuf;
    }

    return HOWIE_SUCCESS;
}

void initialize() {
    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "initialize(): %s",
                        __FUNCTION__);

    echoParameters.delaySamples_ = (sampleRate * delay) / 1000;

    writePosition = 0;
    readPosition = (writePosition - echoParameters.delaySamples_ + bufferSamples) % bufferSamples;

    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "Read position: %i, Delay Samples: %i, Buffer size: %i",
                        readPosition,
                        echoParameters.delaySamples_,
                        bufferSize);
}

HowieError OnCleanupCallback(
        const HowieStream *stream,
        const HowieBuffer *state ) {

    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "OnCleanupCallback: %s",
                        __FUNCTION__);

    EchoState *echoState = reinterpret_cast<EchoState*>(state->data);
    delete echoState->echoQueue_;
    delete [] echoState->buf_;
    return HOWIE_SUCCESS;
}

HowieError OnProcessCallback(
        const HowieStream *stream,
        const HowieBuffer *input,
        const HowieBuffer *output,
        const HowieBuffer *state,
        const HowieBuffer *params)
{
    EchoState *echoState = reinterpret_cast<EchoState*>(state->data);
    EchoParms *echoParms = reinterpret_cast<EchoParms*>(params->data);
    char* echoBuf = echoState->echoQueue_->front();
    short *echo = reinterpret_cast<short*>(echoBuf);
    short *src = reinterpret_cast<short*>(input->data);
    short *dst = reinterpret_cast<short*>(output->data);

    int samples = output->byteCount / 2;

    int delaySamples = echoParms->delaySamples_;
    float factor = 1.0f - echoParms->attenuation_;
    float wet = echoParms->wetDryMix_;
    float dry = 1.0f - wet;

//    __android_log_print(ANDROID_LOG_INFO,
//                        MODULE_NAME,
//                        "readPosition: %i, writePosition: %i, wet/dry: %f, factor: %f",
//                        readPosition,
//                        writePosition,
//                        wet,
//                        factor);
    while (samples--) {
        *dst++ = (*src + *(echoBuffer + readPosition) * factor);
        *(echoBuffer + writePosition) = (*src + *(echoBuffer + readPosition) * factor * wet);

        src++;
        writePosition = (writePosition + 1) % bufferSamples;
        readPosition = (writePosition - delaySamples + bufferSamples) % bufferSamples;

    }
    echoState->echoQueue_->pop();
    echoState->echoQueue_->push(echoBuf);
    return HOWIE_SUCCESS;
}

JNIEXPORT jlong JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_createStream(
        JNIEnv *env,
        jobject instance) {
    __android_log_print(ANDROID_LOG_INFO, MODULE_NAME, __FUNCTION__);

    HowieStreamCreationParams hscp = {
            sizeof(HowieStreamCreationParams),
            HOWIE_STREAM_DIRECTION_BOTH,
            OnDeviceChangedCallback,
            OnProcessCallback,
            OnCleanupCallback,
            sizeof(EchoState),
            sizeof(EchoParms),
            HOWIE_STREAM_STATE_STOPPED
    };

    HowieStream *stream = nullptr;
    HowieStreamCreate(&hscp, &stream);
    return reinterpret_cast<jlong>(stream);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_destroyStream(
        JNIEnv *env,
        jobject instance,
        jlong streamId) {
    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "%s: id = %lli",
                        __FUNCTION__,
                        streamId);

    HowieStream* stream = reinterpret_cast<HowieStream*>(streamId);
    HowieStreamDestroy(stream);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_startEcho(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jboolean start) {
    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "%s : %lli",
                        (start? "start" : "stop"),
                        streamId);

    initialize();
    HowieStream* stream = reinterpret_cast<HowieStream*>(streamId);

    if (start == JNI_TRUE) {
//        HowieStreamSendParameters(stream, &echoParameters, sizeof(echoParameters), 5);
        HowieStreamSetState(stream, HOWIE_STREAM_STATE_PLAYING);
    } else {
        HowieStreamSetState(stream, HOWIE_STREAM_STATE_STOPPED);
    }
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_sendEchoDelay(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jint newDelay) {

    __android_log_print(ANDROID_LOG_INFO,
                        MODULE_NAME,
                        "Old delay: %i, New delay: %i (int: %i)",
                        delay,
                        newDelay,
                        (int) newDelay);

    HowieStream *stream = reinterpret_cast<HowieStream *>(streamId);
    delay = newDelay;
    echoParameters.delaySamples_ = (sampleRate * newDelay) / 1000;
    HowieStreamSendParameters(stream, &echoParameters, sizeof(echoParameters), 5);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_sendEchoAttenuation(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jfloat newAttenuation) {
    HowieStream *stream = reinterpret_cast<HowieStream *>(streamId);
    echoParameters.attenuation_ = newAttenuation;
    HowieStreamSendParameters(stream, &echoParameters, sizeof(echoParameters), 5);
}

JNIEXPORT void JNICALL
Java_com_gmail_meeyeer_viinceent_echo_MainActivity_sendEchoWetDryMix(
        JNIEnv *env,
        jobject instance,
        jlong streamId,
        jfloat newWetDryMix) {
    HowieStream* stream = reinterpret_cast<HowieStream*>(streamId);
    echoParameters.wetDryMix_ = newWetDryMix;
    HowieStreamSendParameters(stream, &echoParameters, sizeof(echoParameters), 5);
}