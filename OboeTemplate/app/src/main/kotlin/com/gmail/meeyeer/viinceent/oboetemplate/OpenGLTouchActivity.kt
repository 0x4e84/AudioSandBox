package com.gmail.meeyeer.viinceent.oboetemplate

import android.content.res.AssetManager
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gmail.meeyeer.viinceent.oboetemplate.databinding.ActivityOpenglTouchBinding

class OpenGLTouchActivity :
    AppCompatActivity() {
    private lateinit var binding: ActivityOpenglTouchBinding
    private external fun native_createOboeTouchClap(assetManager: AssetManager)
    private external fun native_startOboeSound()
    private external fun native_stopOboeSound()
    private external fun native_closeOboeSound()
    private external fun native_isOboeSoundExclusive(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenglTouchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.statusText.movementMethod = ScrollingMovementMethod()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        native_createOboeTouchClap(assets)
    }

    override fun onResume() {
        super.onResume()
        native_startOboeSound()
        val isExclusive = native_isOboeSoundExclusive()
        binding.statusText.text = if (isExclusive) "Exclusive mode" else "Shared mode"
    }

    override fun onPause() {
        super.onPause()
        native_stopOboeSound()
    }

    override fun onStop() {
        super.onStop()
        native_closeOboeSound()
    }
}
