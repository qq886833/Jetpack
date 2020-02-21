package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * Created by wangzhaoxian
 * Email: wangzhaox@bsoft.com.cn
 * Date: 2019/11/1
 * Description:图文咨询
 */
/*
"doctorCode": "1983",//医生工号
        "patientCode": "1045",//病人代码
        "consultId": "726"//业务主键
*/

public class ZXWZBodyVo extends PayBodyVo {
    public String doctorCode;
    public String patientCode;
    public String consultId;

    public ZXWZBodyVo() {
        super(BusType.ZXWZ);
    }
}
