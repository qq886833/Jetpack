package com.bsoft.pub.jetpack.model;


import androidx.annotation.StringDef;
import com.bsoft.libnet.model.BaseVo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ${zhaozhijun} on 16/7/24.
 */
public class TransactionRecordVo extends BaseVo {

    public static final String TOBEPAY = "01";
    public static final String EXPIRED = "02";
    public static final String CANCELED = "03";
    public static final String REFUNDING = "04";
    public static final String REFUND = "05";
    public static final String SUCESSFUL = "06";
    public static final String FAILED = "07";
    public static final String PAYEDBUYHOSFAIL = "08";
    public static final String APPSUCESSFUL = "09";
    public static final String HCNFAILED = "10";
    @StringDef({TOBEPAY, EXPIRED, CANCELED, REFUNDING, REFUND, SUCESSFUL, FAILED, PAYEDBUYHOSFAIL, APPSUCESSFUL, HCNFAILED})//限定为
    @Retention(RetentionPolicy.SOURCE)//注解保留范围为源代码
    public @interface TradeStatus { //接口，定义新的注解类型
    }

    public String finishedTime;         //完成时间

    public String totalFee;             //总金额

    public String tradeType;            //交易类型

    public String tradeTypeText;        //交易说明

    public String payType;              //支付类型

    public String payTypeText;          //支付说明

    public String tradeStatusText;  //支付情况
    public String tradeStatus;  //支付情况



    public String getStatusText(String type) {
        String text = "";


        switch (type) {
            case TOBEPAY:
                text = "待付款";
                break;
            case EXPIRED:
                text = "过期";
                break;
            case CANCELED:
                text = "已取消";
                break;
            case REFUNDING:
                text = "退款中";
                break;
            case REFUND:
                text = "已退款";
                break;
            case SUCESSFUL:
                text = "支付成功";
                break;
            case FAILED:
                text = "支付失败";
                break;
            case PAYEDBUYHOSFAIL:
                text = "已支付，医院端处理失败";
                break;
            case APPSUCESSFUL:
                text = "app端支付成功";
                break;
            case HCNFAILED:
                text = "hcn处理失败";
                break;
            default:
                break;
        }
        return text;
    }
    /**
     * 参数只能传入在声明范围内的整型，不然编译通不过
     * @param currentDay
     */
    public void setCurrentStatusType(@TradeStatus String currentDay) {
        this.tradeStatus = currentDay;
    }

    @TradeStatus
    public String getCurrentStatusType() {
        return tradeStatus;
    }
}
