Workshops
=

Jules Stroller: "Optimising graphics performance"
-


When calling Component::repaint(), expect the OS to repaint what you request, but eventually also a larger area around it (e.g. a larger rectangle rather than a union of smaller areas).

Don't use Graphics::drawLine for drawing... vertical or horizontal lines! It will be blury, because it will be not snapped onto a pixel boundary. Use it for diagonals and use path segments instead for vertical and horizontallines!
If you draw a rectangle with integer coordinates, it will systematically span on 2 pixels and be blury.

Save and restore state: use it when e.g. drawing something, doing a rotation and popping back to the original state. Otherwise, avoid it because it can be quite expensive.

Affine transforms: Adding transforms will NOT slow you down, because the system will do that anyway.

Think about using OpenGL for graphics on Android phones: potentially huge gains, since Android phones have a large amount of pixels and no CoreGraphics.

JUCE will be moving from OpenGL to Vulkan in the near future. Benefit: les verbose, compatible with all platforms.


Ivan Cohen: "Designing digital effects with state-of-the-art DSP and audio filter algorithms"
-


- [Link to code material](https://www.dropbox.com/s/wkjdlqsv9972pk6/ADC18DSPW.zip?dl=0)
- Ivan Cohen: <ivan.cohen@musicalentropy.com>, Zsol Garamvolgyl: <zsolt@roli.com>



RC Low pas filter with cut-off frequency: f0 = 1/(2*Pi"R*C)
H(s) = 1 / (1+s/w0)
Laplace operator s -> jw
w0 = 2*pi*f0

Z-transform:
z -> e^(j*w*Ts)
unitary delay:
H(z) = z^-1

Generic transfer function:
H(z) = b0 + b1*z<sup>-1</sup> + b2*z<sup>-2</sup> / a0 + a1*z<sup>-1</sup> + a2*z<sup>-2</sup>

Bilinear transform:
s -> 2/Ts * (1 - z<sup>-1</sup>) / (1 + z<sup>-1</sup>)



Frequency warping:
The Taylor series approximation shows its limits when the frequency gets closer to the Nyquist frequency, even causing the cut-off frequency to be shifted...
We can compensate for the latter effect by using a new transform s -> w0 / tan(w0*Ts/2) (1 - z^-1) / (1 + z^-1) 


Nyquist frequency clamping:
- Oversampling (but adds latency: we avoid it in use cases where we need linear operation)


=> Chosing any of the methods and adding a filter stage afterwards (e.g. MZT-i: Matched Z-transform)



Ballistic filters:
- Note. new in Juce: ADSR: for creating enveloppes
- VU-Meter typically uses ballistic filters




Session 5:
- Use LinearSmoothedValue for smoothing all control values (volume control,...)!!
- But use a Slew Limiter to limite the slope of a continuously varying parameter.



Adapting filter coefficients to the input:
- LFO
- Enveloppe followers


State variable filter: same effect as tradistional LP/HP filters but with the added possibility of modulating  the filters



Adding nonlinearities (e.g. tanh) to an analog filter can lead to structures that can not be done in the digital form because the output depends on the input AND the output (at time N, not N-1)!!
=> implicit...

Solution: Newton-Raphson algorithm
e.g. for doing proper diode clipper simulation

Hence the marketing term "zero-delay filter", wrongly used to indicate use of the Newton-Raphson technique.

/!\ finding a good "initial value" for the N-R algorithm is very critical (see article by B. Holmes: "Improving the robustness of the iterative solver in state-space modelling of guitar distortion circuitry")



Oversampling (N times):
- Add (N-1) zeros between every sample
- Low-pass filter to remove introduced artefacts
- If non-linaearity are introduced, Cut everything above original Nyquist frequency
- Downsample by taking 1 sample every N samples
[There is a library for this in JUCE!]



Talks
=

Ricardo Garcia (Google): "The new dynamics processing effect (DPE) in Android Open Source Project"
-


Effects in Android (AOSP):
- Built-in: 5-band eq, loudness enhancer, reverberation
- 3rd-party can add their own effects (e.g. Dolby, Fraunhofer) AND encouraged to do so!


Architecture:
- The N channels are linked (e.g. when a channel is compressed because it's very loud, all channels are compressed and taken down simultaneously, e.g to preserve the stereo image)
- sessionId: handle created from the mediaplayer (track, stream...), that you pass to the processing effect
- Cut-off frequency and gain in dB for each band of the equalizer
- Multi-band compressor
- preGain AND postGain
- limiter


Hearing loss compensation?
* Adding a Hearing Loss profile in accessibility settings for correcting on-the-fly (for streaming)?
* Sound amplifier: makes it easier to understand conversations in loud environments, noice reduction, 
* Analysis of hearing thresholds, environments, dimensionality reduction and principal component analysis => best parameters for sound amplifier
* Open for developers: come up with your own mapping!! (Google already in contact with reSound)
* Latency: depends of the frame size (e.g. 10ms)
* Adds only minimal CPU power consumption, working with manufacturers to get some DSP optimization 
* Anchor point: 90dB SPL @1kHz corresponds to ~-22.3 dBFS


Ricardo Garcia: 
- from Colombia
- E-mail: <rago@google.com>
- Twitter: [@ragomusic](https://twitter.com/ragomusic)
- Android Developer Reference: [android.media.audiofx](https://developer.android.com/reference/android/media/audiofx/package-summary)


=> Google is opening the door for 3rd-party manufacturers to create their own mappers for the settings in DPE. Ricardo: "Because YOU know what the needs of your users are!"





Adobe talk:
"From jack of all trades to audio professional. How algorithms and interaction design can help to improve audio editing"
-

on Premiere Rush CC

Gaussian Mixture Model/Hidden Markov Model
Analyzing audio in slices of 25ms at a time to classify video content (e.g. music/speech)
HMM: state over stream of slices



Ben supper: "How's your spatialisation algorithm?"
-

Localization as a function of frequency:
- Below 1.5kHz: interaural time difference
- Above 1.5kHz: interaural level difference
- In the 1 to 3 kHz band, localization doesn't work very well (issue e.g. for Hi-Fi loudpseakers)

152mm between ears (~440us)
With a head in between: ~600 to 700 us

ITU-R-5-point scale from imperceptible to Very annoying


Antidote to "crap research":
- Make stimuli complete and consistent 
- Ask listeners if they're uncomfortable/frustrated/in difficulty
- Interview outliers instead of ignoring them


Pete Brown: "Update on Windows for pro-audio and music application developers"
-

- USB Audio Class 2
- BLE Midi support in W10/JUCE (W10 1809)
- Low-Latency WASAPI (Apps must opt into low-latency through API calls)
- Per-monitor high-DPI support from JUCE 5.4.x

Revenue share model for non-game apps (coming soon):
- 85% of the revenues when people find out about the app in the store
- 95% when the developer sends the user directly to the store


Gaze/eye tracking supported in 1809 for completely hands-free interaction! (e.g. w/ Tobi tracking)



Vadim Zavalishin: "The art of VA filter design – A different kind of digital filter theory"
-

Refers to the freely available book of the same name ([v2.1.0 recently released](https://go.skimresources.com/?id=25446X847199&xs=1&isjs=1&url=https%3A%2F%2Fwww.native-instruments.com%2Ffileadmin%2Fni_media%2Fdownloads%2Fpdf%2FVAFilterDesign_2.1.0.pdf&xguid=01CWMPAFBWED72H6KNDX1M2SHW&xuuid=c2a9369b478dfff8121d03415fe5407b&xsessid=&xcreo=0&xed=0&sref=https%3A%2F%2Fwww.kvraudio.com%2Fforum%2Fviewtopic.php%3Ft%3D350246&xtz=-60&jv=13.12.1-stackpath&bv=2.5.1)). 

VA = Virtual Analog

Frequency-shifter filter: Double-Landen?

Elliptical filter?

- [Will Pirkle: Designing Software Synthesizer Plugins in C++](http://www.willpirkle.com/about/books/)
- [Stephen Schoen: An introduction to Digital Filters](https://discourse-cdn-sjc2.com/.../0cae34973f55ca7eb6890f31c3168be44f6fc9d7.pdf)



Marina Bosi Keynote: "How perceptual audio coding has shaped our lives"
-

- Marina Bosi:
	- [Stanford profile](https://ccrma.stanford.edu/people/marina-bosi)
	- Worked with/for Renzo Piano
- Sound perception theory based on the work by Eberhard Zwicker (e.g. book [Psychoacoustics](https://www.springer.com/de/book/9783662095621))
- Spectral Band Replication (SBR):
	- Only the low part of the signal is waveform-encoded
	- Used for MP3Pro, AAC+, E-AC-3 (DD+), AC-4, MPEG-D ASAC, MPEG-H


Aurelius Prochazka: "Democratization of audio development"
-

AudioKit framework: Open-Source project
"We're running out of synthesizers to emulate!": don't reproduce the problems of the past by trying to copy the original interface (e.g. skeumorphism, sliders disguised as knobs, connected racks as UI...)
Microtonality?


Phil Nash: "Exceptional low-latency C++"
-

- [[nodiscard]] flag (new in C++17)
- ADT-based error handling (Algebraic Data Types)
	- std::optional<T> : returns a value or nothing at all
	- std::variant<T1, T2>
	- std::get_if
	- std::expected<T, E> (coming in C++20 or use boost)
	- combination of "operator |" and std::expect<T, E>
- Herb Sutter's proposal for C++ 23: "Zero-overhead deterministic exceptions: Throwing values"


Amy Dickens: "The UX of audio experiences"
-

Satvik Venkatesh: "Brain-computer music interface systems by using JUCE"
- 

SSVEP Steady State Visual Evoked Potential: Zones of the screen flash at different frequencies. When the user is gazing at a flashing square on a screen, one can measure from his brain activity which square is focused on by measuring the frequency of the stimulus (e.g. 6Hz, 6.6Hz,...). For practical reasons, a sinus modulation is chosen rather than a square wave (deals better with 50 or 60Hz refresh rate).

Don Turner: "Winning on Android"
- 

- Galaxy S9 models now have a round-trip latency of ~20ms
- When an AudioStream is requested w/ Exclusive mode, you get it only if the device has at least 4 channels (2 are required for the system, so at least two are free for the app). If the app is backgrounded and another app requests Exclusive mode, it can be that our stream gets restarted in Shared mode.


- Use Developer branch of Juce and rebuild the Projucer 
- clone the Oboe repository and indicate the path into the juce_audio_basics
- arm64-v8a
- Debugging: use systrace.py --time=5  (from command line?)
- Attach the profiler (restarts the app)

Debugging
- Use a LatencyTuner to automatically adjust the buffer size whenever an underrun happens and tuner->tune()
- Specify a maximum buffer size. A safe maximum would be 4 times the burst size.


- Keep stream running at any team the app should be ready to produce audio, because the start time introduces a large latency.




Posters
=

- Jorge Garcia: "DSP Prototyping with Python"
- Andreas Franck: "The VISR Framework - Component-based audio processing in C++ and Python"
- Chirp <https://chirp.io>


Websites
=

- SOUL: SOUnd Language - <https://soul-lang.org>
- Chirp: <https://chirp.io>
- KVR Audio (Audio plug-in community) forum: <https://www.kvraudio.com/forum/>
- [Open-Source Tracktion engine](https://www.tracktion.com/develop/tracktion-engine)
- [Open-Source AudioKit engine](https://audiokit.io/) (on iOS and Mac only!)
- [Open-Source Virtual instruments with HISE](http://hise.audio/)
- [Elk: the Music Operating System](https://www.mindmusiclabs.com/)


Contacts
=

- Ricardo Garcia: <rago@google.com>
- HKU (Netherlands):
	- Marc Groenewegen: <marc.groenewegen@hku.nl>
	- Harold Groenenboom: <haroldgroenenboom@gmail.com>