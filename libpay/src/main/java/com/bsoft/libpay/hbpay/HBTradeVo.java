package com.bsoft.libpay.hbpay;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12.
 * 健保付订单
 */

public class HBTradeVo implements Serializable {

    /**
     * merchantId : 1201001
     * url : http://122.224.131.235:9099/cfs-pay/alipay/feedback
     * payType : 06
     * sign : cqv3p6K+VpVTYoTf3Q2DsG2zfydgri2ba9Kcd787b8uOPY+NpQmFTwRDAC+iB52vIXTsn0PU9LxTp0UbIlOfew==
     * orderInfo : OUzghUEW4XYSEn3WgDHElvKGlRkcvr35WiqJQWLEAoC6HogGcd8u3waXRUrFs6sgkHHHIQhayhAiWaR64f3L/FCuEjKD5EPsH8LZWv+lV0uBjZ/FslFTznFiu2VlBr7shwUnhexaY2sXYbmT0L+4N3OL0n+IagYqGd0q5oCT7RleMGsHBFxue6J3jaEOuBYnaqs21329DBjj8tI9AsMv8lhtDcbn9iSdl2sfkswePioiXpcTPiS1z/qfcJFr8fQwFgVHDz1/pVaWbYty5vqZrWWnYg4v6rrBSNA6vPMpwmC9gRS/vgIYCwZZu0sV1f3UiXDPYLWdaXR8YK8BFSrHRphdon7Kny9uy8yXjij7lj2Fl6FZHPBpLiLCuVyB7aS0ydjrGFhK0YNKcTL8eyurIbsny2SxDDvpTzgUynXFEA/JD3FW4wyaMgSUWmM0r1oDDEFeQEZqxCNIpd1hnowB/nul+SPEjYCMcpox2i20FhDMdt+2+xVP7y+rnXn6MKEDnMUb8apgtVb9LBe0zaMXA1zMBawU9Vdl6T7bPfvn8xDdGMZPHIrl6A==
     */

    private String merchantId;//健保付平台id
    private String url;
    private String payType;
    private String sign;
    private String orderInfo;
    private String wxAppId;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }
}
