package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * 门诊账户充值
 */
public class ZHCZBodyVo extends PayBodyVo {
    public String patientCode;

    public ZHCZBodyVo() {
        super(BusType.ZHCZ);
    }

}
