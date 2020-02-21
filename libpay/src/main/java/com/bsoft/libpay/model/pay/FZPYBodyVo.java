package com.bsoft.libpay.model.pay;


import com.bsoft.libpay.model.BusType;

/**
 * Created by wangzhaoxian
 * Email: wangzhaox@bsoft.com.cn
 * Date: 2019/11/1
 * Description:图文咨询
 */
/*
""departmentCode": "79",//科室代码
		"doctorCode": "93",//医生代码
		"patientCode": "1424",//病人id
		"consultId": 729,//业务主键
		"parentVisitNo": "46543254132"

*/

public class FZPYBodyVo extends PayBodyVo {
    public String departmentCode;
    public String doctorCode;
    public String patientCode;
    public String consultId;
    public String parentVisitNo;

    public FZPYBodyVo() {
        super(BusType.FZPY);
    }
}
