package cn.tesseract.patcher;

import java.util.Map;

public interface Patch {
    String name();
    void init(Map<String, Object> context);
    byte[] transform(String className, byte[] classBytes);
}
