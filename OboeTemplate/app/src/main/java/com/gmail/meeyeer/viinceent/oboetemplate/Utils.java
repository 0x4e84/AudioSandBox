package com.gmail.meeyeer.viinceent.oboetemplate;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.media.AudioManager.GET_DEVICES_ALL;
import static android.media.AudioManager.GET_DEVICES_INPUTS;
import static android.media.AudioManager.GET_DEVICES_OUTPUTS;

final class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    @NonNull
    static List<AudioDeviceInfo> getAllAudioDevices(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                getAudioDevices(context, GET_DEVICES_ALL) : new ArrayList<>();
    }

    @NonNull
    static List<AudioDeviceInfo> getInputAudioDevices(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                getAudioDevices(context, GET_DEVICES_INPUTS) : new ArrayList<>();
    }

    @NonNull
    static List<AudioDeviceInfo> getOutputAudioDevices(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                getAudioDevices(context, GET_DEVICES_OUTPUTS) : new ArrayList<>();
    }

    @NonNull
    private static List<AudioDeviceInfo> getAudioDevices(@NonNull Context context, int flag) {
        AudioManager audioManager = (AudioManager) (context.getSystemService(Context.AUDIO_SERVICE));
        return audioManager == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.M ?
                new ArrayList<>() : Arrays.asList(audioManager.getDevices(flag));
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    static String getTypeAsString(@NonNull AudioDeviceInfo device) {
        switch (device.getType()) {
            case AudioDeviceInfo.TYPE_AUX_LINE: return "Auxiliary line-level";
            case AudioDeviceInfo.TYPE_BLUETOOTH_A2DP: return "Bluetooth device supporting the A2DP profile";
            case AudioDeviceInfo.TYPE_BLUETOOTH_SCO: return "Bluetooth device typically used for telephony (SCO)";
            case AudioDeviceInfo.TYPE_BUILTIN_EARPIECE: return "Built-in Earpiece";
            case AudioDeviceInfo.TYPE_BUILTIN_MIC: return "Built-in Mic";
            case AudioDeviceInfo.TYPE_BUILTIN_SPEAKER: return "Built-in Speaker";
            case AudioDeviceInfo.TYPE_BUS: return "Bus: type-agnostic device used for communication with external audio systems";
            case AudioDeviceInfo.TYPE_DOCK: return "Dock";
            case AudioDeviceInfo.TYPE_FM: return "Audio signals over FM";
            case AudioDeviceInfo.TYPE_FM_TUNER: return "Audio content transmitted over FM";
            case AudioDeviceInfo.TYPE_HDMI: return "HDMI";
            case AudioDeviceInfo.TYPE_HDMI_ARC: return "HDMI Audio Return Channel";
            case AudioDeviceInfo.TYPE_HEARING_AID: return "Hearing Aid";
            case AudioDeviceInfo.TYPE_IP: return "Connected over IP";
            case AudioDeviceInfo.TYPE_LINE_ANALOG: return "Analog Line-level connection";
            case AudioDeviceInfo.TYPE_LINE_DIGITAL: return "Digital Line (e.g. SPDIF)";
            case AudioDeviceInfo.TYPE_TELEPHONY: return "Telephony";
            case AudioDeviceInfo.TYPE_TV_TUNER: return "TV Tuner";
            case AudioDeviceInfo.TYPE_UNKNOWN: return "Unknown";
            case AudioDeviceInfo.TYPE_USB_ACCESSORY: return "USB audio device in accessory mode";
            case AudioDeviceInfo.TYPE_USB_DEVICE: return "USB audio device";
            case AudioDeviceInfo.TYPE_USB_HEADSET: return "USB audio headset";
            case AudioDeviceInfo.TYPE_WIRED_HEADPHONES: return "Wired Headphones";
            case AudioDeviceInfo.TYPE_WIRED_HEADSET: return "Wired Headset";
            default: return "Unknown";
        }
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    static List<String> getEncodingsAsStrings(@NonNull AudioDeviceInfo device) {
        List<String> encodingStrings = new ArrayList<>();
        for (int encoding : device.getEncodings()) {
            switch (encoding) {
                case AudioFormat.ENCODING_AAC_ELD : encodingStrings.add("AAC ELD compressed");
                case AudioFormat.ENCODING_AAC_HE_V1: encodingStrings.add("AAC HE V1 compressed");
                case AudioFormat.ENCODING_AAC_HE_V2 : encodingStrings.add("AAC HE V2 compressed");
                case AudioFormat.ENCODING_AAC_LC : encodingStrings.add("AAC LC compressed");
                case AudioFormat.ENCODING_AAC_XHE : encodingStrings.add("AAC xHE compressed");
                case AudioFormat.ENCODING_AC3: encodingStrings.add("AC-3 compressed");
                case AudioFormat.ENCODING_AC4 : encodingStrings.add("AC-4 sync frame transport format");
                case AudioFormat.ENCODING_DEFAULT: encodingStrings.add("Default audio data format");
                case AudioFormat.ENCODING_DOLBY_TRUEHD : encodingStrings.add("DOLBY TRUEHD compressed");
                case AudioFormat.ENCODING_DTS: encodingStrings.add("DTS compressed");
                case AudioFormat.ENCODING_DTS_HD: encodingStrings.add("DTS HD compressed");
                case AudioFormat.ENCODING_E_AC3: encodingStrings.add("E-AC-3 compressed");
                case AudioFormat.ENCODING_E_AC3_JOC : encodingStrings.add("E-AC-3-JOC compressed");
                case AudioFormat.ENCODING_IEC61937: encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough");
                case AudioFormat.ENCODING_INVALID: encodingStrings.add("Invalid audio data format");
                case AudioFormat.ENCODING_MP3: encodingStrings.add("MP3 compressed");
                case AudioFormat.ENCODING_PCM_16BIT: encodingStrings.add("PCM 16 bit per sample");
                case AudioFormat.ENCODING_PCM_8BIT: encodingStrings.add("PCM 8 bit per sample");
            }
        }
        return encodingStrings;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    static List<String> getChannelMasksAsStrings(@NonNull AudioDeviceInfo device) {
        List<String> channelMasks = new ArrayList<>();
        for (int channelMask : device.getChannelMasks()) {
            if (device.isSource()) {
                switch (channelMask) {
                    case AudioFormat.CHANNEL_INVALID : channelMasks.add("Invalid");
                    case AudioFormat.CHANNEL_IN_BACK  : channelMasks.add("Back");
                    case AudioFormat.CHANNEL_IN_BACK_PROCESSED : channelMasks.add("Back Processed");
                    case AudioFormat.CHANNEL_IN_DEFAULT : channelMasks.add("Default");
                    case AudioFormat.CHANNEL_IN_FRONT : channelMasks.add("Front or Mono");
                    case AudioFormat.CHANNEL_IN_FRONT_PROCESSED : channelMasks.add("Front Processed");
                    case AudioFormat.CHANNEL_IN_LEFT : channelMasks.add("Left");
                    case AudioFormat.CHANNEL_IN_LEFT_PROCESSED : channelMasks.add("Left Processed");
                    case AudioFormat.CHANNEL_IN_PRESSURE : channelMasks.add("Pressure");
                    case AudioFormat.CHANNEL_IN_RIGHT : channelMasks.add("Right");
                    case AudioFormat.CHANNEL_IN_RIGHT_PROCESSED : channelMasks.add("Right Processed");
                    case AudioFormat.CHANNEL_IN_STEREO : channelMasks.add("Stereo");
                    case AudioFormat.CHANNEL_IN_VOICE_DNLINK : channelMasks.add("Voice Downlink");
                    case AudioFormat.CHANNEL_IN_VOICE_UPLINK : channelMasks.add("Voice Uplink");
                    case AudioFormat.CHANNEL_IN_X_AXIS : channelMasks.add("X-Axis");
                    case AudioFormat.CHANNEL_IN_Y_AXIS : channelMasks.add("Y-Axis");
                    case AudioFormat.CHANNEL_IN_Z_AXIS : channelMasks.add("Z-Axis");
                }
            } else {
                switch (channelMask) {
                    case AudioFormat.CHANNEL_INVALID : channelMasks.add("Invalid");
                    case AudioFormat.CHANNEL_OUT_5POINT1 : channelMasks.add("5.1");
//                    case AudioFormat.CHANNEL_OUT_7POINT1 : channelMasks.add("7.1");
                    case AudioFormat.CHANNEL_OUT_7POINT1_SURROUND : channelMasks.add("7.1 Surround");
                    case AudioFormat.CHANNEL_OUT_BACK_CENTER : channelMasks.add("Back Center");
                    case AudioFormat.CHANNEL_OUT_BACK_LEFT : channelMasks.add("Back Left");
                    case AudioFormat.CHANNEL_OUT_BACK_RIGHT : channelMasks.add("Back Right");
                    case AudioFormat.CHANNEL_OUT_DEFAULT : channelMasks.add("Default");
                    case AudioFormat.CHANNEL_OUT_FRONT_CENTER : channelMasks.add("Front Center");
                    case AudioFormat.CHANNEL_OUT_FRONT_LEFT : channelMasks.add("Front Left or Mono");
                    case AudioFormat.CHANNEL_OUT_FRONT_LEFT_OF_CENTER : channelMasks.add("Front Left of Center");
                    case AudioFormat.CHANNEL_OUT_FRONT_RIGHT : channelMasks.add("Front Right");
                    case AudioFormat.CHANNEL_OUT_FRONT_RIGHT_OF_CENTER : channelMasks.add("Front Right of Center");
                    case AudioFormat.CHANNEL_OUT_LOW_FREQUENCY : channelMasks.add("Low Frequency");
                    case AudioFormat.CHANNEL_OUT_QUAD : channelMasks.add("Quad");
                    case AudioFormat.CHANNEL_OUT_SIDE_LEFT : channelMasks.add("Side Left");
                    case AudioFormat.CHANNEL_OUT_SIDE_RIGHT : channelMasks.add("Side Right");
                    case AudioFormat.CHANNEL_OUT_STEREO : channelMasks.add("Stereo");
                    case AudioFormat.CHANNEL_OUT_SURROUND : channelMasks.add("Surround");
                }
            }
        }
        return channelMasks;
    }
}