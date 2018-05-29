#ifndef OBOETEMPLATE_STREAMIN_H
#define OBOETEMPLATE_STREAMIN_H

#include <oboe/Oboe.h>
#include "audio/Mixer.h"

using namespace oboe;

class StreamIn : public AudioStreamCallback {
public:
    explicit StreamIn();

    void start();
    void stop();

private:
    AudioStream *mAudioStreamIn{nullptr};

    DataCallbackResult onAudioReady(AudioStream *audioStream, void *audioData, int32_t numFrames);
};

#endif //OBOETEMPLATE_STREAMIN_H
