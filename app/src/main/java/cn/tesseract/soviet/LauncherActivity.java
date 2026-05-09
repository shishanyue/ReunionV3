package cn.tesseract.soviet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.corrodinggames.rts.appFramework.IntroScreen;

import cn.tesseract.crosshook.HookRegistry;
import cn.tesseract.soviet.hook.MainHook;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HookRegistry.instance.register(MainHook.class);

        startActivity(new Intent(this, IntroScreen.class));
        finish();
    }
}
