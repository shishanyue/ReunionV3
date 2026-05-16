package com.corrodinggames.rts.appFramework;

import android.util.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public class class_251 implements class_254 {
    final GLSurfaceViewShared field_609;
    private int field_610;

    public class_251(GLSurfaceViewShared var1) {
        this.field_609 = var1;
        this.field_610 = 12440;
    }

    public final EGLContext method_166(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
        int[] iArr = {this.field_610, this.field_609.mEGLContextClientVersion, 12344};
        EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
        if (this.field_609.mEGLContextClientVersion == 0) {
            iArr = null;
        }
        return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
    }


    public void method_167(EGL10 var1, EGLDisplay var2, EGLContext var3) {
        if (!var1.eglDestroyContext(var2, var3)) {
            Log.e("DefaultContextFactory", "display:" + var2 + " context: " + var3);
            class_256.method_171("eglDestroyContex", var1.eglGetError());
        }
    }
}
