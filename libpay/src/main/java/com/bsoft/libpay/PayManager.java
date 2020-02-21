package com.bsoft.libpay;


import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.bsoft.libpay.alipay.AlipayUtil;
import com.bsoft.libpay.dic.PayTypeDic;
import com.bsoft.libpay.hbpay.HbpayUtil;
import com.bsoft.libpay.model.PayResult;
import com.bsoft.libpay.weixin.WeixinPayUtil;


public class PayManager {

    public static final String PAY_FRAGMENT_TAG = "core_pay_fragment";
    private AlipayUtil alipayUtil;
    private WeixinPayUtil weixinPayUtil;
    private HbpayUtil hbpayUtil;
    private PayResultListener listener;

    public PayManager(AppCompatActivity activity, PayResultListener listener) {
        this.listener = listener;
        alipayUtil = new AlipayUtil(getRxFragment(activity), listener);
        weixinPayUtil = new WeixinPayUtil(getRxFragment(activity), listener);
        hbpayUtil = new HbpayUtil(getRxFragment(activity), listener);
    }

    public void setListener(PayResultListener listener) {
        alipayUtil.setListener(listener);
        weixinPayUtil.setListener(listener);
        hbpayUtil.setListener(listener);
    }

    public void pay(String payType, String payInfo) {
        if (TextUtils.isEmpty(payInfo)) {
            PayResult resultVo = new PayResult();
            resultVo.setCode(PayTypeDic.RESULT_CODE_PARAME_ERROR);
            resultVo.setMsg("参数错误");
            if (listener != null) {
                listener.error(payType, resultVo);
            }
            return;
        }
        if (TextUtils.equals(payType, PayTypeDic.TYPE_ALI)) {
            alipayUtil.goAlipay(payInfo);
        } else if (TextUtils.equals(payType, PayTypeDic.TYPE_WEIXIN)) {
            weixinPayUtil.goWechatPay(payInfo);
        } else if (TextUtils.equals(payType, PayTypeDic.TYPE_HBPAY)) {
            hbpayUtil.goHbpay(payInfo);
        } else {
            PayResult resultVo = new PayResult();
            resultVo.setCode(PayTypeDic.RESULT_CODE_PARAME_ERROR);
            resultVo.setMsg("参数错误");
            if (listener != null) {
                listener.error(payType, resultVo);
            }
        }
    }

    private PayFragment getRxFragment(AppCompatActivity activity) {
        PayFragment fragment = findRxFragment(activity);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new PayFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, PayManager.PAY_FRAGMENT_TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    private PayFragment findRxFragment(AppCompatActivity activity) {
        return (PayFragment) activity.getSupportFragmentManager().findFragmentByTag(PayManager.PAY_FRAGMENT_TAG);
    }
}
