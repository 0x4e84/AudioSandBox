/*
  ==============================================================================

   This file is part of the JUCE tutorials.
   Copyright (c) 2017 - ROLI Ltd.

   The code included in this file is provided under the terms of the ISC license
   http://www.isc.org/downloads/software-support-policy/isc-license. Permission
   To use, copy, modify, and/or distribute this software for any purpose with or
   without fee is hereby granted provided that the above copyright notice and
   this permission notice appear in all copies.

   THE SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY, AND ALL WARRANTIES,
   WHETHER EXPRESSED OR IMPLIED, INCLUDING MERCHANTABILITY AND FITNESS FOR
   PURPOSE, ARE DISCLAIMED.

  ==============================================================================
*/

/*******************************************************************************
 The block below describes the properties of this PIP. A PIP is a short snippet
 of code that can be read by the Projucer and used to generate a JUCE project.

 BEGIN_JUCE_PIP_METADATA

 name:             SineSynthTutorial
 version:          1.0.0
 vendor:           JUCE
 website:          http://juce.com
 description:      Simple sine synthesiser.

 dependencies:     juce_audio_basics, juce_audio_devices, juce_audio_formats,
                   juce_audio_processors, juce_audio_utils, juce_core,
                   juce_data_structures, juce_events, juce_graphics,
                   juce_gui_basics, juce_gui_extra
 exporters:        xcode_mac, vs2017, linux_make

 type:             Component
 mainClass:        MainContentComponent

 useLocalCopy:     1

 END_JUCE_PIP_METADATA

*******************************************************************************/


#pragma once

class DecibelSlider : public Slider
{
public:
    DecibelSlider() {}

    double getValueFromText(const String& text) override
    {
        auto minusInfinitydB = -100.0;

        auto decibelText = text.upToFirstOccurrenceOf("dB", false, false).trim();    // [1]

        return decibelText.equalsIgnoreCase("-INF") ? minusInfinitydB
            : decibelText.getDoubleValue();  // [2]
    }

    String getTextFromValue(double value) override
    {
        return Decibels::toString(value);
    }

private:
    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR(DecibelSlider)
};

//==============================================================================
class MainContentComponent : public AudioAppComponent
{
public:
    MainContentComponent()
    {
        addAndMakeVisible (frequencySlider);
        frequencySlider.setRange (50.0, 5000.0);
        frequencySlider.setSkewFactorFromMidPoint (500.0); // [4]
        frequencySlider.setValue(currentFrequency, dontSendNotification);  // [6]
        frequencySlider.onValueChange = [this]
        {
            if (currentSampleRate > 0.0)
                updateAngleDelta();
        };

        addAndMakeVisible(dBLevelSlider);
        dBLevelSlider.setRange(-100, -12);
        dBLevelSlider.setTextBoxStyle(Slider::TextBoxRight, false, 100, 20);
        dBLevelSlider.setValue((double)Decibels::gainToDecibels(currentLevel), dontSendNotification);
        dBLevelSlider.onValueChange = [this] 
        { 
            targetLevel = Decibels::decibelsToGain((float)dBLevelSlider.getValue()); 
        };

        setSize (600, 100);
        setAudioChannels (0, 2); // no inputs, two outputs
    }

    ~MainContentComponent() override
    {
        shutdownAudio();
    }

    void resized() override
    {
        frequencySlider.setBounds (10, 10, getWidth() - 20, 20);
        dBLevelSlider.setBounds(10, 40, getWidth() - 20, 20);
    }

    void updateAngleDelta()
    {
        auto cyclesPerSample = frequencySlider.getValue() / currentSampleRate;         // [2]
        angleDelta = cyclesPerSample * 2.0 * MathConstants<double>::pi;                // [3]
    }

    void prepareToPlay (int, double sampleRate) override
    {
        currentSampleRate = sampleRate;
        updateAngleDelta();
    }

    void releaseResources() override {}

    void getNextAudioBlock (const AudioSourceChannelInfo& bufferToFill) override
    {
        auto level = 0.125f;
        auto* leftBuffer  = bufferToFill.buffer->getWritePointer (0, bufferToFill.startSample);
        auto* rightBuffer = bufferToFill.buffer->getWritePointer (1, bufferToFill.startSample);

        auto localTargetFrequency = targetFrequency;

        if (localTargetFrequency != currentFrequency)                                                         // [7]
        {
            auto frequencyIncrement = (localTargetFrequency - currentFrequency) / bufferToFill.numSamples;    // [8]

            for (auto sample = 0; sample < bufferToFill.numSamples; ++sample)
            {
                auto currentSample = (float)std::sin(currentAngle);
                currentFrequency += frequencyIncrement;                                                       // [9]
                updateAngleDelta();                                                                           // [10]
                currentAngle += angleDelta;
                leftBuffer[sample] = currentSample * level;
                rightBuffer[sample] = currentSample * level;
            }

            currentFrequency = localTargetFrequency;
        }
        else                                                                                                  // [11]
        {
            for (auto sample = 0; sample < bufferToFill.numSamples; ++sample)
            {
                auto currentSample = (float)std::sin(currentAngle);
                currentAngle += angleDelta;
                leftBuffer[sample] = currentSample * level;
                rightBuffer[sample] = currentSample * level;
            }
        }

        auto localTargetLevel = targetLevel;
        bufferToFill.buffer->applyGainRamp(bufferToFill.startSample, bufferToFill.numSamples, currentLevel, localTargetLevel);
        currentLevel = localTargetLevel;
    }

private:
    Slider frequencySlider;
    DecibelSlider dBLevelSlider;
    Label decibelLabel;
    double currentSampleRate = 0.0, currentAngle = 0.0, angleDelta = 0.0; // [1]
    double currentFrequency = 500.0, targetFrequency = 500.0; // [5]
    float currentDbLevel = 0.0f;
    float currentLevel = 0.1f, targetLevel = 0.1f;

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR (MainContentComponent)
};
