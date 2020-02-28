package com.bsoft.pub.jetpack.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.utils.DataConverUtil;
import com.bsoft.libbasic.utils.log.LogUtil;
import com.bsoft.message.model.MsgDetailVo;
import com.bsoft.message.utils.MsgUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtil.d(TAG, "JPush;onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogUtil.d(TAG, "JPush 用户注册成功,regId=" + regId);
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                CustomMessage customMessage = DataConverUtil.convertAtoB(message,CustomMessage.class);
                if (customMessage != null && TextUtils.equals(customMessage.getType(),"sso")){
//                    if (BaseAppInit.getInstance().getListener() != null) {
//                        BaseAppInit.getInstance().getListener().tokenError(customMessage.getContent());
//                    }
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                receivingNotification(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                openNotification(context, bundle);
            } else {
                LogUtil.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtil.d(TAG, " title : " + title + ",message : " + message + ",extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        LogUtil.d(TAG, "openNotification;extras : " + extras);

        MsgDetailVo msgDetailVo = DataConverUtil.convertAtoBInFastJson(extras, MsgDetailVo.class);
        if (msgDetailVo != null && !TextUtils.isEmpty(msgDetailVo.extras)  && !TextUtils.isEmpty(msgDetailVo.notificationCode)) {
            MsgUtil.msgRouter(context, msgDetailVo);
        } else {
            CommonArouterGroup.getArouter(CommonArouterGroup.MSG_LIST_ACTIVITY)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .navigation();
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtil.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }
}
