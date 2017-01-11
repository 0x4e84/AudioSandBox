package com.gmail.meeyeer.viinceent.echo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.howie.HowieEngine;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MAX_DELAY_MS = 1000;

    private long streamId;
    private int delay;
    private float attenuation;
    private float wetDryMix;

    private ToggleButton toggle;
    private TextView tvAttenuation;
    private TextView tvDelay;
    private TextView tvWetDryMix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("echo");
        HowieEngine.init(this);

        toggle = (ToggleButton) findViewById(R.id.toggleEcho);
        final SeekBar seekBarAttenuation = (SeekBar) findViewById(R.id.seekBarAttenuation);
        final SeekBar seekBarDelay = (SeekBar) findViewById(R.id.seekBarDelay);
        final SeekBar seekBarWetDryMix = (SeekBar) findViewById(R.id.seekBarWetDryMix);

        tvAttenuation = (TextView) findViewById(R.id.textViewAttenuation);
        tvDelay = (TextView) findViewById(R.id.textViewDelay);
        tvWetDryMix = (TextView) findViewById(R.id.textViewWetDryMix);

        setAttenuation(seekBarAttenuation.getProgress());
        setWetDryMix(seekBarWetDryMix.getProgress());
        setDelay(seekBarDelay.getProgress());

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                startEcho(streamId, isChecked);
            }
        });

        seekBarAttenuation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setAttenuation(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarDelay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setDelay(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarWetDryMix.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setWetDryMix(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPause() {
        toggle.setChecked(false);
        destroyStream(streamId);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        streamId = createStream();
    }

    public void setDelay(int delayPercentage) {
        delay = delayPercentage * MAX_DELAY_MS / 100;
        tvDelay.setText(String.format(Locale.US, "Delay: %d ms", delay));
        Log.d(TAG, String.format(Locale.US, "Delay: %d", delay));
        sendEchoDelay(streamId, delay);
    }

    public void setAttenuation(int attenuationPercentage) {
        attenuation = attenuationPercentage / 100.0f;
        tvAttenuation.setText(String.format(Locale.US, "Attenuation: %d %%", attenuationPercentage));
        Log.d(TAG, String.format(Locale.US, "Attenuation: %f", attenuation));
        sendEchoAttenuation(streamId, attenuation);
    }

    public void setWetDryMix(int wetDryMixPercentage) {
        wetDryMix = wetDryMixPercentage / 100.0f;
        tvWetDryMix.setText(String.format(Locale.US, "Wet/Dry Mix: %d %% wet", wetDryMixPercentage));
        Log.d(TAG, String.format(Locale.US, "Wet/Dry Mix: %f", wetDryMix));
        sendEchoWetDryMix(streamId, wetDryMix);
    }

    native public long createStream();
    native public void destroyStream(long streamId);
    native public void startEcho(long stream, boolean start);
    native public void sendEchoAttenuation(long stream, float attenuation);
    native public void sendEchoWetDryMix(long stream, float wetDryMix);
    native public void sendEchoDelay(long stream, int delay);
}
