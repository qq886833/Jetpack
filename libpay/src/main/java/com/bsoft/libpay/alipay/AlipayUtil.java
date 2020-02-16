package com.bsoft.libpay.alipay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.alipay.sdk.app.PayTask;
import com.bsoft.libcommon.commonaop.PermissionCancel;
import com.bsoft.libcommon.commonaop.PermissionDenied;
import com.bsoft.libcommon.commonaop.PermissionNeed;
import com.bsoft.libcommon.utils.SettingUtil;
import com.bsoft.libpay.PayFragment;
import com.bsoft.libpay.PayResultListener;
import com.bsoft.libpay.dic.PayTypeDic;
import com.bsoft.libpay.model.PayResult;

import java.util.Map;

public class AlipayUtil  implements LifecycleObserver {

    private PayResultListener listener;

    private PayFragment rxPayFragment;
    private String payInfo;

    @SuppressLint("CheckResult")
    public AlipayUtil(@NonNull PayFragment rxPayFragment, PayResultListener listener) {
        this.listener = listener;
        this.rxPayFragment = rxPayFragment;

    }

    public void setListener(PayResultListener listener) {
        this.listener = listener;
    }

    @SuppressLint("CheckResult")
    public void goAlipay(final String payInfo) {
        this.payInfo=payInfo;
        requestPermission();
    }

    /**
     * 申请多个权限
     */
    @PermissionNeed(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, requestCode = 12)
    public void requestPermission() {
        pay(payInfo);
    }
    @PermissionCancel()
    public void permissionCancel(int requestCode) {
        Log.e("pay", "permissionCancel: " + requestCode);
    }


    private void pay(final String payInfo) {
        if (TextUtils.isEmpty(payInfo)) {
            PayResult resultVo = new PayResult();
            resultVo.setCode(PayTypeDic.RESULT_CODE_PARAME_ERROR);
            resultVo.setMsg("参数错误");
            if (listener != null) {
                listener.error(PayTypeDic.TYPE_ALI, resultVo);
            }
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(rxPayFragment.getActivity());
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(payInfo, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        if (listener != null) {
            Uri uri = Uri.parse("?" + payInfo);
            String appId = uri.getQueryParameter("app_id");
            listener.start(PayTypeDic.TYPE_ALI, appId, payInfo);
        }
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    //支付宝支付handler返回
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            PayResult resultVo = new PayResult();
            switch (msg.what) {
                case 1: {
                    AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
//                    resultVo.setCode(resultStatus);
                    resultVo.setExtra(resultInfo);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        resultVo.setCode(PayTypeDic.RESULT_CODE_OK);
                        resultVo.setMsg("支付成功");
                        if (listener != null) {
                            listener.success(PayTypeDic.TYPE_ALI, resultVo);
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        resultVo.setCode(PayTypeDic.RESULT_CODE_CANCEL);
                        resultVo.setMsg("支付取消");
                        if (listener != null) {
                            listener.cancel(PayTypeDic.TYPE_ALI, resultVo);
                        }
                    } else {
                        resultVo.setCode(PayTypeDic.RESULT_CODE_ERROR);
                        resultVo.setMsg("支付失败");
                        if (listener != null) {
                            listener.error(PayTypeDic.TYPE_ALI, resultVo);
                        }
                    }
                    break;
                }
                default:
                    resultVo.setCode(PayTypeDic.RESULT_CODE_ERROR);
                    resultVo.setMsg("支付失败");
                    if (listener != null) {
                        listener.error(PayTypeDic.TYPE_ALI, resultVo);
                    }
                    break;
            }
        }
    };
    @PermissionDenied()
    public void permissionDenied(int requestCode) {
        Log.e("pay", "permissionDenied: " + requestCode);
        switch (requestCode) {
            case 11:
                showDialog("定位权限被禁止，需要手动去开启");
                break;
            case 12:
                showDialog("文件存储权限可能被禁止，需要手动去开启");
                break;
        }
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(rxPayFragment.getActivity())
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SettingUtil.go2Setting(rxPayFragment.getActivity());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(rxPayFragment.getContext(), "权限获取失败", Toast.LENGTH_SHORT).show();
                        PayResult resultVo = new PayResult();
                        resultVo.setCode(PayTypeDic.RESULT_CODE_PERMISSION_DENY);
                        resultVo.setMsg("权限获取失败");
                        if (listener != null) {
                            listener.error(PayTypeDic.TYPE_ALI, resultVo);
                        }

                        dialog.dismiss();
                    }
                }).create().show();
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy(){
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


}
