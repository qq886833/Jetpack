package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * 门诊账户充值
 */
public class BLFYBodyVo extends PayBodyVo {
    public String caseHistoryId;

    public BLFYBodyVo() {
        super(BusType.BLFY);
    }

}
