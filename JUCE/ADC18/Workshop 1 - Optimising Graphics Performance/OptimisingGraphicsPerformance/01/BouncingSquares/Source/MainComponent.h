#pragma once

#include "../JuceLibraryCode/JuceHeader.h"

#include <random>

//==============================================================================
// A component that displays a bouncing square
class BouncingSquare   : public Component,
                         private Timer
{
public:
    //==============================================================================
    BouncingSquare (float initialVelocityX, float initialVeloctyY)
        : velocity (initialVelocityX, initialVeloctyY)
    {
        setSize (100, 100);

        // We will update the square's position at 60 Hz
        startTimerHz (60);
    }

    //==============================================================================
    void paint (Graphics& g) override
    {
        g.setColour (Colours::white);
        g.drawRect (getLocalBounds(), 1);

        g.fillRect (square);
    }

private:
    //==============================================================================
    void timerCallback() override
    {
        // Update the square's position
        square += velocity;

        // If the square has gone beyond an edge bounce it

        if (square.getX() < 0.0f)
        {
            square.setX (-square.getX());
            velocity.setX (-velocity.getX());
        }
        else if (square.getRight() > getWidth())
        {
            auto overshoot = square.getRight() - getWidth();
            square.setX (getWidth() - (square.getWidth() + overshoot));
            velocity.setX (-velocity.getX());
        }

        if (square.getY() < 0.0f)
        {
            square.setY (-square.getY());
            velocity.setY (-velocity.getY());
        }
        else if (square.getBottom() > getHeight())
        {
            auto overshoot = square.getBottom() - getHeight();
            square.setY (getHeight() - (square.getHeight() + overshoot));
            velocity.setY (-velocity.getY());
        }

        // Tell the OS to redraw the component
        Rectangle<float> dirtyArea(square);
        square += velocity;
        dirtyArea = dirtyArea.getUnion(square);

        //repaint();
        repaint(dirtyArea.getSmallestIntegerContainer());
    }

    //==============================================================================
    Rectangle<float> square { 8.0f, 8.0f };
    Point<float> velocity;

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR (BouncingSquare)
};

//==============================================================================
// Our main component. This will contain multiple bouncing squares.
class MainComponent   : public Component
{
public:
    //==============================================================================
    MainComponent()
    {
        setSize (800, 800);

        // Get some random numbers to give some variation in our child components.
        std::uniform_real_distribution<float> distribution (0.0f, 1.0f);
        std::mt19937 randomDevice (121234);

        for (int i = 0; i < numRows * numCols; ++i)
            addAndMakeVisible (bouncers.add (new BouncingSquare (distribution (randomDevice),
                                                                 distribution (randomDevice))));

        // Layout our components
        resized();
    }

    ~MainComponent()
    {
    }

    //==============================================================================
    void paint (Graphics&) override
    {
    }

    void resized() override
    {
        int columnWidth = getWidth()  / numCols;
        int rowHeight   = getHeight() / numRows;

        int itemWidth  = columnWidth  - (2 * margin);
        int itemHeight = rowHeight    - (2 * margin);

        for (int i = 0; i < bouncers.size(); ++i)
            bouncers[i]->setBounds (((i % numCols) * columnWidth) + margin,
                                    ((i / numCols) * rowHeight)   + margin,
                                    itemWidth,
                                    itemHeight);
    }

private:
    //==============================================================================
    int numRows = 1, numCols = 1, margin = 4;

    OwnedArray<BouncingSquare> bouncers;

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR (MainComponent)
};
