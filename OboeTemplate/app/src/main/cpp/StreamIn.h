#ifndef OBOETEMPLATE_STREAMIN_H
#define OBOETEMPLATE_STREAMIN_H

#include <oboe/Oboe.h>
#include "audio/Mixer.h"

using namespace oboe;

class StreamIn : public AudioStreamCallback {
public:
    explicit StreamIn();

    void start(int32_t deviceId);
    void stop();
    void close();

private:
    AudioStream *mAudioStreamIn{nullptr};

    DataCallbackResult onAudioReady(AudioStream *audioStream, void *audioData, int32_t numFrames);

    int64_t sampleCount = 0;
};

#endif //OBOETEMPLATE_STREAMIN_H
