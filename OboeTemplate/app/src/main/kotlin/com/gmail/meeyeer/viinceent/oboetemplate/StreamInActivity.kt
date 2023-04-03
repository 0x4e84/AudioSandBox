package com.gmail.meeyeer.viinceent.oboetemplate

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import com.gmail.meeyeer.viinceent.oboetemplate.databinding.ActivityStreamInBinding

class StreamInActivity :
    AppCompatActivity(),
    OnRequestPermissionsResultCallback,
    View.OnClickListener {
    private lateinit var binding: ActivityStreamInBinding
    private var isStarted = false
    private var deviceId = 0
    private external fun native_createStreamIn()
    private external fun native_startStreamIn(deviceId: Int)
    private external fun native_stopStreamIn()
    private external fun native_closeStreamIn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreamInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.devicesText.movementMethod = ScrollingMovementMethod()
        findViewById<Button>(R.id.button_refresh)
        binding.buttonRefresh.setOnClickListener(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        refreshDevices()
        native_createStreamIn()
    }

    override fun onResume() {
        super.onResume()
        if (!isRecordPermissionGranted) {
            requestRecordPermission()
        } else {
            refreshDevices()
            start()
        }
    }

    override fun onPause() {
        super.onPause()
        native_stopStreamIn()
    }

    override fun onStop() {
        super.onStop()
        native_closeStreamIn()
    }

    private val isRecordPermissionGranted: Boolean
        get() = ActivityCompat.checkSelfPermission(this, RECORD_AUDIO) == PERMISSION_GRANTED

    private fun requestRecordPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(RECORD_AUDIO),
            STREAM_IN_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (STREAM_IN_REQUEST != requestCode) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.size != 1 || grantResults[0] != PERMISSION_GRANTED) {

            // User denied the permission, without this we cannot record audio
            // Show a toast and update the status accordingly
            binding.statusViewText.setText(R.string.status_record_audio_denied)
            Toast.makeText(
                applicationContext,
                getString(R.string.need_record_audio_permission),
                Toast.LENGTH_SHORT
            )
                .show()
            stop()
        } else {
            // Permission was granted, start
            start()
        }
    }

    private fun start() {
        if (!isStarted) {
            native_startStreamIn(deviceId)
            isStarted = true
        }
    }

    private fun stop() {
        if (isStarted) {
            native_stopStreamIn()
            isStarted = false
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_refresh) {
            refreshDevices()
        }
    }

    private fun refreshDevices() {
        val allAudioDevices = Utils.getAllAudioDevices(this)
        val builder = StringBuilder()
        var isFirstSourceFound = false
        for (device in allAudioDevices) {
            if (!isFirstSourceFound) {
                deviceId = device.id
                isFirstSourceFound = true
            }
            builder
                .append(if (device.isSource) "Input" else "Output")
                .append(" device: ")
                .append(device.productName)
                .append(" (")
                .append(Utils.getTypeAsString(device))
                .append(", id: ")
                .append(device.id)
                .append(")\n")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && device.address.isNotEmpty()) {
                builder.append("  Location: ")
                    .append(device.address)
                    .append("\n")
            }
            val sampleRates = device.sampleRates
            if (sampleRates.isNotEmpty()) {
                builder.append("  sampleRates: ")
                    .append(sampleRates.contentToString())
                    .append("\n")
            }
            val encodingsAsStrings = Utils.getEncodingsAsStrings(device)
            if (encodingsAsStrings.isNotEmpty()) {
                builder.append("  encodings: ")
                    .append(TextUtils.join(", ", encodingsAsStrings))
                    .append("\n")
            }
            val channelCounts = device.channelCounts
            if (channelCounts.isNotEmpty()) {
                builder.append("  channelCount: ")
                    .append(channelCounts.contentToString())
                    .append("\n")
            }
            val channelMasksAsStrings = Utils.getChannelMasksAsStrings(device)
            if (channelMasksAsStrings.isNotEmpty()) {
                builder.append("  channelMask: ")
                    .append(TextUtils.join(", ", channelMasksAsStrings))
                    .append("\n")
            }
            val channelIndexMasks = device.channelIndexMasks
            if (channelCounts.isNotEmpty()) {
                builder.append("  channelIndexMask: ")
                    .append(channelIndexMasks.contentToString())
                    .append("\n")
            }
            builder.append("\n")
        }
        binding.devicesText.text = builder.toString()
    }

    companion object {
        private const val STREAM_IN_REQUEST = 0x6789
    }
}