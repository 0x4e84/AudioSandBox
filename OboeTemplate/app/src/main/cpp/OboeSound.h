#ifndef OBOETEMPLATE_OBOESOUND_H
#define OBOETEMPLATE_OBOESOUND_H

#include <android/asset_manager.h>
#include <oboe/Oboe.h>
#include "audio/SoundRecording.h"
#include "audio/Mixer.h"

using namespace oboe;

class OboeSound : public AudioStreamCallback {
public:
    explicit OboeSound(AAssetManager *assetManager);

    void start();
    void stop();
    void close();
    void onSurfaceCreated();
    void onSurfaceDestroyed();
    void onSurfaceChanged(int widthInPixels, int heightInPixels);
    void tick();
    void tap(int64_t eventTimeAsUptime);
    bool isExclusive();
    int64_t getBufferSizeInFrames();

    // Inherited from oboe::AudioStreamCallback
    DataCallbackResult
    onAudioReady(AudioStream *oboeStream, void *audioData, int32_t numFrames) override;

private:
    AAssetManager *mAssetManager{nullptr};
    AudioStream *mAudioStream{nullptr};
    SoundRecording *mClap{nullptr};
    Mixer mMixer;
    bool mIsExclusive = false;
    int64_t mBufferCapacityInFrames = 0;
};


#endif //OBOETEMPLATE_OBOESOUND_H
