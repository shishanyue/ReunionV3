package cn.tesseract.soviet;

/** Simple console logger implementation. Always available. */
public final class ConsoleLoggerService implements LoggerService {

    @Override
    public boolean isAvailable() { return true; }

    @Override
    public void i(String tag, String msg) { System.out.println("[Soviet/" + tag + "] " + msg); }

    @Override
    public void w(String tag, String msg) { System.out.println("[Soviet/" + tag + "] WARN: " + msg); }

    @Override
    public void e(String tag, String msg, Throwable t) {
        System.out.println("[Soviet/" + tag + "] ERROR: " + msg);
        if (t != null) t.printStackTrace(System.out);
    }
}

