package com.bsoft.libpay;


import com.bsoft.libpay.model.PayResult;

public interface PayResultListener {
    void start(String payType, String appId, String payInfo);

    void success(String payType, PayResult resultVo);

    void error(String payType, PayResult resultVo);

    void cancel(String payType, PayResult resultVo);
}
