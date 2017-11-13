/*
==============================================================================

This file is part of the ADC 2017 DSP Workshop demo project.
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

#pragma once

template <typename Type>
class Distortion
{
public:
    //==============================================================================
    Distortion()
    {
        // <- 7.5. get a reference to the Waveshaper with processorChain.get<>()
        auto& waveshaper = processorChain.template get<waveshaperIndex>();
        // <- 7.6. initialise the Waveshaper to a hard clipper, which limits the
        //         input signal to the [-0.1, 0.1] range
        // waveshaper.functionToUse = [](Type x) { return jlimit(Type(-.1), Type(.1), x); };

        // <- 8.5. change the Waveshaper's transfer curve to hyperbolic tangent
        waveshaper.functionToUse = [](Type x) { return std::tanh(x); };
        // <- 8.6. get a reference to the pre-gain with processorChain.get<>()
        auto& preGain = processorChain.template get<preGainIndex>();
        // <- 8.7. set the pre-gain to 30 dB
        preGain.setGainDecibels(30.0f);

        // <- 8.8. get a reference to the post-gain with processorChain.get<>()
        auto& postGain = processorChain.template get<postGainIndex>();
        // <- 8.9. set the post-gain to -20 dB
        // postGain.setGainDecibels(-20.0f);

        // <- 10.8. set the post-gain to 0 dB after the CabSimulator has 
        //          been integrated
        postGain.setGainDecibels(0.0f);
    }

    //==============================================================================
    void prepare(const juce::dsp::ProcessSpec& spec)
    {
        // <- 9.3. get a reference to the filter ProcessorDuplicator with
        //         processorChain.get<>()
        auto& filter = processorChain.template get<filterIndex>();

        // <- 9.4 assign a new set of coefficients to the ProcessorDuplicator's
        //        state using juce::dsp::IIR::Coefficients::makeFirstOrderHighPass();
        //        the cutoff frequency should be 1 kHz
        filter.state = FilterCoefs::makeFirstOrderHighPass(spec.sampleRate, 1e3f);

        // <- 7.4. prepare the processorChain
        processorChain.prepare(spec);

        ignoreUnused(spec);
    }

    //==============================================================================
    template <typename ProcessContext>
    void process(const ProcessContext& context) noexcept
    {
        // <- 7.7. process the processorChain with the given context
        processorChain.process(context);

        ignoreUnused(context);
    }

    //==============================================================================
    void reset() noexcept
    {
        // <- 7.3. reset the processorChain
        processorChain.reset();
    }

private:
    //==============================================================================
    enum
    {
        // <- 9.2. add Filter index
        filterIndex,
        // <- 8.3. add pre-gain index
        preGainIndex,
        // <- 7.2. add Waveshaper index
        waveshaperIndex,
        // <- 8.4. add post-gain index
        postGainIndex
    };

    using Filter = juce::dsp::IIR::Filter<Type>;
    using FilterCoefs = juce::dsp::IIR::Coefficients<Type>;

    juce::dsp::ProcessorChain<
        // <- 9.1. add a multi-channel IIR filter using juce::dsp::ProcessorDuplicator,
        //         Filter and FilterCoefs
        // (that's required to work on a stereo signal)
        juce::dsp::ProcessorDuplicator<Filter, FilterCoefs>,
        // <- 8.1. add a juce::dsp::Gain<Type>
        juce::dsp::Gain<Type>,
        // <- 7.1. add a juce::dsp::WaveShaper
        juce::dsp::WaveShaper<Type>,
        // <- 8.2. add a juce::dsp::Gain<Type>
        juce::dsp::Gain<Type>
    > processorChain;
};
