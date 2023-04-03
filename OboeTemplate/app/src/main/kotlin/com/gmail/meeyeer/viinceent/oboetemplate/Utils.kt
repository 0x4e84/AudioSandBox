package com.gmail.meeyeer.viinceent.oboetemplate

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioDeviceInfo.TYPE_AUX_LINE
import android.media.AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
import android.media.AudioDeviceInfo.TYPE_BLUETOOTH_SCO
import android.media.AudioDeviceInfo.TYPE_BUILTIN_EARPIECE
import android.media.AudioDeviceInfo.TYPE_BUILTIN_MIC
import android.media.AudioDeviceInfo.TYPE_BUILTIN_SPEAKER
import android.media.AudioDeviceInfo.TYPE_BUS
import android.media.AudioDeviceInfo.TYPE_DOCK
import android.media.AudioDeviceInfo.TYPE_FM
import android.media.AudioDeviceInfo.TYPE_FM_TUNER
import android.media.AudioDeviceInfo.TYPE_HDMI
import android.media.AudioDeviceInfo.TYPE_HDMI_ARC
import android.media.AudioDeviceInfo.TYPE_HEARING_AID
import android.media.AudioDeviceInfo.TYPE_IP
import android.media.AudioDeviceInfo.TYPE_LINE_ANALOG
import android.media.AudioDeviceInfo.TYPE_LINE_DIGITAL
import android.media.AudioDeviceInfo.TYPE_TELEPHONY
import android.media.AudioDeviceInfo.TYPE_TV_TUNER
import android.media.AudioDeviceInfo.TYPE_UNKNOWN
import android.media.AudioDeviceInfo.TYPE_USB_ACCESSORY
import android.media.AudioDeviceInfo.TYPE_USB_DEVICE
import android.media.AudioDeviceInfo.TYPE_USB_HEADSET
import android.media.AudioDeviceInfo.TYPE_WIRED_HEADPHONES
import android.media.AudioDeviceInfo.TYPE_WIRED_HEADSET
import android.media.AudioFormat.CHANNEL_INVALID
import android.media.AudioFormat.CHANNEL_IN_BACK
import android.media.AudioFormat.CHANNEL_IN_BACK_PROCESSED
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.CHANNEL_IN_FRONT
import android.media.AudioFormat.CHANNEL_IN_FRONT_PROCESSED
import android.media.AudioFormat.CHANNEL_IN_LEFT
import android.media.AudioFormat.CHANNEL_IN_LEFT_PROCESSED
import android.media.AudioFormat.CHANNEL_IN_PRESSURE
import android.media.AudioFormat.CHANNEL_IN_RIGHT
import android.media.AudioFormat.CHANNEL_IN_RIGHT_PROCESSED
import android.media.AudioFormat.CHANNEL_IN_STEREO
import android.media.AudioFormat.CHANNEL_IN_VOICE_DNLINK
import android.media.AudioFormat.CHANNEL_IN_VOICE_UPLINK
import android.media.AudioFormat.CHANNEL_IN_X_AXIS
import android.media.AudioFormat.CHANNEL_IN_Y_AXIS
import android.media.AudioFormat.CHANNEL_IN_Z_AXIS
import android.media.AudioFormat.CHANNEL_OUT_5POINT1
import android.media.AudioFormat.CHANNEL_OUT_7POINT1_SURROUND
import android.media.AudioFormat.CHANNEL_OUT_BACK_CENTER
import android.media.AudioFormat.CHANNEL_OUT_BACK_LEFT
import android.media.AudioFormat.CHANNEL_OUT_BACK_RIGHT
import android.media.AudioFormat.CHANNEL_OUT_DEFAULT
import android.media.AudioFormat.CHANNEL_OUT_FRONT_CENTER
import android.media.AudioFormat.CHANNEL_OUT_FRONT_LEFT
import android.media.AudioFormat.CHANNEL_OUT_FRONT_LEFT_OF_CENTER
import android.media.AudioFormat.CHANNEL_OUT_FRONT_RIGHT
import android.media.AudioFormat.CHANNEL_OUT_FRONT_RIGHT_OF_CENTER
import android.media.AudioFormat.CHANNEL_OUT_LOW_FREQUENCY
import android.media.AudioFormat.CHANNEL_OUT_QUAD
import android.media.AudioFormat.CHANNEL_OUT_SIDE_LEFT
import android.media.AudioFormat.CHANNEL_OUT_SIDE_RIGHT
import android.media.AudioFormat.CHANNEL_OUT_STEREO
import android.media.AudioFormat.CHANNEL_OUT_SURROUND
import android.media.AudioFormat.ENCODING_AAC_ELD
import android.media.AudioFormat.ENCODING_AAC_HE_V1
import android.media.AudioFormat.ENCODING_AAC_HE_V2
import android.media.AudioFormat.ENCODING_AAC_LC
import android.media.AudioFormat.ENCODING_AAC_XHE
import android.media.AudioFormat.ENCODING_AC3
import android.media.AudioFormat.ENCODING_AC4
import android.media.AudioFormat.ENCODING_DEFAULT
import android.media.AudioFormat.ENCODING_DOLBY_TRUEHD
import android.media.AudioFormat.ENCODING_DTS
import android.media.AudioFormat.ENCODING_DTS_HD
import android.media.AudioFormat.ENCODING_E_AC3
import android.media.AudioFormat.ENCODING_E_AC3_JOC
import android.media.AudioFormat.ENCODING_IEC61937
import android.media.AudioFormat.ENCODING_INVALID
import android.media.AudioFormat.ENCODING_MP3
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioFormat.ENCODING_PCM_8BIT
import android.media.AudioManager

