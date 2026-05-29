package cn.tesseract.soviet.desktop.mixin;


import com.corrodinggames.rts.java.Main;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Main.class)
public class TestMixin {
    @Overwrite
    public static void main(String args[]) {

    }
}
