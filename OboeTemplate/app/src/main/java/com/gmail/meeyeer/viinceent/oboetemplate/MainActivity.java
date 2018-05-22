package com.gmail.meeyeer.viinceent.oboetemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openGLButton = findViewById(R.id.button_opengl);
        openGLButton.setOnClickListener(this);

        // Example of a call to a native method
        tv = findViewById(R.id.sample_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_opengl:
                startActivity(new Intent(this, OpenGLTouchActivity.class));
                break;
        }
    }
}
