package cn.tesseract.soviet.android.mixin;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.corrodinggames.rts.appFramework.IntroScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntroScreen.class)
public abstract class TestMixin extends Activity {
    @Inject(method = "onCreate", at = @At("HEAD"))
    public void onCreate(Bundle bundle, CallbackInfo ci) {
        Toast.makeText(this, "aa", Toast.LENGTH_LONG).show();
    }
}
