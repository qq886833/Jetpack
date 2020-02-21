package com.bsoft.libpay.hbpay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import com.alibaba.fastjson.JSON;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed;
import com.bsoft.libcommon.commonaop.permission.util.SettingUtil;
import com.bsoft.libpay.PayFragment;
import com.bsoft.libpay.PayResultListener;
import com.bsoft.libpay.dic.PayTypeDic;
import com.bsoft.libpay.model.PayResult;
import com.bsoft.libpay.weixin.WXPayBackUtil;
import com.healthpay.payment.hpaysdk.HPayAPIFactory;
import com.healthpay.payment.hpaysdk.HPayApi;
import com.healthpay.payment.hpaysdk.interfaces.HPayResultListener;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tencent.mm.opensdk.modelbase.BaseResp;


public class HbpayUtil implements LifecycleObserver {
    public static String KEY_NAME = "WEIXIN_CALLBACK";
    private PayFragment rxPayFragment;
    private PayResultListener listener;

    /*Flag*/
    private boolean localWeixinEnable;
    String payInfo;
    @SuppressLint("CheckResult")
    public HbpayUtil(@NonNull PayFragment rxPayFragment, PayResultListener listener) {
        this.rxPayFragment = rxPayFragment;
        this.listener = listener;


        LiveEventBus
                .get(KEY_NAME, BaseResp.class)
                .observe(rxPayFragment.getActivity(), new Observer<BaseResp>() {
                    @Override
                    public void onChanged(@Nullable BaseResp baseResp) {

                        if (!localWeixinEnable) {
                            return;
                        }

                        WXPayBackUtil.wxPayBackEnable = false;
                        localWeixinEnable = false;

                        PayResult resultVo = new PayResult();
                        resultVo.setExtra(baseResp.errStr);
                        if (baseResp.errCode == 0) {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_OK);
                            resultVo.setMsg("支付成功");
                            if (listener != null) {
                                listener.success(PayTypeDic.TYPE_HBPAY, resultVo);
                            }
                        } else if (baseResp.errCode == -2) {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_CANCEL);
                            resultVo.setMsg("支付取消");
                            if (listener != null) {
                                listener.cancel(PayTypeDic.TYPE_HBPAY, resultVo);
                            }
                        } else {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_ERROR);
                            resultVo.setMsg("支付失败");
                            if (listener != null) {
                                listener.error(PayTypeDic.TYPE_HBPAY, resultVo);
                            }

                        }

                    }
                });
    }

    public void setListener(PayResultListener listener) {
        this.listener = listener;
    }

    @SuppressLint("CheckResult")
    public void goHbpay(final String payInfo) {
        this.payInfo=payInfo;
        requestPermission();
    }

    /**
     * 申请多个权限
     * @param
     */
    @PermissionNeed(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE}, requestCode = 12)
    public void requestPermission() { pay(payInfo);
    }


    private void pay(final String payInfo) {
        if (TextUtils.isEmpty(payInfo)) {
            PayResult resultVo = new PayResult();
            resultVo.setCode(PayTypeDic.RESULT_CODE_PARAME_ERROR);
            resultVo.setMsg("参数错误");
            if (listener != null) {
                listener.error(PayTypeDic.TYPE_HBPAY, resultVo);
            }
        }

        final HBTradeVo hbTradeVo = JSON.parseObject(payInfo, HBTradeVo.class);

        WXPayBackUtil.wxPayBackEnable = true;
        localWeixinEnable = true;
        if (listener != null) {
            String appId = null;
            if (hbTradeVo != null) {
                appId = hbTradeVo.getMerchantId();
            }
            listener.start(PayTypeDic.TYPE_HBPAY, appId, payInfo);
        }

        HPayApi hPayApi = HPayAPIFactory.createHPayApi(hbTradeVo.getMerchantId(), hbTradeVo.getUrl());
        hPayApi.doPay(rxPayFragment.getActivity(), hbTradeVo.getSign(), hbTradeVo.getOrderInfo()
                , new HPayResultListener() {
                    @Override
                    public void result(String code, String msg) {
                        Log.d("HbpayUtil","code=" + code + ",msg=" + msg);

                        PayResult resultVo = new PayResult();
                        resultVo.setExtra(msg);
                        if (TextUtils.equals("1001", code)) {//健保付回调成功
                            if (!TextUtils.equals(hbTradeVo.getPayType(), "03")) {//"03"健宝付的微信
                                //微信有自己的回调，当非微信情况下，成功即可查询服务端支付结果
                                resultVo.setCode(PayTypeDic.RESULT_CODE_OK);
                                resultVo.setMsg("支付成功");
                                if (listener != null) {
                                    listener.success(PayTypeDic.TYPE_HBPAY, resultVo);
                                }
                            }
                        } else {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_ERROR);
                            resultVo.setMsg("支付失败");
                            if (listener != null) {
                                listener.error(PayTypeDic.TYPE_HBPAY, resultVo);
                            }
                        }
                    }
                });
    }



    @PermissionCancel()
    public void permissionCancel(int requestCode) {
        Log.e("pay", "permissionCancel: " + requestCode);
    }
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
                            listener.error(PayTypeDic.TYPE_HBPAY, resultVo);
                        }

                        dialog.dismiss();
                    }
                }).create().show();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy(){
        setListener(null);
    }


}
