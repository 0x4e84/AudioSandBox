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
class Oscillator
{
public:
    //==============================================================================
    Oscillator()
    {
        // <- 1.10. get a reference to the Oscillator with processorChain.get<>()
        auto& oscillator = processorChain.template get<0>();
        // <- 1.11. initialise the Oscillator with a 128-point sine wave lookup table
        oscillator.initialise([](Type x) { return std::sin(x); }, 128);
        // <- 2.1. initialise the Oscillator with a 2-point sawtooth wave (i.e. a
        //         linear ramp from -1 to 1) lookup table
    }

    //==============================================================================
    void setFrequency(Type newValue, bool force = false)
    {
        ignoreUnused(newValue);
        ignoreUnused(force);

        // <- 1.12. get a reference to the Oscillator with processorChain.get<>()
        auto& oscillator = processorChain.template get<oscIndex>();
        // <- 1.13. set the frequency of the Oscillator
        oscillator.setFrequency(newValue, force);
    }

    //==============================================================================
    void setLevel(Type newValue)
    {
        ignoreUnused(newValue);

        // <- 1.14. get a reference to the Gain with processorChain.get<>()
        auto& gain = processorChain.template get<gainIndex>();
        // <- 1.15. set the gain of the Gain here
        gain.setGainLinear(newValue);
    }

    //==============================================================================
    void reset() noexcept
    {
        // <- 1.9. reset the processorChain
        processorChain.reset();
    }

    //==============================================================================
    template <typename ProcessContext>
    void process(const ProcessContext& context) noexcept
    {
        auto&& outBlock = context.getOutputBlock();
        const auto numSamples = outBlock.getNumSamples();

        ignoreUnused(numSamples);

        // <- 1.16. create an AudioBlock with the correct size (numSamples)
        //          from tempBlock using getSubBlock()
        auto blockToUse = tempBlock.getSubBlock(0, numSamples);
        // <- 1.17. create a juce::dsp::ProcessContextReplacing from the
        //          AudioBlock above
        auto contextToUse = juce::dsp::ProcessContextReplacing<Type>(blockToUse);
        // <- 1.18. process processorChain with the above context
        processorChain.process(contextToUse);
        // <- 1.19. add the results to outBlock
        outBlock.add(blockToUse);
    }

    //==============================================================================
    void prepare(const juce::dsp::ProcessSpec& spec)
    {
        ignoreUnused(spec);

        // <- 1.7. prepare() the processorChain
        processorChain.prepare(spec);
        // <- 1.8. Create a new tempBlock here with spec.numChannels channels
        //         and spec.maximumBlockSize samples. Pass the HeapBlock declared
        //         below to the constructor, which will handle memory allocation.
        tempBlock = juce::dsp::AudioBlock<Type>(heapBlock, spec.numChannels, spec.maximumBlockSize);
    }

private:
    //==============================================================================
    enum
    {
        // <- 1.3. add Oscillator index
        oscIndex,
        // <- 1.4. add Gain index
        gainIndex
    };

    juce::dsp::ProcessorChain<
        // <- 1.1. add a juce::dsp::Oscillator
        juce::dsp::Oscillator<Type>,
        // <- 1.2. add a juce::dsp::Gain
        juce::dsp::Gain<Type>
    > processorChain;

    // <- 1.5. declare a juce::HeapBlock<char>
    juce::HeapBlock<char> heapBlock;
    // <- 1.6. declare a juce::dsp::AudioBlock named tempBlock
    juce::dsp::AudioBlock<Type> tempBlock;
};
