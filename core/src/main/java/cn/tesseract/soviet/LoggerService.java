package cn.tesseract.soviet;

/**
 * SPI for logging implementations used by {@link SovietLogger}.
 */
public interface LoggerService {
    /** Returns true if this provider is usable in the current runtime. */
    boolean isAvailable();

    void i(String tag, String msg);

    void w(String tag, String msg);

    void e(String tag, String msg, Throwable t);
}

