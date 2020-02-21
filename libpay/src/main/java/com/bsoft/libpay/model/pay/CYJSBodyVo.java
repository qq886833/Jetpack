package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * Created by wangzhaoxian
 * Email: wangzhaox@bsoft.com.cn
 * Date: 2019/11/1
 * Description:出院结算
 */
public class CYJSBodyVo extends PayBodyVo {
    public String merchantOrderNo;//商户订单号
    public String inHospitalRecordNumber;//住院号
    public String patientCode;//病人id

    public CYJSBodyVo() {
        super(BusType.CYJS);
    }
}
