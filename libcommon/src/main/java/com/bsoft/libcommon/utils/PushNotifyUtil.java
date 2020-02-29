package com.bsoft.libcommon.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;


/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2019/10/15
 * describe :
 *
 *
 if (!PushNotifyUtil.isNotificationEnabled(mContext)) {
 new CustomDialog.Builder(mContext)
 .setContent("为了更好地体验互联网诊室功能，建议您开启系统推送功能")
 .setConfirmTextColor(ContextCompat.getColor(mContext, R.color.main))
 .setPositiveButton("去设置", (dialog, which) -> {
 PushNotifyUtil.gotoSetNotification(mContext);
 dialog.dismiss();
 })
 .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
 .create()
 .show();
 }
 *
 */
public class PushNotifyUtil {

    //是否开启了消息通知
    public static boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOpened;

    }

    //跳转设置界面开启消息通知
    public static void gotoSetNotification(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
