package cn.tesseract.soviet;

import java.lang.reflect.Method;

/** LoggerService that delegates to android.util.Log via reflection when available. */
public final class AndroidLoggerService implements LoggerService {

    private final Class<?> logClass;
    private final Method iMethod;
    private final Method wMethod;
    private final Method eMethod;

    public AndroidLoggerService() {
        Class<?> cls = null;
        Method im = null, wm = null, em = null;
        try {
            cls = Class.forName("android.util.Log");
            im = cls.getMethod("i", String.class, String.class);
            wm = cls.getMethod("w", String.class, String.class);
            em = cls.getMethod("e", String.class, String.class, Throwable.class);
        } catch (Throwable t) {
            cls = null; im = wm = em = null;
        }
        logClass = cls; iMethod = im; wMethod = wm; eMethod = em;
    }

    @Override
    public boolean isAvailable() { return logClass != null; }

    @Override
    public void i(String tag, String msg) {
        try { iMethod.invoke(null, tag, msg); } catch (Throwable ignored) { }
    }

    @Override
    public void w(String tag, String msg) {
        try { wMethod.invoke(null, tag, msg); } catch (Throwable ignored) { }
    }

    @Override
    public void e(String tag, String msg, Throwable t) {
        try { eMethod.invoke(null, tag, msg, t); } catch (Throwable ignored) { }
    }
}

