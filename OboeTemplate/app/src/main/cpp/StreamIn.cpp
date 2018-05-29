#include <utils/logging.h>
#include "StreamIn.h"
#include "OboeSoundConstants.h"

StreamIn::StreamIn() {
}

void StreamIn::start() {
    // Create a builder
    AudioStreamBuilder builder;
    builder.setDirection(Direction::Input);
    builder.setFormat(AudioFormat::I16);
    builder.setChannelCount(1);
    builder.setSampleRate(kSampleRateHz);
    builder.setCallback(this);
    builder.setPerformanceMode(PerformanceMode::LowLatency);
    builder.setSharingMode(SharingMode::Exclusive);

    Result result = builder.openStream(&mAudioStreamIn);
    if (result != Result::OK){
        LOGE("Failed to open stream. Error: %s", convertToText(result));
    }

    // Reduce stream latency by setting the buffer size to a multiple of the burst size
    mAudioStreamIn->setBufferSizeInFrames(
            mAudioStreamIn->getFramesPerBurst() * kBufferSizeInBursts);

    result = mAudioStreamIn->requestStart();
    if (result != Result::OK){
        LOGE("Failed to start stream. Error: %s", convertToText(result));
    }

    LOGD("Sharing mode: %s", convertToText(mAudioStreamIn->getSharingMode()));
    LOGD("Buffer Capacity: %d frames", mAudioStreamIn->getBufferCapacityInFrames());
}

void StreamIn::stop() {
    Result result = mAudioStreamIn->requestStop();
    if (result != Result::OK){
        LOGE("Failed to stop stream. Error: %s", convertToText(result));
    }
}

DataCallbackResult
StreamIn::onAudioReady(AudioStream *audioStream, void *audioData, int32_t numFrames) {
    LOGD("Data coming in: %d frames", numFrames);
    return DataCallbackResult::Continue;
}
