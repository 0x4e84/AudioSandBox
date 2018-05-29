package com.gmail.meeyeer.viinceent.oboetemplate;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class OpenGLTouchActivity extends AppCompatActivity {

    private native void native_createOboeTouchClap(AssetManager assetManager);
    private native void native_startOboeSound();
    private native void native_stopOboeSound();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_touch);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        native_createOboeTouchClap(getAssets());
    }

    @Override
    protected void onResume() {
        super.onResume();
        native_startOboeSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        native_stopOboeSound();
    }
}
