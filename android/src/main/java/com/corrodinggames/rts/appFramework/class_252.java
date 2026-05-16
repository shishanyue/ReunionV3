package com.corrodinggames.rts.appFramework;

import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class class_252 implements class_255 {
    public class_252() {
    }

    public final EGLSurface method_168(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
        try {
            return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
        } catch (IllegalArgumentException e) {
            Log.e("GLSurfaceView", "eglCreateWindowSurface", e);
            return null;
        }
    }


    public final void method_169(EGL10 var1, EGLDisplay var2, EGLSurface var3) {
        var1.eglDestroySurface(var2, var3);
    }
}
