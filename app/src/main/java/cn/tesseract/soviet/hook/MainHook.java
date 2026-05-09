package cn.tesseract.soviet.hook;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corrodinggames.rts.R$id;
import com.corrodinggames.rts.appFramework.MainMenuActivity;

import cn.tesseract.crosshook.Callback;
import cn.tesseract.crosshook.Hook;

public class MainHook {
    @Hook
    public static void warnAboutBugs(Callback<MainMenuActivity> c) {
        MainMenuActivity activity = c.getThisObject();
        ((Button) activity.findViewById(R$id.startgameButton)).setWidth(900);
        ((Button) activity.findViewById(R$id.menuCustomButton)).setWidth(900);
        ((Button) activity.findViewById(R$id.multiplayerButton)).setWidth(900);
        LinearLayout l1 = (LinearLayout) activity.findViewById(R$id.settingsButton).getParent();
        l1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.topMargin = 0;

        ((Button) activity.findViewById(R$id.settingsButton)).setWidth(450);
        ((Button) activity.findViewById(R$id.modsButton)).setWidth(450);
        ((Button) activity.findViewById(R$id.settingsButton)).setLayoutParams(lp);
        ((Button) activity.findViewById(R$id.modsButton)).setLayoutParams(lp);

        LinearLayout l2 = new LinearLayout(activity);
        ((LinearLayout) l1.getParent()).addView(l2);

        Button b;

        l1.removeView(b = (Button) activity.findViewById(R$id.exitgameButton));
        l2.addView(b, lp);
        b.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("更新日志")
                    .setMessage("这里还什么都没写")
                    .setPositiveButton("确定", (dialog, which) -> {
                    })
                    .show();
        });
        b.setVisibility(View.VISIBLE);
        b.setWidth(450);

        l1.removeView(b = (Button) activity.findViewById(R$id.helpButton));
        l2.addView(b, lp);
        b.setWidth(450);
    }

    @Hook(injector = Hook.TAIL)
    public static void setButtonText(Callback<MainMenuActivity> c) {
        ((Button) c.getThisObject().findViewById(R$id.exitgameButton)).setText("更新日志");
    }
}
