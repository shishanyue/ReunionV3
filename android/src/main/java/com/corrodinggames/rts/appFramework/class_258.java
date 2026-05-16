package com.corrodinggames.rts.appFramework;

public class class_258 {
    private static final String field_639 = "GLThreadManager";

    public class_258() {
    }

    public synchronized void method_182(class_257 class_257Var) {
        class_257Var.field_617 = true;
        notifyAll();
    }
}
