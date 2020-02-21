package com.bsoft.libpay.model.pay;

import com.bsoft.libpay.model.BusType;

import java.util.List;

/**
 * 预约挂号
 * /*"hisOrderNumber": "295",//预约挂号记录id
 * "outpatientType": "1",//1标识非云诊室预约(健康城市可不传，互联网医院必穿)
 * "paymentBudgetNumber": "29421",//预结算标识
 * " invoiceNumber ":"发票号码"
 */


public class YZSGHBodyVo extends YYGHBodyVo {

    public String hisOrderNumber; //预约记录流水号(his返回)
    public String outpatientType;//2标识云诊室预约
    public String paymentBudgetNumber;//预结算标识
    public List<FeeRecord> feeRecords;


    public YZSGHBodyVo() {
        this.busType = BusType.YZZF;
    }

}
