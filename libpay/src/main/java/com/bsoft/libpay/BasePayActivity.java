package com.bsoft.libpay;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import com.bsoft.libbasic.base.activity.CoreActivity;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libnet.api.NetPostApi;
import com.bsoft.libnet.observer.BaseObserver;
import com.bsoft.libpay.dic.PayTypeDic;
import com.bsoft.libpay.model.PayResult;
import com.bsoft.libpay.model.PayTypeVo;
import com.bsoft.libpay.model.TradeVo;
import com.bsoft.libpay.model.pay.PayBodyVo;
import com.bsoft.libpay.widget.PayTypeLayout;
import com.jeremyliao.liveeventbus.LiveEventBus;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;

public abstract class BasePayActivity extends CoreActivity {
    public static final String KEY_PAY_RESULT_NAME = "pay_result";
    public AppCompatActivity appCompatActivity;
    public PayTypeLayout payTypeLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        appCompatActivity=this;
       // payTypeLayout = findViewById(R.id.paytype_layout);
        //payTypeLayout.takePayType(this,getHospitalCode(),getBusType(),getPayPrice(),getOptionType());

    }




    public void taskPayOrder() {

        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.payTradeV2");
        head.put(HttpConstants.Head_Method, "createPaymentOrder");

        body.put("hospitalCode",getHospitalCode());
        body.put("amt",getPayPrice());
        body.put("payType",payTypeLayout.getPayType());
        body.put("externalTradeNo",getExternalTradeNo());//预结算标识
        body.put("body", getPayBody());
        list.add(body);

       NetPostApi.getInstance().post(HttpConstants.REQUEST_URL,head, list, TradeVo.class, new BaseObserver<TradeVo>() {
            @Override
            public void onHandlePrePare(Disposable d) {
               showLoadingDialog();
            }

            @Override
            protected void onHandleSuccess(TradeVo value) {

                if (value != null) {
                    PayManager payManager = new PayManager(appCompatActivity, new PayResultListener() {
                        @Override
                        public void start(String payType, String appId, String payInfo) {

                        }

                        @Override
                        public void success(String payType, PayResult resultVo) {
                            taskQueryPay(value.tradeNo);

                        }

                        @Override
                        public void error(String payType, PayResult resultVo) {

                        }

                        @Override
                        public void cancel(String payType, PayResult resultVo) {

                        }


                    });
                    PayTypeVo payWayVo = payTypeLayout.getPayTypeVo();
                    if(payWayVo != null && payWayVo.ifJbf()){
                        payManager.pay(PayTypeDic.TYPE_HBPAY, value.tradeInfo);
                    }else
                    if (TextUtils.equals(payTypeLayout.getPayType(), PayTypeDic.ALI_PAY_CODE)) {
                        payManager.pay(PayTypeDic.TYPE_ALI, value.tradeInfo);
                    } else if (TextUtils.equals(payTypeLayout.getPayType(), PayTypeDic.WE_CHAT_PAY_CODE)) {
                        payManager.pay(PayTypeDic.TYPE_WEIXIN, value.tradeInfo);
                    }
                }else {
                    taskQueryPay(value.tradeNo);
                }
            }

            @Override
            protected void onHandleError(Throwable e) {

                dismissLoadingDialog();
                ToastUtil.showLong(e.getMessage());
            }

            @Override
            protected void onHandleComplete() {
                dismissLoadingDialog();
            }
        });
    }

    public void taskQueryPay(String tradeNo) {
        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.payTradeV2");
        head.put(HttpConstants.Head_Method, "notifyPayResult");

        body.put("tradeNo",tradeNo);
        list.add(body);

        NetPostApi.getInstance().post(HttpConstants.REQUEST_URL,head, list, String.class, new BaseObserver<String>() {
            @Override
            public void onHandlePrePare(Disposable d) {
                showLoadingDialog();
            }

            @Override
            protected void onHandleSuccess(String value) {
                LiveEventBus.get(KEY_PAY_RESULT_NAME).post(value);
            }

            @Override
            protected void onHandleError(Throwable e) {

                dismissLoadingDialog();
                ToastUtil.showLong(e.getMessage());
            }

            @Override
            protected void onHandleComplete() {
                dismissLoadingDialog();
            }

        });



    }

    public boolean validate() {
        if (TextUtils.isEmpty(payTypeLayout.getPayType())) {
            ToastUtil.showShort("请先选择支付方式");
            return false;
        }
        return true;
    }
    protected abstract PayBodyVo getPayBody();
    protected abstract int getLayoutId();
    protected abstract String getHospitalCode();
    protected abstract String getBusType();//模块id
    protected abstract String getOptionType();
    protected abstract String getPayPrice();
    protected abstract String getExternalTradeNo();
}
