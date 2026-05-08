package cn.tesseract.soviet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.corrodinggames.rts.appFramework.IntroScreen;

/**
 * Entry point that runs before the game launches.
 * Place any pre-launch logic here.
 */
public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- Pre-launch logic ---
        // TODO: insert your initialization code here

        startActivity(new Intent(this, IntroScreen.class));
        finish();
    }
}
