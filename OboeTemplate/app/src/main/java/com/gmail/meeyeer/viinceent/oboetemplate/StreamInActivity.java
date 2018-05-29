package com.gmail.meeyeer.viinceent.oboetemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class StreamInActivity extends AppCompatActivity {

    private native void native_createStreamIn();
    private native void native_startStreamIn();
    private native void native_stopStreamIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_in);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        native_createStreamIn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        native_startStreamIn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        native_stopStreamIn();
    }
}
