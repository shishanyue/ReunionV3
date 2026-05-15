package cn.tesseract.soviet;

import java.util.Iterator;
import java.util.ServiceLoader;

public final class SovietLogger {

    private static final LoggerService SERVICE = loadService();

    private SovietLogger() {}

    public static void i(String tag, String msg) {
        SERVICE.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        SERVICE.w(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        SERVICE.e(tag, msg, t);
    }

    private static LoggerService loadService() {
        ServiceLoader<LoggerService> loader = ServiceLoader.load(LoggerService.class);
        Iterator<LoggerService> it = loader.iterator();
        LoggerService fallback = new ConsoleLoggerService();
        if (it.hasNext()) {
            LoggerService s = it.next();
            if (s != null && s.isAvailable()) return s;
        }
        return fallback;
    }
}
