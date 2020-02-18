package com.bsoft.libcommon.commonaop.permission.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class HuaWei implements ISetting {

    @Override
    public Intent getSetting(Context context) {
        Intent intent = new Intent("com.enjoy.lh_permission");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = new ComponentName("com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(componentName);
        return intent;
    }
}
