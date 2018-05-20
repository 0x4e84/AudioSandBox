package com.gmail.meeyeer.viinceent.oboetemplate;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class OpenGLSurfaceView extends GLSurfaceView {

    public static native void native_onTouchInput(int eventType, long timeSinceBootMs, int pixel_x, int pixel_y);
    public static native void native_surfaceDestroyed();
    private final RendererWrapper renderer;

    public OpenGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        renderer = new RendererWrapper();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
    }

    public OpenGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        renderer = new RendererWrapper();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        native_surfaceDestroyed();
        super.surfaceDestroyed(holder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In our case we care about DOWN events.
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                native_onTouchInput(0, e.getEventTime(), (int)e.getX(), (int)e.getY());
                break;
        }
        return true;
    }
}
