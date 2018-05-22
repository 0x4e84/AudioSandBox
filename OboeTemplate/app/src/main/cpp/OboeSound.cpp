//
// Created by 11vmeyer on 2018-05-20.
//

#include <thread>

#include "OboeSound.h"
#include "OboeSoundConstants.h"
#include "utils/logging.h"
#include "ui/OpenGLFunctions.h"

OboeSound::OboeSound(AAssetManager *assetManager): mAssetManager(assetManager) {
}

void OboeSound::start() {
    // Load the RAW PCM data files for both the clap sound and backing track into memory.
    mClap = SoundRecording::loadFromAssets(mAssetManager, "CLAP.raw");
    mMixer.addTrack(mClap);

    // Create a builder
    AudioStreamBuilder builder;
    builder.setFormat(AudioFormat::I16);
    builder.setChannelCount(2);
    builder.setSampleRate(kSampleRateHz);
    builder.setCallback(this);
    builder.setPerformanceMode(PerformanceMode::LowLatency);
    builder.setSharingMode(SharingMode::Exclusive);

    Result result = builder.openStream(&mAudioStream);
    if (result != Result::OK){
        LOGE("Failed to open stream. Error: %s", convertToText(result));
    }

    // Reduce stream latency by setting the buffer size to a multiple of the burst size
    mAudioStream->setBufferSizeInFrames(
            mAudioStream->getFramesPerBurst() * kBufferSizeInBursts);

    result = mAudioStream->requestStart();
    if (result != Result::OK){
        LOGE("Failed to start stream. Error: %s", convertToText(result));
    }
}

void OboeSound::stop() {
    Result result = mAudioStream->requestStop();
    if (result != Result::OK){
        LOGE("Failed to stop stream. Error: %s", convertToText(result));
    }
}

void OboeSound::onSurfaceCreated() {

}

void OboeSound::onSurfaceDestroyed() {
    stop();
}

void OboeSound::onSurfaceChanged(int widthInPixels, int heightInPixels) {

}

void OboeSound::tick() {

}

void OboeSound::tap(int64_t eventTimeAsUptime) {
    mClap->setPlaying(true);
}

DataCallbackResult
OboeSound::onAudioReady(AudioStream *oboeStream, void *audioData, int32_t numFrames) {
    mMixer.renderAudio(static_cast<int16_t*>(audioData), numFrames);
    return DataCallbackResult::Continue;
}



