package com.gmail.meeyeer.viinceent.oboetemplate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class StreamInActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {

    private static final String TAG = StreamInActivity.class.getSimpleName();
    private static final int STREAM_IN_REQUEST = 0x6789;
    private TextView statusText;
    private TextView devicesText;
    private boolean isStarted = false;
    private int deviceId = 0;

    private native void native_createStreamIn();
    private native void native_startStreamIn(int deviceId);
    private native void native_stopStreamIn();
    private native void native_closeStreamIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_in);
        statusText = findViewById(R.id.status_view_text);
        devicesText = findViewById(R.id.devices_text);
        devicesText.setMovementMethod(new ScrollingMovementMethod());
        Button refreshButton = findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        refreshDevices();
        native_createStreamIn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRecordPermissionGranted()) {
            requestRecordPermission();
        } else {
            refreshDevices();
            start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        native_stopStreamIn();
    }

    @Override
    protected void onStop() {
        super.onStop();
        native_closeStreamIn();
    }

    private boolean isRecordPermissionGranted() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED);
    }

    private void requestRecordPermission(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                STREAM_IN_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (STREAM_IN_REQUEST != requestCode) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            // User denied the permission, without this we cannot record audio
            // Show a toast and update the status accordingly
            statusText.setText(R.string.status_record_audio_denied);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.need_record_audio_permission),
                    Toast.LENGTH_SHORT)
                    .show();
            stop();

        } else {
            // Permission was granted, start
            start();
        }
    }

    private void start() {
        if (!isStarted) {
            native_startStreamIn(deviceId);
            isStarted = true;
        }
    }

    private void stop() {
        if (isStarted) {
            native_stopStreamIn();
            isStarted = false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_refresh) {
            refreshDevices();
        }
    }

    private void refreshDevices() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        List<AudioDeviceInfo> allAudioDevices = Utils.getAllAudioDevices(this);
        StringBuilder builder = new StringBuilder();
        boolean isFirstSourceFound = false;
        for (AudioDeviceInfo device : allAudioDevices) {
            if (!isFirstSourceFound) {
                deviceId = device.getId();
                isFirstSourceFound = true;
            }
            builder
                    .append(device.isSource() ? "Input" : "Output")
                    .append(" device: ")
                    .append(device.getProductName())
                    .append(" (")
                    .append(Utils.getTypeAsString(device))
                    .append(", id: ")
                    .append(device.getId())
                    .append(")\n");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !device.getAddress().isEmpty()) {
                builder.append("  Location: ")
                        .append(device.getAddress())
                        .append("\n");
            }

            final int[] sampleRates = device.getSampleRates();
            if (sampleRates.length > 0) {
                builder.append("  sampleRates: ")
                    .append(Arrays.toString(sampleRates))
                    .append("\n");
            }

            final List<String> encodingsAsStrings = Utils.getEncodingsAsStrings(device);
            if (!encodingsAsStrings.isEmpty()) {
                builder.append("  encodings: ")
                        .append(TextUtils.join(", ", encodingsAsStrings))
                        .append("\n");
            }

            final int[] channelCounts = device.getChannelCounts();
            if (channelCounts.length > 0) {
                builder.append("  channelCount: ")
                        .append(Arrays.toString(channelCounts))
                        .append("\n");
            }

            final List<String> channelMasksAsStrings = Utils.getChannelMasksAsStrings(device);
            if (!channelMasksAsStrings.isEmpty()) {
                builder.append("  channelMask: ")
                        .append(TextUtils.join(", ", channelMasksAsStrings))
                        .append("\n");
            }

            final int[] channelIndexMasks = device.getChannelIndexMasks();
            if (channelCounts.length > 0) {
                builder.append("  channelIndexMask: ")
                        .append(Arrays.toString(channelIndexMasks))
                        .append("\n");
            }

            builder.append("\n");
        }
        devicesText.setText(builder.toString());
    }
}
