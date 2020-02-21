package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * 预约挂号
 * /*"hisOrderNumber": "295",//预约挂号记录id
 * "outpatientType": "1",//1标识非云诊室预约(健康城市可不传，互联网医院必穿)
 * "paymentBudgetNumber": "29421",//预结算标识
 * " invoiceNumber ":"发票号码"
 */


public class YYGHBodyVo extends PayBodyVo {

    public String hisOrderNumber;
    public String outpatientType;
    public String invoiceNumber;
    public String paymentBudgetNumber;
    public String patientCode;

    public YYGHBodyVo() {
        super(BusType.YYGH);
    }

}
