package com.gmail.meeyeer.viinceent.oboetemplate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gmail.meeyeer.viinceent.oboetemplate.databinding.ActivityMainBinding

class MainActivity :
    AppCompatActivity(),
    View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpengl.setOnClickListener(this)
        binding.buttonStreamIn.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        binding.sampleText.text = stringFromJNI()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_opengl -> startActivity(
                Intent(this, OpenGLTouchActivity::class.java))
            R.id.button_stream_in -> startActivity(
                Intent(this, StreamInActivity::class.java))
        }
    }

    external fun stringFromJNI(): String?
}
