package com.bsoft.libcommon.commonaop.permission.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class ViVo implements ISetting {

    @Override
    public Intent getSetting(Context context) {
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if (appIntent != null && Build.VERSION.SDK_INT < 23) {
            context.startActivity(appIntent);
            return null;
        }
        Intent vIntent = new Intent();
        vIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        vIntent.setAction(Settings.ACTION_SETTINGS);
        return vIntent;
    }
}