internal object Utils {
    fun getAllAudioDevices(context: Context): List<AudioDeviceInfo> {
        return getAudioDevices(context, AudioManager.GET_DEVICES_ALL)
    }

    fun getInputAudioDevices(context: Context): List<AudioDeviceInfo> {
        return getAudioDevices(context, AudioManager.GET_DEVICES_INPUTS)
    }

    fun getOutputAudioDevices(context: Context): List<AudioDeviceInfo> {
        return getAudioDevices(context, AudioManager.GET_DEVICES_OUTPUTS)
    }

    private fun getAudioDevices(context: Context, flag: Int): List<AudioDeviceInfo> {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return listOf(*audioManager.getDevices(flag))
    }

    fun getTypeAsString(device: AudioDeviceInfo) = when (device.type) {
        TYPE_AUX_LINE -> "Auxiliary line-level"
        TYPE_BLUETOOTH_A2DP -> "Bluetooth device supporting the A2DP profile"
        TYPE_BLUETOOTH_SCO -> "Bluetooth device typically used for telephony (SCO)"
        TYPE_BUILTIN_EARPIECE -> "Built-in Earpiece"
        TYPE_BUILTIN_MIC -> "Built-in Mic"
        TYPE_BUILTIN_SPEAKER -> "Built-in Speaker"
        TYPE_BUS -> "Bus: type-agnostic device used for communication with external audio systems"
        TYPE_DOCK -> "Dock"
        TYPE_FM -> "Audio signals over FM"
        TYPE_FM_TUNER -> "Audio content transmitted over FM"
        TYPE_HDMI -> "HDMI"
        TYPE_HDMI_ARC -> "HDMI Audio Return Channel"
        TYPE_HEARING_AID -> "Hearing Aid"
        TYPE_IP -> "Connected over IP"
        TYPE_LINE_ANALOG -> "Analog Line-level connection"
        TYPE_LINE_DIGITAL -> "Digital Line (e.g. SPDIF)"
        TYPE_TELEPHONY -> "Telephony"
        TYPE_TV_TUNER -> "TV Tuner"
        TYPE_UNKNOWN -> "Unknown"
        TYPE_USB_ACCESSORY -> "USB audio device in accessory mode"
        TYPE_USB_DEVICE -> "USB audio device"
        TYPE_USB_HEADSET -> "USB audio headset"
        TYPE_WIRED_HEADPHONES -> "Wired Headphones"
        TYPE_WIRED_HEADSET -> "Wired Headset"
        else -> "Unknown"
    }

