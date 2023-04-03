package com.gmail.meeyeer.viinceent.oboetemplate

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder

class OpenGLSurfaceView : GLSurfaceView {
    private val renderer: RendererWrapper

    constructor(context: Context?) : super(context) {
        setEGLContextClientVersion(2)
        renderer = RendererWrapper()
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setEGLContextClientVersion(2)
        renderer = RendererWrapper()
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        native_surfaceDestroyed()
        super.surfaceDestroyed(holder)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In our case we care about DOWN events.
        when (e.action) {
            MotionEvent.ACTION_DOWN -> //                native_onTouchInput(0, e.getEventTime(), (int)e.getX(), (int)e.getY());
                native_onTouchDown(0, e.eventTime, e.x.toInt(), e.y.toInt())

            MotionEvent.ACTION_MOVE -> native_onTouchMove(0, e.eventTime, e.x.toInt(), e.y.toInt())
            MotionEvent.ACTION_UP -> native_onTouchUp(0, e.eventTime, e.x.toInt(), e.y.toInt())
        }
        return true
    }

    companion object {
        external fun native_onTouchInput(
            eventType: Int,
            timeSinceBootMs: Long,
            pixel_x: Int,
            pixel_y: Int
        )

        external fun native_onTouchDown(
            eventType: Int,
            timeSinceBootMs: Long,
            pixel_x: Int,
            pixel_y: Int
        )

        external fun native_onTouchMove(
            eventType: Int,
            timeSinceBootMs: Long,
            pixel_x: Int,
            pixel_y: Int
        )

        external fun native_onTouchUp(
            eventType: Int,
            timeSinceBootMs: Long,
            pixel_x: Int,
            pixel_y: Int
        )

        external fun native_surfaceDestroyed()
    }
}