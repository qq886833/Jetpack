package com.bsoft.libpay.weixin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bsoft.libbasic.utils.DensityUtil;
import com.bsoft.libbasic.widget.dialog.ConfirmDialog;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed;
import com.bsoft.libcommon.commonaop.permission.util.SettingUtil;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.libpay.PayFragment;
import com.bsoft.libpay.PayResultListener;
import com.bsoft.libpay.dic.PayTypeDic;
import com.bsoft.libpay.model.PayResult;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.json.JSONObject;

public class WeixinPayUtil {

    private PayFragment rxPayFragment;
    private PayResultListener listener;

    /*Flag*/
    public static String weixinAppId = "";
    private boolean localWeixinEnable;
    private String payInfo;
    @SuppressLint("CheckResult")
    public WeixinPayUtil(@NonNull PayFragment rxPayFragment, PayResultListener listener) {
        this.rxPayFragment = rxPayFragment;
        this.listener = listener;




        LiveEventBus
                .get(LiveEventBusKey.KEY_WEIXIN_CALLBACK, BaseResp.class)
                .observe(rxPayFragment.getActivity(), new Observer<BaseResp>() {
                    @Override
                    public void onChanged(@Nullable BaseResp baseResp) {

                        if (!localWeixinEnable) {
                            return;
                        }
                        //appid 初始化
                        weixinAppId = "";
                        WXPayBackUtil.wxPayBackEnable = false;
                        localWeixinEnable = false;

                        PayResult resultVo = new PayResult();
                        resultVo.setExtra(baseResp.errStr);
                        if (baseResp.errCode == 0) {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_OK);
                            resultVo.setMsg("支付成功");
                            if (listener != null) {
                                listener.success(PayTypeDic.TYPE_WEIXIN, resultVo);
                            }
                        } else if (baseResp.errCode == -2) {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_CANCEL);
                            resultVo.setMsg("支付取消");
                            if (listener != null) {
                                listener.cancel(PayTypeDic.TYPE_WEIXIN, resultVo);
                            }
                        } else {
                            resultVo.setCode(PayTypeDic.RESULT_CODE_ERROR);
                            resultVo.setMsg("支付失败");
                            if (listener != null) {
                                listener.error(PayTypeDic.TYPE_WEIXIN, resultVo);
                            }
                        }

                    }
                });
    }

    public void setListener(PayResultListener listener) {
        this.listener = listener;
    }

    @SuppressLint("CheckResult")
    public void goWechatPay(final String payInfo) {
        requestPermission(payInfo);
    }




    /**
     * 申请多个权限
     * @param payInfo
     */
    @PermissionNeed(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE}, requestCode = 12)
    public void requestPermission(String payInfo) {
        pay(payInfo);
    }



    private void pay(String payInfo) {
        if (TextUtils.isEmpty(payInfo)) {
            PayResult resultVo = new PayResult();
            resultVo.setCode(PayTypeDic.RESULT_CODE_PARAME_ERROR);
            resultVo.setMsg("参数错误");
            if (listener != null) {
                listener.error(PayTypeDic.TYPE_WEIXIN, resultVo);
            }
            return;
        }

        try {
            JSONObject ob = new JSONObject(payInfo);
            PayReq req = new PayReq();
            req.appId = ob.getString("appid");
            weixinAppId = req.appId;
            req.partnerId = ob.getString("partnerid");
            req.packageValue = ob.getString("package");
            req.timeStamp = ob.getString("timestamp");
            req.sign = ob.getString("sign");
            req.nonceStr = ob.getString("noncestr");
            req.prepayId = ob.getString("prepayid");
            if (ob.has("extData")) {
                req.extData = ob.getString("extData");
            }
            IWXAPI wxApi = WXAPIFactory.createWXAPI(rxPayFragment.getActivity(), null);
            wxApi.registerApp(req.appId);
            if (!wxApi.isWXAppInstalled()) {

                Toast.makeText(rxPayFragment.getContext(), "没有安装微信", Toast.LENGTH_SHORT).show();
                PayResult resultVo = new PayResult();
                resultVo.setCode(PayTypeDic.RESULT_CODE_NO_WEIXIN_APP);
                resultVo.setMsg("没有安装微信");
                if (listener != null) {
                    listener.error(PayTypeDic.TYPE_WEIXIN, resultVo);
                }
                return;
            } else {
                WXPayBackUtil.wxPayBackEnable = true;
                localWeixinEnable = true;
                if (listener != null) {
                    listener.start(PayTypeDic.TYPE_WEIXIN, weixinAppId, payInfo);
                }
                wxApi.sendReq(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PayResult resultVo = new PayResult();
            resultVo.setCode(PayTypeDic.RESULT_CODE_PARAME_ERROR);
            resultVo.setMsg("参数错误");
            if (listener != null) {
                listener.error(PayTypeDic.TYPE_WEIXIN, resultVo);
            }
        }
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
        ConfirmDialog
                .newInstance("提示",
                        message,
                        "去开启",
                        "取消")
                .setCommonDialogListener(new ConfirmDialog.CommonDialogListener() {
                    @Override
                    public void onComplete(boolean ok, String tag) {
                        if (ok) {

                            SettingUtil.go2Setting(rxPayFragment.getActivity());
                        } else {
                            Toast.makeText(rxPayFragment.getContext(), "权限获取失败", Toast.LENGTH_SHORT).show();
                            PayResult resultVo = new PayResult();
                            resultVo.setCode(PayTypeDic.RESULT_CODE_PERMISSION_DENY);
                            resultVo.setMsg("权限获取失败");
                            if (listener != null) {
                                listener.error(PayTypeDic.TYPE_WEIXIN, resultVo);
                            }
                        }
                    }
                }).setOutCancel(false)
                .setMargin(DensityUtil.dip2px(25))
                .show(rxPayFragment.getChildFragmentManager());
    }


}
