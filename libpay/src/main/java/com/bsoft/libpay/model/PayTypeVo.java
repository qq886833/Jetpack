package com.bsoft.libpay.model;

import java.io.Serializable;

public class PayTypeVo implements Serializable {


    /**
     * orgId	String		N	健康城市机构id
     * payType	String		Y	支付方式
     * 1  健康钱包
     * 2  支付宝APP
     * 2-1  支付宝-扫码支付
     * 3  微信APP
     * 3-1   微信-扫码支付
     * 3-2   微信-公众号支付
     * 4  银行卡
     * 6  中福支付
     * 6-1  中福支付-健康钱包
     * 6-2  中福支付-支付宝
     * 6-3  中福支付-微信
     * 6-4  中福支付-银行卡
     * <p>
     * payTypeText	String		Y	支付标题
     * payDefault	Int		N	默认值1-默认
     * payExplain	String		N	支付说明
     * account	String		N	支付账号
     * appId	String		N	应用id
     * <p>
     * <p>
     * "orgId": "29179311-e7e7-45ba-9a79-126ae53045b7",
     * "payType": "2",
     * "payTypeText": "支付宝",
     * "payDefault": "2",
     * "payExplain": "推荐使用支付宝",
     * "account": "bsoftmobile@qq.com",
     * "appId": "2018080160806862"
     * payIcon //支付方式的图标，本地存放
     */

    public boolean isSelected;

    public boolean ifJbf(){
        return payType.equals("6");
    }
    public String orgId;



    public String payType;
    public String payTypeText;
    public String payDefault;
    public String payExplain;
    public String account;
    public String appId;
    public int payIcon;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeText() {
        return payTypeText;
    }

    public void setPayTypeText(String payTypeText) {
        this.payTypeText = payTypeText;
    }

    public String getPayDefault() {
        return payDefault;
    }

    public void setPayDefault(String payDefault) {
        this.payDefault = payDefault;
    }

    public String getPayExplain() {
        return payExplain;
    }

    public void setPayExplain(String payExplain) {
        this.payExplain = payExplain;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getPayIcon() {
        return payIcon;
    }

    public void setPayIcon(int payIcon) {
        this.payIcon = payIcon;
    }
}
