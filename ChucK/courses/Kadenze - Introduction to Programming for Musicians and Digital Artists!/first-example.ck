SinOsc sinOscillator => dac;

while (true)
{
    220 => sinOscillator.freq;
    .1::second => now;

    Math.random2f(30,1000) => sinOscillator.freq;
    .1::second => now;

    880 => sinOscillator.freq;
    .2::second => now;
}