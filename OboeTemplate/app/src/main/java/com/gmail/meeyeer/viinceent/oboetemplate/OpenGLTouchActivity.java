package com.gmail.meeyeer.viinceent.oboetemplate;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

public class OpenGLTouchActivity extends AppCompatActivity {

    private native void native_createOboeTouchClap(AssetManager assetManager);
    private native void native_startOboeSound();
    private native void native_stopOboeSound();
    private native void native_closeOboeSound();
    private native boolean native_isOboeSoundExclusive();

    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_touch);
        statusText = findViewById(R.id.statusText);
        statusText.setMovementMethod(new ScrollingMovementMethod());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        native_createOboeTouchClap(getAssets());
    }

    @Override
    protected void onResume() {
        super.onResume();
        native_startOboeSound();
        boolean isExclusive = native_isOboeSoundExclusive();
        statusText.setText(isExclusive ? "Exclusive mode" : "Shared mode");
    }

    @Override
    protected void onPause() {
        super.onPause();
        native_stopOboeSound();
    }

    @Override
    protected void onStop() {
        super.onStop();
        native_closeOboeSound();
    }
}