    fun getEncodingsAsStrings(device: AudioDeviceInfo): List<String> {
        val encodingStrings: MutableList<String> = ArrayList()
        for (encoding in device.encodings) {
            when (encoding) {
                ENCODING_AAC_ELD -> {
                    encodingStrings.add("AAC ELD compressed")
                    encodingStrings.add("AAC HE V1 compressed")
                    encodingStrings.add("AAC HE V2 compressed")
                    encodingStrings.add("AAC LC compressed")
                    encodingStrings.add("AAC xHE compressed")
                    encodingStrings.add("AC-3 compressed")
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_AAC_HE_V1 -> {
                    encodingStrings.add("AAC HE V1 compressed")
                    encodingStrings.add("AAC HE V2 compressed")
                    encodingStrings.add("AAC LC compressed")
                    encodingStrings.add("AAC xHE compressed")
                    encodingStrings.add("AC-3 compressed")
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_AAC_HE_V2 -> {
                    encodingStrings.add("AAC HE V2 compressed")
                    encodingStrings.add("AAC LC compressed")
                    encodingStrings.add("AAC xHE compressed")
                    encodingStrings.add("AC-3 compressed")
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_AAC_LC -> {
                    encodingStrings.add("AAC LC compressed")
                    encodingStrings.add("AAC xHE compressed")
                    encodingStrings.add("AC-3 compressed")
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_AAC_XHE -> {
                    encodingStrings.add("AAC xHE compressed")
                    encodingStrings.add("AC-3 compressed")
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_AC3 -> {
                    encodingStrings.add("AC-3 compressed")
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_AC4 -> {
                    encodingStrings.add("AC-4 sync frame transport format")
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_DEFAULT -> {
                    encodingStrings.add("Default audio data format")
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_DOLBY_TRUEHD -> {
                    encodingStrings.add("DOLBY TRUEHD compressed")
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_DTS -> {
                    encodingStrings.add("DTS compressed")
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_DTS_HD -> {
                    encodingStrings.add("DTS HD compressed")
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_E_AC3 -> {
                    encodingStrings.add("E-AC-3 compressed")
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_E_AC3_JOC -> {
                    encodingStrings.add("E-AC-3-JOC compressed")
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_IEC61937 -> {
                    encodingStrings.add("IEC61937 compressed audio wrapped in PCM for HDMI or S/PDIF passthrough")
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_INVALID -> {
                    encodingStrings.add("Invalid audio data format")
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_MP3 -> {
                    encodingStrings.add("MP3 compressed")
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_PCM_16BIT -> {
                    encodingStrings.add("PCM 16 bit per sample")
                    encodingStrings.add("PCM 8 bit per sample")
                }

                ENCODING_PCM_8BIT -> encodingStrings.add("PCM 8 bit per sample")
            }
        }
        return encodingStrings
    }

    fun getChannelMasksAsStrings(device: AudioDeviceInfo): List<String> {
        val channelMasks: MutableList<String> = ArrayList()
        for (channelMask in device.channelMasks) {
            if (device.isSource) {
                when (channelMask) {
                    CHANNEL_INVALID -> {
                        channelMasks.add("Invalid")
                        channelMasks.add("Back")
                        channelMasks.add("Back Processed")
                        channelMasks.add("Default")
                        channelMasks.add("Front or Mono")
                        channelMasks.add("Front Processed")
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_BACK -> {
                        channelMasks.add("Back")
                        channelMasks.add("Back Processed")
                        channelMasks.add("Default")
                        channelMasks.add("Front or Mono")
                        channelMasks.add("Front Processed")
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_BACK_PROCESSED -> {
                        channelMasks.add("Back Processed")
                        channelMasks.add("Default")
                        channelMasks.add("Front or Mono")
                        channelMasks.add("Front Processed")
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_DEFAULT -> {
                        channelMasks.add("Default")
                        channelMasks.add("Front or Mono")
                        channelMasks.add("Front Processed")
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_FRONT -> {
                        channelMasks.add("Front or Mono")
                        channelMasks.add("Front Processed")
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_FRONT_PROCESSED -> {
                        channelMasks.add("Front Processed")
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_LEFT -> {
                        channelMasks.add("Left")
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_LEFT_PROCESSED -> {
                        channelMasks.add("Left Processed")
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_PRESSURE -> {
                        channelMasks.add("Pressure")
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_RIGHT -> {
                        channelMasks.add("Right")
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_RIGHT_PROCESSED -> {
                        channelMasks.add("Right Processed")
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_STEREO -> {
                        channelMasks.add("Stereo")
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_VOICE_DNLINK -> {
                        channelMasks.add("Voice Downlink")
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_VOICE_UPLINK -> {
                        channelMasks.add("Voice Uplink")
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_X_AXIS -> {
                        channelMasks.add("X-Axis")
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_Y_AXIS -> {
                        channelMasks.add("Y-Axis")
                        channelMasks.add("Z-Axis")
                    }

                    CHANNEL_IN_Z_AXIS ->
                        channelMasks.add("Z-Axis")
                }
            } else {
                when (channelMask) {
                    CHANNEL_INVALID -> {
                        channelMasks.add("Invalid")
                        channelMasks.add("5.1")
                        channelMasks.add("7.1 Surround")
                        channelMasks.add("Back Center")
                        channelMasks.add("Back Left")
                        channelMasks.add("Back Right")
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_5POINT1 -> {
                        channelMasks.add("5.1")
                        channelMasks.add("7.1 Surround")
                        channelMasks.add("Back Center")
                        channelMasks.add("Back Left")
                        channelMasks.add("Back Right")
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_7POINT1_SURROUND -> {
                        channelMasks.add("7.1 Surround")
                        channelMasks.add("Back Center")
                        channelMasks.add("Back Left")
                        channelMasks.add("Back Right")
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_BACK_CENTER -> {
                        channelMasks.add("Back Center")
                        channelMasks.add("Back Left")
                        channelMasks.add("Back Right")
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_BACK_LEFT -> {
                        channelMasks.add("Back Left")
                        channelMasks.add("Back Right")
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_BACK_RIGHT -> {
                        channelMasks.add("Back Right")
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_DEFAULT -> {
                        channelMasks.add("Default")
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_FRONT_CENTER -> {
                        channelMasks.add("Front Center")
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_FRONT_LEFT -> {
                        channelMasks.add("Front Left or Mono")
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_FRONT_LEFT_OF_CENTER -> {
                        channelMasks.add("Front Left of Center")
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_FRONT_RIGHT -> {
                        channelMasks.add("Front Right")
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_FRONT_RIGHT_OF_CENTER -> {
                        channelMasks.add("Front Right of Center")
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_LOW_FREQUENCY -> {
                        channelMasks.add("Low Frequency")
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_QUAD -> {
                        channelMasks.add("Quad")
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_SIDE_LEFT -> {
                        channelMasks.add("Side Left")
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_SIDE_RIGHT -> {
                        channelMasks.add("Side Right")
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_STEREO -> {
                        channelMasks.add("Stereo")
                        channelMasks.add("Surround")
                    }

                    CHANNEL_OUT_SURROUND ->
                        channelMasks.add("Surround")
                }
            }
        }
        return channelMasks
    }
}
