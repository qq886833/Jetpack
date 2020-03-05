package com.bsoft.libcommon.utils.callphone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallPhoneUtil {

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }


    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint({"CheckResult", "MissingPermission"})
    public static void callPhone(final Activity activity, String phoneNum) {
        final Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        activity.startActivity(intent);


    }



}
