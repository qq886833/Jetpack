package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

import java.util.List;





/**
 * 诊间支付
 * "paymentBudgetNumber": "23",//预结算标识
 * "busType": "2",//业务类型
 * "feeRecords": [{
 * "feeNo": "1082",
 * "feeTypeCode": "1",
 * "feeTypeName": "西药处方"
 * }],
 */
public class ZJZFBodyVo extends PayBodyVo {
    public String paymentBudgetNumber;
    public String patientCode;
    public String invoiceNumber;
    public List<FeeRecord> feeRecords;

    public ZJZFBodyVo() {
        super(BusType.ZJZF);
    }
}
